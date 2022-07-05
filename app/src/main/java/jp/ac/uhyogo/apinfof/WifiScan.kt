package jp.ac.uhyogo.apinfof

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

object WifiScan {
    lateinit var job: Job

    @OptIn(DelicateCoroutinesApi::class)
    fun start(){
        job = GlobalScope.launch {
            while (true) {
                //Wifi情報の取得
                MainActivity.wifiManager.startScan()

                //一定時間待機（操作読み取りのため）
                delay(CommonManager.interval)
            }
        }
    }

    fun stop(){
        job.cancel()
    }
}