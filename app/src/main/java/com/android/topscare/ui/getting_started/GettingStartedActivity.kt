package com.android.topscare.ui.getting_started

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.android.topscare.NavHostActivity
import com.android.topscare.R
import com.android.topscare.databinding.ActivityGettingStartedBinding
import com.android.topscare.lib_base.extension.observe
import com.android.topscare.lib_base.extension.toUri
import com.android.topscare.ui.check.ProductInfoDialogFragmentArgs
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GettingStartedActivity : AppCompatActivity() {
    private val viewModel: GettingStartedViewModel by viewModels()
    private lateinit var binding: ActivityGettingStartedBinding
    private val args by navArgs<GettingStartedActivityArgs>()
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents != null) {
            doVibrate()
            result.contents?.let {
                val ip = it.toUri().getQueryParameter("ip")?:""
                val key = it.toUri().getQueryParameter("key")?:""
                viewModel.doCheckIp(ip, key)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGettingStartedBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        makeStatusBarTransparent()
        if(!viewModel.isFistTimeUsage()){
            navigateToMainMenu()
        }
        binding.descGettingStart.text = HtmlCompat.fromHtml(getString(R.string.desc_getting_started), HtmlCompat.FROM_HTML_MODE_COMPACT)
        registerObserver()
        val ip = args.ip
        val key = args.key
        if(ip?.isNotEmpty() == true && key?.isNotEmpty() == true){
            viewModel.doCheckIp(ip, key)
        }
    }

    private fun navigateToMainMenu() {
        startActivity(Intent(this, NavHostActivity::class.java))
        finish()
    }

    private fun registerObserver() {
        with(viewModel) {
            observe(_scanNow){
                openCameraToScan()
            }
            observe(_checkIpSuccess){
                navigateToMainMenu()
            }
            observe(_checkIpError){
                Toast.makeText(this@GettingStartedActivity,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun openCameraToScan() {
        val options = ScanOptions()
        options.setOrientationLocked(false)
        barcodeLauncher.launch(options)
    }
    @androidx.annotation.RequiresPermission(value = "android.permission.VIBRATE")
    private fun doVibrate(){
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        v!!.vibrate(300)
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