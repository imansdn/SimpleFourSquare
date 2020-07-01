package com.imandroid.simplefoursquare.screen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imandroid.simplefoursquare.R
import com.imandroid.simplefoursquare.service.TrackingService
import com.imandroid.simplefoursquare.util.LOCATION_PERMISSIONS_REQUEST
import com.imandroid.simplefoursquare.util.PermissionUtils
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /** start location tracking service */
        PermissionUtils.Builder()
            .key(LOCATION_PERMISSIONS_REQUEST)
            .permission(PermissionUtils.PermissionEnum.ACCESS_COARSE_LOCATION)
            .permission(PermissionUtils.PermissionEnum.ACCESS_FINE_LOCATION)
            .callback { allPermissionsGranted, somePermissionsDeniedForever ->
                when {
                    allPermissionsGranted -> {

                        startTrackingService()

                    }
                    somePermissionsDeniedForever -> {
                        PermissionUtils.openApplicationSettings(
                            this,
                            this.packageName
                        )
                    }
                }
            }.ask(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTrackingService()
    }
    private fun stopTrackingService() {
        Timber.i("stopTrackingService")
        stopService(Intent(this, TrackingService::class.java))
    }

    private fun startTrackingService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            startForegroundService(Intent(this, TrackingService::class.java))
        } else {
            startService(Intent(this, TrackingService::class.java))

        }
        Timber.i("startTrackingService")
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==LOCATION_PERMISSIONS_REQUEST){
            Timber.i("🔑 we got the location permission requestCode = $requestCode")
            startTrackingService()
        }

    }
}