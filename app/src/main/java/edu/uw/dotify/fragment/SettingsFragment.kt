package edu.uw.dotify.fragment

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.dotify.DotifyApplication
import edu.uw.dotify.NavGraphDirections
import edu.uw.dotify.fragment.SettingsFragmentArgs
import edu.uw.dotify.databinding.FragmentSettingsBinding
import edu.uw.dotify.manager.SongRecommendationManager
import edu.uw.dotify.model.Song

class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private lateinit var binding: FragmentSettingsBinding
    private val safeArgs: SettingsFragmentArgs by navArgs()
    private lateinit var application: DotifyApplication
    private lateinit var songRecommendationManager: SongRecommendationManager
    private lateinit var song: Song
    private var playCount: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        application = context.applicationContext as DotifyApplication
        songRecommendationManager = application.songRecommendationManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        song = safeArgs.song
        playCount = safeArgs.playCount

        setFragmentNavigation()

        with(binding) {
            switchRecommendationNotif.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    songRecommendationManager.songRecommendationPeriodically()
                }
            }
        }

        return binding.root
    }

    private fun setFragmentNavigation() {
        with(binding) {
            btnProfile.setOnClickListener {
                navController.navigate(NavGraphDirections.actionGlobalProfileFragment())
            }

            btnAbout.setOnClickListener {
                navController.navigate(NavGraphDirections.actionGlobalAboutFragment())
            }

            btnStatistics.setOnClickListener {
                navController.navigate(
                    NavGraphDirections.actionGlobalStatisticsFragment(
                        song,
                        playCount
                    )
                )
            }
        }
    }
}