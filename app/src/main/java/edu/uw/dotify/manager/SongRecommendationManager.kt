package edu.uw.dotify.manager

import android.content.Context
import androidx.work.*
import edu.uw.dotify.worker.SongRecommendationWorker
import java.util.concurrent.TimeUnit

private const val SONG_RECOMMENDATION_WORK_TAG = "SONG_RECOMMENDATION_WORK_TAG"

class SongRecommendationManager(context: Context) {

    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun songRecommendationPeriodically() {
        cancelWorkIfRunning(SONG_RECOMMENDATION_WORK_TAG)

        val request = PeriodicWorkRequestBuilder<SongRecommendationWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(SONG_RECOMMENDATION_WORK_TAG)
            .build()

        workManager.enqueue(request)
    }

    private fun cancelWorkIfRunning(tag: String) {
        if (isWorkRunning(tag)) {
            workManager.cancelAllWorkByTag(tag)
        }
    }

    private fun isWorkRunning(tag: String): Boolean {
        return workManager.getWorkInfosByTag(tag).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }
}