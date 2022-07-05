package jp.ac.uhyogo.apinfof

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
// import androidx.lifecycle.lifecycleScope
// import androidx.work.WorkManager
// import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import jp.ac.uhyogo.apinfof.WifiReceiver

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var wifiManager: WifiManager
        @SuppressLint("StaticFieldLeak")
        lateinit var tvTimestamp: TextView
        @SuppressLint("StaticFieldLeak")
        lateinit var tvFailure: TextView
        @SuppressLint("StaticFieldLeak")
        lateinit var lvAPInfo: ListView
    }

    private var wifiReceiver = WifiReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // wi-fiマネージャーの起動
        wifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
        // wi-fi off のとき，このアプリにおいてonにする
        if (!wifiManager.isWifiEnabled) {
            Toast.makeText(this, "Wifi is disable ... We need to enable it.", Toast.LENGTH_SHORT).show()
            wifiManager.isWifiEnabled
        }

        // permissionの設定
        val REQUEST_PERMISSIONS_ID = 127            // リクエスト識別用のユニークな値(数値はなんでもいい)
        val reqPermissions = ArrayList<String>()    // リクエスト用
        reqPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)    // 必要なpermissionをリクエスト に追加
        // パーミションのリクエスト
        ActivityCompat.requestPermissions(this, reqPermissions.toTypedArray(), REQUEST_PERMISSIONS_ID)

        // 画面要素の取得
        tvTimestamp = findViewById(R.id.timestamp)
        tvFailure   = findViewById(R.id.failure)
        lvAPInfo    = findViewById(R.id.lv)

        // AP情報の取得開始
        WifiScan.start()
    }

    override fun onResume() {
        registerReceiver(
            wifiReceiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )
        super.onResume()
    }

}