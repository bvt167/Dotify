package edu.uw.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import edu.uw.dotify.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    private val safeArgs: SettingsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStatisticsBinding.inflate(inflater)
        var song = safeArgs.song
        var numPlays = safeArgs.playCount

        with(binding) {
            tvNumPlays.text = root.context.getString(R.string.statistics_play_count_text, song.title, numPlays)
            ivCoverArt.setImageResource(song.largeImageID)
        }

        return binding.root
    }

}