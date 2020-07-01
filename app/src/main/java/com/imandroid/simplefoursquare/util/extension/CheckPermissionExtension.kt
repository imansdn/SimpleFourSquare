package com.imandroid.simplefoursquare.util.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import android.widget.Toast

fun Context.toast(@StringRes messageRes: Int) {
    Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
}

fun Context.toast(messageRes: String) {
    Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
}

fun Context.checkLocationPermission(): Boolean {
    val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION
    )
    return permission == PackageManager.PERMISSION_GRANTED
}