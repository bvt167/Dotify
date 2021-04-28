package edu.uw.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import com.ericchee.songdataprovider.Song
import edu.uw.dotify.databinding.ActivitySettingBinding

fun navigateToSettingsActivity(context: Context, song: Song, playCount: Int) = with(context) {
    val intent = Intent(this, SettingActivity::class.java).apply {
        val bundle = Bundle().apply {
            putParcelable(SONG_KEY, song)
            putInt(PLAY_COUNT_KEY, playCount)
        }
        putExtras(bundle)
    }
    startActivity(intent)
}

class SettingActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        navController.setGraph(R.navigation.nav_graph, intent.extras)
    }

}