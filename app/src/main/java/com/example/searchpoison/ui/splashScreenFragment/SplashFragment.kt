package com.example.searchpoison.ui.splashScreenFragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.searchpoison.R
import com.example.searchpoison.databinding.SplashScreenBinding
import com.example.searchpoison.ui.baseView.BaseViewBindingFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseViewBindingFragment<SplashScreenBinding>(SplashScreenBinding::inflate) {

    companion object {
        private const val TIME_ANIMATION_MILLIS = 5500L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiScope.launch {
            delay(TIME_ANIMATION_MILLIS)
            findNavController().navigate(R.id.action_splashScreenFragment_to_SearchPoisonFragment)
        }

        initSplash()
    }

    private fun initSplash() {
        binding.nameApp.text = getString(R.string.app_name)
    }
}