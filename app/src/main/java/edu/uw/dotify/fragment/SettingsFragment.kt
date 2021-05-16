package edu.uw.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.dotify.NavGraphDirections
import edu.uw.dotify.fragment.SettingsFragmentArgs
import edu.uw.dotify.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private lateinit var binding: FragmentSettingsBinding
    private val safeArgs: SettingsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        val song = safeArgs.song
        val playCount = safeArgs.playCount

//        Toast.makeText(context, "${song.title} was played $playCount times", Toast.LENGTH_SHORT).show()

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

        return binding.root
    }
}