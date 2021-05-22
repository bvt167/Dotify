package edu.uw.dotify.worker

import android.content.Context
import android.util.Log
import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import edu.uw.dotify.DotifyApplication

class SongRecommendationWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    private val application by lazy { context.applicationContext as DotifyApplication }

    override suspend fun doWork(): Result {
        Log.i("wtf", "This is the song recommendation worker reporting for duty")
        return Result.success()
    }
}