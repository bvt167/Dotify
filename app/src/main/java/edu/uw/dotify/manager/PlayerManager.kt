package edu.uw.dotify.manager

import edu.uw.dotify.model.Song

class PlayerManager {

    var currentSong: Song? = null
        private set
    var playCount: Int = 0
        private set

//    maybe move songList functionality here in the future
//    var songList: List<Song> = SongDataProvider.getAllSongs()
//        private set

    fun incrementPlayCount(): Int {
        playCount++
        return playCount
    }

    fun setCurrentSong(song: Song) {
        this.currentSong = song
    }

    fun setPlayCount(playCount: Int) {
        this.playCount = playCount
    }
}