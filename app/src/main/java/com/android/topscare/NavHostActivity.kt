package com.android.topscare

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.android.topscare.databinding.ActivityNavHostBinding
import com.android.topscare.lib_base.utils.DWUtilities
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
        DWUtilities.CreateDWProfile(this)
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
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        updateScanResult(intent)
    }

    private fun updateScanResult(scanIntent: Intent) {
        val decodedData =
            scanIntent.getStringExtra(resources.getString(R.string.datawedge_intent_key_data))
        navHostViewModel._doReceiveBarcode.value = decodedData
    }
}