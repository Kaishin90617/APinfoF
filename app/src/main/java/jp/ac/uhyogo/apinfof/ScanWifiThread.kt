package jp.ac.uhyogo.apinfof

import jp.ac.uhyogo.apinfof.MainActivity.Companion.interval
import jp.ac.uhyogo.apinfof.MainActivity.Companion.wifiManager

class ScanWifiThread: Thread() {
    //start()により呼び出されるメソッド
    override fun run() {
        while (true) {
            //Wifi情報の取得
            wifiManager.startScan()

            //一定時間待機（操作読み取りのため）
            sleep(interval)
        }
    }
}