package edu.uw.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.uw.dotify.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var numPlays : Int = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        numPlays = Random.nextInt(0, 1000)
        binding.tvNumPlays.text = "${numPlays.toString()} plays"

        binding.ibPlay.setOnClickListener {
            incrementNumPlays()
        }

        binding.ibPrevious.setOnClickListener {
            showToastMsg("Skipping to previous Track")
        }

        binding.ibNext.setOnClickListener {
            showToastMsg("Skipping to next Track")
        }

        binding.btnChangeUser.setOnClickListener {
            changeUser()
        }

        binding.ivCoverArt.setOnLongClickListener {
            changePlayCountColor()
            true
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
            binding.tvUsername.text = newUsername
            binding.btnChangeUser.text = "Change User"
            binding.etNewUsername.text.clear()
            binding.tvUsername.visibility = View.VISIBLE
            binding.etNewUsername.visibility = View.GONE
            binding.btnChangeUser.setOnClickListener {
                changeUser()
            }
        }
    }

    private fun changePlayCountColor() {
        var currentColor = binding.tvNumPlays.currentTextColor
        if (currentColor == getColor(R.color.black)) {
            binding.tvNumPlays.setTextColor(getColor(R.color.purple_700))
        } else {
            binding.tvNumPlays.setTextColor(getColor(R.color.black))
        }
    }
}