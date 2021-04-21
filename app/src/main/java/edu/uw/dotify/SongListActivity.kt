package edu.uw.dotify

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.dotify.databinding.ActivitySongListBinding

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private var currentSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "All Songs"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        with (binding) {
            var listOfSongs = SongDataProvider.getAllSongs().toMutableList()
            val adapter = SongListAdapter(listOfSongs)
            rvAllSongs.adapter = adapter

            adapter.onSongClickListener = {song: Song ->
                tvMiniPlayerText.text = root.context.getString(R.string.mini_player_text, song.title, song.artist)
                currentSong = song
            }

            adapter.onSongLongClickListener = {position, song ->
                listOfSongs.removeAt(position)
                Toast.makeText(this@SongListActivity,
                    root.context.getString(R.string.toast_delete_song, song.title, song.artist),
                    Toast.LENGTH_SHORT).show()
                adapter.updateSongList(listOfSongs)
            }

            btnShuffle.setOnClickListener {
                adapter.updateSongList(listOfSongs.shuffled())
            }

            clMiniPlayer.setOnClickListener {
                if (currentSong != null) {
                    navigateToPlayerActivity(this@SongListActivity, currentSong!!)
                }
            }
            
        }
    }
}