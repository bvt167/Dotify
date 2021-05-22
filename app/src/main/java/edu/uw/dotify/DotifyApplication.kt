package edu.uw.dotify

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import edu.uw.dotify.manager.PlayerManager
import edu.uw.dotify.manager.SongNotificationManager
import edu.uw.dotify.manager.SongRecommendationManager
import repository.DataRepository

private const val DOTIFY_APP_PREFS_KEY = "DOTIFY_APP_PREFS_KEY"

class DotifyApplication: Application() {

    lateinit var playerManager: PlayerManager
    lateinit var dataRepository: DataRepository
    lateinit var songRecommendationManager: SongRecommendationManager
    lateinit var songNotificationManager: SongNotificationManager
    lateinit var preferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        this.playerManager = PlayerManager()
        this.dataRepository = DataRepository()
        this.songRecommendationManager = SongRecommendationManager(this)
        this.songNotificationManager = SongNotificationManager(this)
        this.preferences = this.getSharedPreferences(DOTIFY_APP_PREFS_KEY, Context.MODE_PRIVATE)
    }
}