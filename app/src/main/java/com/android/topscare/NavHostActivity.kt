package com.android.topscare

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.android.topscare.databinding.ActivityNavHostBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {
    private val navController: NavController by lazy { findNavController(R.id.navHostFragment)}
    private lateinit var binding: ActivityNavHostBinding
    private val navHostViewModel : NavHostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavHostBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = navHostViewModel
        setContentView(binding.root)
        makeStatusBarTransparent()
    }

    private fun makeStatusBarTransparent() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.TRANSPARENT
        }
    }
}