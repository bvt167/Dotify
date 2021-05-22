package edu.uw.dotify.manager

import android.app.ApplicationErrorReport
import android.content.Context
import androidx.work.*
import edu.uw.dotify.worker.SongRecommendationWorker
import java.util.concurrent.TimeUnit

private const val SONG_RECOMMENDATION_WORK_TAG = "SONG_RECOMMENDATION_WORK_TAG"

class SongRecommendationManager(context: Context) {

    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun songRecommendationPeriodically() {
        cancelWorkIfRunningByTag(SONG_RECOMMENDATION_WORK_TAG)

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

    fun songRecommendationPeriodicallyExtraCredit() {
        cancelWorkIfRunningByTag(SONG_RECOMMENDATION_WORK_TAG)

        val request = PeriodicWorkRequestBuilder<SongRecommendationWorker>(2, TimeUnit.DAYS)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .addTag(SONG_RECOMMENDATION_WORK_TAG)
            .build()

        workManager.enqueue(request)
    }

    private fun cancelWorkIfRunningByTag(tag: String) {
        if (isWorkRunningByTag(tag)) {
            workManager.cancelAllWorkByTag(tag)
        }
    }

    private fun isWorkRunningByTag(tag: String): Boolean {
        return workManager.getWorkInfosByTag(tag).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }
}