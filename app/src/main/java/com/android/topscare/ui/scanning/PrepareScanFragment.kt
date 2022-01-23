package com.android.topscare.ui.scanning

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.android.topscare.NavHostViewModel
import com.android.topscare.R
import com.android.topscare.databinding.FragmentPrepareScanBinding
import com.android.topscare.domain.data.ScanMode
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.lib_base.base.BaseFragment
import com.android.topscare.lib_base.extension.observe
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PrepareScanFragment : BaseFragment() {
    private val viewModel: PrepareScanViewModel by viewModels()
    private lateinit var binding: FragmentPrepareScanBinding
    private val navHostViewModel: NavHostViewModel by activityViewModels()
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents != null) {
            doVibrate()
            result.contents?.let {
                viewModel.getProductByKey(key = it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrepareScanBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(navHostViewModel.scanMode.value?: ScanMode.CHECK)
        //Register receiver so my app can listen for intents which action is ACTION_BARCODE_DATA
        val intentFilter = IntentFilter(ACTION_BARCODE_DATA)
        requireContext().registerReceiver(barcodeDataReceiver, intentFilter)
        registerObserver()
    }
    private fun registerObserver() {
        with(viewModel) {
            observe(_onBackPressed) {
                navController.popBackStack()
            }
            observe(_onCameraPressed){
                openCameraToScan()
            }
            observe(_product){
                when(navHostViewModel.scanMode.value){
                    ScanMode.CHECK ->{
                        navigateToCheckDialog(it)
                    }
                    ScanMode.COUNT ->{
                        navigateToCountPage(it)
                    }
                    ScanMode.ORDER ->{
                        navigateToOrderPage(it)
                    }
                    ScanMode.RECEIVE ->{
                        navigateToReceivePage(it)
                    }
                }
            }
            observe(_onHistoryPressed){
                when(navHostViewModel.scanMode.value){
                    ScanMode.COUNT ->{
                        navigateToCountHistoryPage()
                    }
                    ScanMode.ORDER ->{
                        navigateToOrderHistoryPage()
                    }
                    ScanMode.RECEIVE ->{

                    }
                }
            }
            observe(dataStates){
                if(dataStates.value?.isError() == true){
                    navigateToError()
                }
            }
        }

        with(navHostViewModel){
            observe(_doCount){ req->
                req?.let {
                    viewModel.insertCount(it)
                }
            }
            observe(_doReceive){ req->
                req?.let {
                    viewModel.insertReceive(it)
                }
            }
            observe(_doOrder){ req->
                req?.let {
                    viewModel.insertOrder(it)
                }
            }
        }
    }

    private fun openCameraToScan() {
        val options = ScanOptions()
        options.setOrientationLocked(false)
        barcodeLauncher.launch(options)
    }
    private fun navigateToError() {
        navController.navigate(R.id.errorDialogFragment)
    }

    private fun navigateToCheckDialog(product: ProductResponse) {
        try {
            if (navController.currentDestination?.id == R.id.prepareScanFragment) {
                navController.navigate(
                    PrepareScanFragmentDirections.actionPrepareScanFragmentToProductInfoDialogFragment(
                        product
                    )
                )
            } else {
                navController.popBackStack(R.id.prepareScanFragment, false)
                navController.navigate(
                    PrepareScanFragmentDirections.actionPrepareScanFragmentToProductInfoDialogFragment(
                        product
                    )
                )
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    private fun navigateToCountPage(product: ProductResponse){
        try {
            if (navController.currentDestination?.id == R.id.prepareScanFragment) {
                navController.navigate(
                    PrepareScanFragmentDirections.actionPrepareScanFragmentToCountDialogFragment(
                        product
                    )
                )
            } else {
                navController.popBackStack(R.id.prepareScanFragment, false)
                navController.navigate(
                    PrepareScanFragmentDirections.actionPrepareScanFragmentToCountDialogFragment(
                        product
                    )
                )
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    private fun navigateToOrderPage(product: ProductResponse){
        try {
            if (navController.currentDestination?.id == R.id.prepareScanFragment) {
                navController.navigate(
                    PrepareScanFragmentDirections.actionPrepareScanFragmentToOrderDialogFragment(
                        product
                    )
                )
            } else {
                navController.popBackStack(R.id.prepareScanFragment, false)
                navController.navigate(
                    PrepareScanFragmentDirections.actionPrepareScanFragmentToOrderDialogFragment(
                        product
                    )
                )
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    private fun navigateToReceivePage(product: ProductResponse){
        try {
            if (navController.currentDestination?.id == R.id.prepareScanFragment) {
                navController.navigate(
                    PrepareScanFragmentDirections.actionPrepareScanFragmentToReceiveDialogFragment(
                        product
                    )
                )
            } else {
                navController.popBackStack(R.id.prepareScanFragment, false)
                navController.navigate(
                    PrepareScanFragmentDirections.actionPrepareScanFragmentToReceiveDialogFragment(
                        product
                    )
                )
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    private fun navigateToOrderHistoryPage(){

    }
    private fun navigateToCountHistoryPage(){
        navController.navigate(PrepareScanFragmentDirections.actionPrepareScanFragmentToCountHistoryFragment())
    }

    override fun onResume() {
        super.onResume()
        //Register receiver so my app can listen for intents which action is ACTION_BARCODE_DATA
        val intentFilter = IntentFilter(ACTION_BARCODE_DATA)
        requireContext().registerReceiver(barcodeDataReceiver, intentFilter)

        //Will setup the new configuration of the scanner.
        claimScanner()
    }
    @androidx.annotation.RequiresPermission(value = "android.permission.VIBRATE")
    private fun doVibrate(){
        val v = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        v!!.vibrate(300)
    }
    private val barcodeDataReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (ACTION_BARCODE_DATA == action) {
                doVibrate()
                /*
                These extras are available:
                    "version" (int) = Data Intent Api version
                    "aimId" (String) = The AIM Identifier
                    "charset" (String) = The charset used to convert "dataBytes" to "data" string
                    "codeId" (String) = The Honeywell Symbology Identifier
                    "data" (String) = The barcode data as a String
                    "dataBytes" (byte[]) = The barcode data as a byte array
                    "timestamp" (String) = The barcode timestamp
                 */
                val version = intent.getIntExtra("version", 0)
                if (version >= 1) {
                    val aimId = intent.getStringExtra("aimId")
                    val charset = intent.getStringExtra("charset")
                    val codeId = intent.getStringExtra("codeId")
                    val data = intent.getStringExtra("data")
                    val dataBytes = intent.getByteArrayExtra("dataBytes")
                    val dataBytesStr: String = bytesToHexString(dataBytes)
                    val timestamp = intent.getStringExtra("timestamp")
                    val text = String.format(
                        """
                          Data:%s
                          Charset:%s
                          Bytes:%s
                          AimId:%s
                          CodeId:%s
                          Timestamp:%s
                          
                          """.trimIndent(),
                        data, charset, dataBytesStr, aimId, codeId, timestamp
                    )
                    data?.let {
                        viewModel.getProductByKey(key = it)
                    }
                }
            }
        }
    }
    private fun claimScanner() {
        val properties = Bundle()

        //When we press the scan button and read a barcode, a new Broadcast intent will be launched by the service
        properties.putBoolean("DPR_DATA_INTENT", true)

        //That intent will have the action "ACTION_BARCODE_DATA"
        // We will capture the intents with that action (every scan event while in the application)
        // in our BroadcastReceiver barcodeDataReceiver.
        properties.putString("DPR_DATA_INTENT_ACTION", ACTION_BARCODE_DATA)
        //properties.putString("TRIGGER_MODE", "continuous");
        val intent = Intent()
        intent.action = ACTION_CLAIM_SCANNER

        /*
         * We use setPackage() in order to send an Explicit Broadcast Intent, since it is a requirement
         * after API Level 26+ (Android 8)
         */intent.setPackage("com.intermec.datacollectionservice")

        //We will use the internal scanner
        intent.putExtra(EXTRA_SCANNER, "dcs.scanner.imager")

        /*
        We are using "MyProfile1", so a profile with this name has to be created in Scanner settings:
               Android Settings > Honeywell Settings > Scanning > Internal scanner > "+"
        - If we use "DEFAULT" it will apply the settings from the Default profile in Scanner settings
        - If not found, it will use Factory default settings.
         */intent.putExtra(EXTRA_PROFILE, "TopsCare")
        intent.putExtra(EXTRA_PROPERTIES, properties)
        requireContext().sendBroadcast(intent)
    }

     override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(barcodeDataReceiver)
        releaseScanner()
    }

    private fun releaseScanner() {
        val intent = Intent()
        intent.action = ACTION_RELEASE_SCANNER
        requireContext().sendBroadcast(intent)
    }

    private fun bytesToHexString(array: ByteArray?): String {
        var s = "[]"
        array?.let {
            s = "["
            for (i in it.indices) {
                s += "0x" + Integer.toHexString(it[i].toInt()) + ", "
            }
            s = s.substring(0, s.length - 2) + "]"
        }
        return s
    }

    companion object {
        const val ACTION_BARCODE_DATA = "com.android.topscare.ui.scanning.BARCODE"
        const val EXTRA_CONTROL = "com.honeywell.aidc.action.ACTION_CONTROL_SCANNER"
        /**
         * Honeywell DataCollection Intent API
         * Claim scanner
         * Permissions:
         * "com.honeywell.decode.permission.DECODE"
         */
        const val ACTION_CLAIM_SCANNER = "com.honeywell.aidc.action.ACTION_CLAIM_SCANNER"

        /**
         * Honeywell DataCollection Intent API
         * Release scanner claim
         * Permissions:
         * "com.honeywell.decode.permission.DECODE"
         */
        const val ACTION_RELEASE_SCANNER = "com.honeywell.aidc.action.ACTION_RELEASE_SCANNER"

        /**
         * Honeywell DataCollection Intent API
         * Optional. Sets the scanner to claim. If scanner is not available or if extra is not used,
         * DataCollection will choose an available scanner.
         * Values : String
         * "dcs.scanner.imager" : Uses the internal scanner
         * "dcs.scanner.ring" : Uses the external ring scanner
         */
        const val EXTRA_SCANNER = "com.honeywell.aidc.extra.EXTRA_SCANNER"

        /**
         * Honeywell DataCollection Instent API
         * Optional. Sets the profile to use. If profile is not available or if extra is not used,
         * the scanner will use factory default properties (not "DEFAULT" profile properties).
         * Values : String
         */
        const val EXTRA_PROFILE = "com.honeywell.aidc.extra.EXTRA_PROFILE"

        /**
         * Honeywell DataCollection Intent API
         * Optional. Overrides the profile properties (non-persistend) until the next scanner claim.
         * Values : Bundle
         */
        const val EXTRA_PROPERTIES = "com.honeywell.aidc.extra.EXTRA_PROPERTIES"
    }
}