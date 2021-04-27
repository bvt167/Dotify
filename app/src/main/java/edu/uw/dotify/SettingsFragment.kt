package edu.uw.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import edu.uw.dotify.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)

        with(binding) {
            btnProfile.setOnClickListener {
                navController.navigate(R.id.profileFragment)
            }

            btnAbout.setOnClickListener {
                navController.navigate(R.id.aboutFragment)
            }

            btnStatistics.setOnClickListener {
                navController.navigate(R.id.statisticsFragment)
            }
        }

        return binding.root
    }
}