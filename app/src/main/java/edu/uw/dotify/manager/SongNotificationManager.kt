package edu.uw.dotify.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import edu.uw.dotify.R
import edu.uw.dotify.DotifyApplication
import edu.uw.dotify.model.Song
import repository.DataRepository
import kotlin.random.Random

private const val NEW_SONG_CHANNEL_ID = "NEW_SONG_CHANNEL_ID"

class SongNotificationManager(
    private  val context: Context
) {

    private val notificationManager = NotificationManagerCompat.from(context)

    // For converting Song from SongDataProvider to another Song class
    private var application: DotifyApplication
    private var dataRepository: DataRepository

    init {
        initNotificationChannels()
        application = context.applicationContext as DotifyApplication
        dataRepository = application.dataRepository
    }

    fun publishNewSongNotification(randomSong: Song) {
        val builder = NotificationCompat.Builder(context, NEW_SONG_CHANNEL_ID)    // channel id from creating the channel
            .setSmallIcon(R.drawable.ic_baseline_android_24)
            .setContentTitle(this.context.getString(R.string.new_song_notification_title, randomSong.artist))
            .setContentText(this.context.getString(R.string.new_song_notification_text, randomSong.title))
            // .setContentIntent(pendingIntent)    // sets the action when user clicks on notification
            .setAutoCancel(true)    // This will dismiss the notification tap
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(notificationManager) {
            notify(Random.nextInt(), builder.build())
        }
    }

    private fun initNotificationChannels() {
        initNewSongChannel()
    }

    private fun initNewSongChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Info about the channel
            val name = context.getString(R.string.new_song_channel)
            val descriptionText = context.getString(R.string.new_song_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            // Create channel object
            val channel = NotificationChannel(NEW_SONG_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Tell the Android OS to create a channel
            notificationManager.createNotificationChannel(channel)
        }
    }

}