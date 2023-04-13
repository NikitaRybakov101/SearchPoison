package com.example.searchpoison.ui.splashScreenFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.searchpoison.R
import com.example.searchpoison.databinding.SplashScreenBinding
import com.example.searchpoison.ui.baseView.BaseViewBindingFragment
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : BaseViewBindingFragment<SplashScreenBinding>(SplashScreenBinding::inflate) {

    companion object {
        private const val TIME_ANIMATION_MILLIS = 5500L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_MainNewsFragment)
        }, TIME_ANIMATION_MILLIS)

        initSplash()
    }

    private fun initSplash() {
        binding.nameApp.text = getString(R.string.App_name_splash_screen)
    }
}