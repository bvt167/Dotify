package edu.uw.dotify.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import coil.load
import edu.uw.dotify.DotifyApplication
import edu.uw.dotify.R
import edu.uw.dotify.databinding.ActivityPlayerBinding
import edu.uw.dotify.manager.PlayerManager
import edu.uw.dotify.model.Song
import kotlin.random.Random

const val SONG_KEY: String = "song"
const val PLAY_COUNT_KEY = "playCount"

fun navigateToPlayerActivity(context: Context, song: Song) = with(context) {
    val intent = Intent(this, PlayerActivity::class.java).apply {
        val bundle = Bundle().apply {
            putParcelable(SONG_KEY, song)
        }
        putExtras(bundle)
    }
    startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {

    private lateinit var playerManager: PlayerManager
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.playerManager = (application as DotifyApplication).playerManager
        binding = ActivityPlayerBinding.inflate(layoutInflater).apply { setContentView(root) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var currentSong: Song?
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                currentSong =  getParcelable(SONG_KEY)
                currentSong?.let {
                    playerManager.setCurrentSong(it)
                }
                playerManager.setPlayCount(getInt(PLAY_COUNT_KEY))
            }
        } else {
            currentSong = intent.getParcelableExtra<Song>(SONG_KEY)
            currentSong?.let {
                playerManager.setCurrentSong(it)
            }
            playerManager.setPlayCount(Random.nextInt(0, 1000))
        }

        with(binding) {
            tvTitle.text = currentSong?.title
            tvArtist.text = currentSong?.artist
            currentSong?.largeImageURL?.let { ivCoverArt.load(it) }

            tvNumPlays.text = root.context.getString(R.string.num_plays_text, playerManager.playCount)

            ibPlay.setOnClickListener {
                incrementNumPlays()
            }

            ibPrevious.setOnClickListener {
                showToastMsg(getString(R.string.toast_previous))
            }

            ibNext.setOnClickListener {
                showToastMsg(getString(R.string.toast_next))
            }

            ivCoverArt.setOnLongClickListener {
                changePlayCountColor()
                true
            }

        }


    }

    private fun incrementNumPlays() {
        binding.tvNumPlays.text = "${(playerManager.incrementPlayCount()).toString()} plays"
    }

    private fun showToastMsg(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun changePlayCountColor() {
        val currentColor = binding.tvNumPlays.currentTextColor
        if (currentColor == getColor(R.color.black)) {
            binding.tvNumPlays.setTextColor(getColor(R.color.purple_700))
        } else {
            binding.tvNumPlays.setTextColor(getColor(R.color.black))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelable(SONG_KEY, playerManager.currentSong)
            putInt(PLAY_COUNT_KEY, playerManager.playCount)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.player_menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.app_bar_settings -> this@PlayerActivity.playerManager.currentSong?.let {
                navigateToSettingsActivity(this@PlayerActivity, it, this@PlayerActivity.playerManager.playCount)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}