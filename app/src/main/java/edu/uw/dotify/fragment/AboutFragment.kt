package edu.uw.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.uw.dotify.BuildConfig
import edu.uw.dotify.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAboutBinding.inflate(inflater)

        with(binding) {
            tvVersion.text = "Version: ${BuildConfig.VERSION_NAME}"
        }

        return binding.root
    }

}