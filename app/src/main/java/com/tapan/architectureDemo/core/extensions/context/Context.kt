package com.tapan.architectureDemo.core.extensions.context

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View

/**
 * Network Status helper
 */
val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun View.visible(){
    this.visibility = View.VISIBLE
}
fun View.gone(){
    this.visibility = View.GONE
}
