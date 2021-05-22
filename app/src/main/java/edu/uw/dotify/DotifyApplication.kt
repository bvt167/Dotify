package edu.uw.dotify

import android.app.Application
import android.app.NotificationManager
import edu.uw.dotify.manager.PlayerManager
import edu.uw.dotify.manager.SongNotificationManager
import edu.uw.dotify.manager.SongRecommendationManager
import repository.DataRepository

class DotifyApplication: Application() {

    lateinit var playerManager: PlayerManager
    lateinit var dataRepository: DataRepository
    lateinit var songRecommendationManager: SongRecommendationManager
    lateinit var songNotificationManager: SongNotificationManager

    override fun onCreate() {
        super.onCreate()

        this.playerManager = PlayerManager()
        this.dataRepository = DataRepository()
        this.songRecommendationManager = SongRecommendationManager(this)
        this.songNotificationManager = SongNotificationManager(this)
    }
}