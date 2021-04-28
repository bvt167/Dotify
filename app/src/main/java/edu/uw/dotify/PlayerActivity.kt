package edu.uw.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import edu.uw.dotify.databinding.ActivityPlayerBinding
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

    private var numPlays : Int = 0
    private var currentSong: Song? = null
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityPlayerBinding.inflate(layoutInflater).apply { setContentView(root) }
        numPlays = Random.nextInt(0, 1000)
        currentSong = intent.getParcelableExtra<Song>(SONG_KEY)

        with(binding) {
            tvTitle.text = currentSong?.title
            tvArtist.text = currentSong?.artist
            currentSong?.largeImageID?.let { ivCoverArt.setImageResource(it) }

            tvNumPlays.text = root.context.getString(R.string.num_plays_text, numPlays)

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

            btnSettings.setOnClickListener {
                this@PlayerActivity.currentSong?.let {
                    navigateToSettingsActivity(this@PlayerActivity, it, this@PlayerActivity.numPlays)
                }
            }

        }


    }

    private fun incrementNumPlays() {
        binding.tvNumPlays.text = "${(numPlays++).toString()} plays"
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

//    private fun changeUser() {
//        binding.btnChangeUser.text = "Apply"
//        binding.tvUsername.visibility = View.GONE
//        binding.etNewUsername.visibility = View.VISIBLE
//        binding.btnChangeUser.setOnClickListener {
//            setNewUsername()
//        }
//    }
//
//    private fun setNewUsername() {
//        val newUsername : String = binding.etNewUsername.text.toString()
//        if (!newUsername.isNullOrEmpty()) {
//            with(binding) {
//                tvUsername.text = newUsername
//                btnChangeUser.text = "Change User"
//                tvUsername.visibility = View.VISIBLE
//                etNewUsername.visibility = View.GONE
//                btnChangeUser.setOnClickListener {
//                    changeUser()
//                }
//            }
//        }
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        outState?.run {
//            putString(USERNAME_KEY, binding.tvUsername.text.toString())
//        }
//        if (outState != null) {
//            super.onSaveInstanceState(outState)
//        }
//    }
}