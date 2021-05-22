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
    private val songNotificationManager by lazy { application.songNotificationManager }
    private val dataRepository by lazy { application.dataRepository }

    override suspend fun doWork(): Result {
        val randomSong = dataRepository.getSongList().songs.random()
        songNotificationManager.publishNewSongNotification(randomSong)
        return Result.success()
    }
}