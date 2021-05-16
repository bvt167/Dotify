package repository

import edu.uw.dotify.model.Song
import edu.uw.dotify.model.SongList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * https://raw.githubusercontent.com/echeeUW/codesnippets/master/
 */

class DataRepository {

    private val songService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/echeeUW/codesnippets/master/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SongService::class.java)

    suspend fun getSongList(): SongList = songService.getSongList()
}

interface SongService {

    @GET("musiclibrary.json")
    suspend fun getSongList(): SongList

}
