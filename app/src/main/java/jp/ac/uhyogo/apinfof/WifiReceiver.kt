package jp.ac.uhyogo.apinfof

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import android.view.View
import jp.ac.uhyogo.apinfof.MainActivity.Companion.lvAPInfo
import jp.ac.uhyogo.apinfof.MainActivity.Companion.tvFailure
import jp.ac.uhyogo.apinfof.MainActivity.Companion.tvTimestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WifiReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)

        if (success) {
            scanSuccess(context, MainActivity.wifiManager)   // スキャン成功時
        } else {
            scanFailure()   // スキャン失敗時
        }
    }

    // スキャン成功時
    private fun scanSuccess(context: Context, wifiManager: WifiManager) {
        Log.i("Scan", "Success")
        tvFailure.visibility = View.INVISIBLE
        val results = wifiManager.scanResults
       displayScanResults(context, results)
    }

    // スキャン失敗時
    private fun scanFailure() {
        Log.i("Scan", "Failure")
        tvFailure.visibility = View.VISIBLE
    }

    // スキャン結果を反映
    private fun displayScanResults(context: Context, responses:MutableList<ScanResult>?){
        Log.i("Responses", responses.toString())

        val apInfo = ArrayList<APInfo>()

        for (res in responses!!){
            val apData = APInfo(
                res.SSID,
                res.BSSID,
                res.level,
                res.frequency
            )
            apInfo.add(apData)
        }

        // 取得時刻の表示
        val localDateTime = LocalDateTime.now()
        val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")
        tvTimestamp.text = localDateTime.format(dtf)
        Log.i("datetime", localDateTime.format(dtf))

        // ListView
        val adapter = APInfoAdapter(context, apInfo)
        lvAPInfo.adapter = adapter
    }
}