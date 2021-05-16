package edu.uw.dotify.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import edu.uw.dotify.R
import edu.uw.dotify.databinding.ActivitySettingBinding
import edu.uw.dotify.model.Song

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

    private lateinit var binding: ActivitySettingBinding
    private val navController by lazy { findNavController(R.id.navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater).apply { setContentView(root) }

        navController.setGraph(R.navigation.nav_graph, intent.extras)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}