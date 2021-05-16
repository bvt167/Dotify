package edu.uw.dotify

import android.app.Application
import edu.uw.dotify.manager.PlayerManager
import repository.DataRepository

class DotifyApplication: Application() {

    lateinit var playerManager: PlayerManager
    lateinit var dataRespository: DataRepository


    override fun onCreate() {
        super.onCreate()

        this.playerManager = PlayerManager()
        this.dataRespository = DataRepository()
    }
}