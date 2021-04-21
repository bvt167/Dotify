package edu.uw.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import edu.uw.dotify.databinding.ActivityMainBinding
import kotlin.random.Random

private const val SONG_KEY = "song"
//private const val USERNAME_KEY = "username"

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
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        numPlays = Random.nextInt(0, 1000)
        val currentSong: Song? = intent.getParcelableExtra<Song>(SONG_KEY)

//        if (savedInstanceState != null) {
//            with(savedInstanceState) {
//                binding.tvUsername.text = getString(USERNAME_KEY)
//            }
//        }

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

            btnChangeUser.setOnClickListener {
                changeUser()
            }

            ivCoverArt.setOnLongClickListener {
                changePlayCountColor()
                true
            }
        }


    }

    private fun incrementNumPlays() {
        binding.tvNumPlays.text = "${(numPlays++).toString()} plays"
    }

    private fun showToastMsg(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun changeUser() {
        binding.btnChangeUser.text = "Apply"
        binding.tvUsername.visibility = View.GONE
        binding.etNewUsername.visibility = View.VISIBLE
        binding.btnChangeUser.setOnClickListener {
            setNewUsername()
        }
    }

    private fun setNewUsername() {
        val newUsername : String = binding.etNewUsername.text.toString()
        if (!newUsername.isNullOrEmpty()) {
            with(binding) {
                tvUsername.text = newUsername
                btnChangeUser.text = "Change User"
                tvUsername.visibility = View.VISIBLE
                etNewUsername.visibility = View.GONE
                btnChangeUser.setOnClickListener {
                    changeUser()
                }
            }
        }
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

//    override fun onSaveInstanceState(outState: Bundle) {
//        outState?.run {
//            putString(USERNAME_KEY, binding.tvUsername.text.toString())
//        }
//        if (outState != null) {
//            super.onSaveInstanceState(outState)
//        }
//    }
}