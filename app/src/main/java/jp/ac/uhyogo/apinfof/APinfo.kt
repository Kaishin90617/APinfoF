package jp.ac.uhyogo.apinfof

data class APInfo(
    val ssid        : String,
    val address     : String?,
    val rssi        : Int?,
    val frequency   : Int?
)
