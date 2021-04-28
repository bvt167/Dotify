package edu.uw.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.dotify.databinding.ActivitySongListBinding

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private lateinit var listOfSongs: List<Song>
    private var currentSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "All Songs"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                currentSong = getParcelable(SONG_KEY)
                currentSong?.let {
                    setMiniPlayerText(it)
                }
            }
        }

        with (binding) {
            listOfSongs = SongDataProvider.getAllSongs()
            val adapter = SongListAdapter(listOfSongs)
            rvAllSongs.adapter = adapter

            adapter.onSongClickListener = {song: Song ->
                setMiniPlayerText(song)
                currentSong = song
            }

            adapter.onSongLongClickListener = {song ->
                val editedList = listOfSongs.toMutableList().apply{ remove(song) }
                Toast.makeText(this@SongListActivity,
                    root.context.getString(R.string.toast_delete_song, song.title, song.artist),
                    Toast.LENGTH_SHORT).show()
                adapter.updateSongList(editedList)
                listOfSongs = editedList
            }

            btnShuffle.setOnClickListener {
                val shuffledList = listOfSongs.toMutableList().shuffled()
                adapter.updateSongList(shuffledList)
                listOfSongs = shuffledList
            }

            clMiniPlayer.setOnClickListener {
                this@SongListActivity.currentSong?.let {
                    navigateToPlayerActivity(this@SongListActivity, it)
                }
            }
            
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelable(SONG_KEY, currentSong)
        }
        super.onSaveInstanceState(outState)
    }

    private fun setMiniPlayerText(song: Song) {
        binding.tvMiniPlayerText.text = this@SongListActivity.getString(R.string.mini_player_text, song.title, song.artist)
    }
}