package edu.uw.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.ericchee.songdataprovider.Song
import edu.uw.dotify.databinding.ActivitySettingBinding

fun navigateToSettingsActivity(context: Context, song: Song) = with(context) {
    val intent = Intent(this, SettingActivity::class.java).apply {
        val bundle = Bundle().apply {
            putParcelable(SONG_KEY, song)
        }
        putExtras(bundle)
    }
    startActivity(intent)
}

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

    }
}