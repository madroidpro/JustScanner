package com.madroid.justscanner

import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.madroid.justscanner.databinding.ActivityMainBinding
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler,
    QRCodeReaderView.OnQRCodeReadListener {
    val TAG = "ScannerResults"
    lateinit var mScannerView: QRCodeReaderView;
    lateinit var mBinding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.presenter = this
        initView()
        // mScannerView.setAspectTolerance(0.5f)
    }

    private fun initView() {
        // mScannerView = mBinding.scannerView
        mScannerView = mBinding.qrscannerView
        mScannerView.setQRDecodingEnabled(true)
        mScannerView.setAutofocusInterval(2000L)
        mScannerView.setOnQRCodeReadListener(this)
        /*val list: List<BarcodeFormat> = mutableListOf(BarcodeFormat.QR_CODE);
        mScannerView.setFormats(list)
        mScannerView.setAutoFocus(true)*/
    }

    fun startScan() = runWithPermissions(android.Manifest.permission.CAMERA) {
        mBinding.btnScan.visibility = View.GONE
        mBinding.qrscannerView.visibility = View.VISIBLE
        mScannerView.startCamera();
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera();

    }

    override fun handleResult(rawResult: Result?) {
        Log.d(TAG, rawResult?.text)
    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        Log.d(TAG, text)
    }
}