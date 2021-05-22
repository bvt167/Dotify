package edu.uw.dotify

import android.app.Application
import edu.uw.dotify.manager.PlayerManager
import edu.uw.dotify.manager.SongRecommendationManager
import repository.DataRepository

class DotifyApplication: Application() {

    lateinit var playerManager: PlayerManager
    lateinit var dataRepository: DataRepository
    lateinit var songRecommendationManager: SongRecommendationManager

    override fun onCreate() {
        super.onCreate()

        this.playerManager = PlayerManager()
        this.dataRepository = DataRepository()
        this.songRecommendationManager = SongRecommendationManager(this)
    }
}