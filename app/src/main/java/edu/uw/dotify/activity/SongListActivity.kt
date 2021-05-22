package edu.uw.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import edu.uw.dotify.DotifyApplication
import edu.uw.dotify.R
import edu.uw.dotify.adapter.SongListAdapter
import edu.uw.dotify.databinding.ActivitySongListBinding
import edu.uw.dotify.manager.PlayerManager
import edu.uw.dotify.model.Song
import kotlinx.coroutines.launch

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private lateinit var listOfSongs: List<Song>
    private lateinit var playerManager: PlayerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "All Songs"
        super.onCreate(savedInstanceState)

        playerManager = (application as DotifyApplication).playerManager
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                var currentSong: Song? = getParcelable(SONG_KEY)
                currentSong?.let {
                    setMiniPlayerText(it)
                }
            }
        }

        with (binding) {
            loadSongs()

            clMiniPlayer.setOnClickListener {
                this@SongListActivity.playerManager.currentSong?.let {
                    navigateToPlayerActivity(this@SongListActivity, it)
                }
            }

            swipeToRefreshLayout.setOnRefreshListener {
                hideErrorMsg()
                loadSongs()
                swipeToRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelable(SONG_KEY, playerManager.currentSong)
        }
        super.onSaveInstanceState(outState)
    }

    private fun setMiniPlayerText(song: Song) {
        binding.tvMiniPlayerText.text = this@SongListActivity.getString(R.string.mini_player_text, song.title, song.artist)
    }

    private fun loadSongs() {
        with(binding) {
            lifecycleScope.launch {
                runCatching {
                    listOfSongs = (application as DotifyApplication).dataRepository.getSongList().songs
                    val adapter = SongListAdapter(listOfSongs)
                    rvAllSongs.adapter = adapter

                    adapter.onSongClickListener = { song: Song ->
                        setMiniPlayerText(song)
                        playerManager.setCurrentSong(song)
                    }

                    adapter.onSongLongClickListener = { song ->
                        val editedList = listOfSongs.toMutableList().apply { remove(song) }
                        Toast.makeText(
                            this@SongListActivity,
                            root.context.getString(
                                R.string.toast_delete_song,
                                song.title,
                                song.artist
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                        adapter.updateSongList(editedList)
                        listOfSongs = editedList
                    }

                    btnShuffle.setOnClickListener {
                        val shuffledList = listOfSongs.toMutableList().shuffled()
                        adapter.updateSongList(shuffledList)
                        listOfSongs = shuffledList
                    }
                }.onFailure {
                    showErrorMsg()
                }
            }
        }
    }

    private fun showErrorMsg(errorMsg: String = "Error has occurred") {
        with(binding) {
            rvAllSongs.visibility = View.GONE
            tvErrorMsg.text = errorMsg
            tvErrorMsg.visibility = View.VISIBLE
        }
    }

    private fun hideErrorMsg() {
        with(binding) {
            tvErrorMsg.visibility = View.GONE
            rvAllSongs.visibility = View.VISIBLE
        }
    }
}