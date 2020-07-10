package com.imandroid.simplefoursquare.service


import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.android.gms.location.*
import com.imandroid.simplefoursquare.data.ExploreRepository
import com.imandroid.simplefoursquare.data.db.DatabaseGenerator
import com.imandroid.simplefoursquare.data.db.ExploreDbDataImpl
import com.imandroid.simplefoursquare.data.network.ExploreApiDataImpl
import com.imandroid.simplefoursquare.data.sharedPref.SharedPrefHelper
import com.imandroid.simplefoursquare.screen.MainActivity
import com.imandroid.simplefoursquare.util.*
import com.imandroid.simplefoursquare.util.extension.checkLocationPermission
import com.imandroid.simplefoursquare.util.extension.disposedBy
import com.imandroid.simplefoursquare.util.extension.distanceBetween
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import com.imandroid.simplefoursquare.R



/**
 * Service to track the location updates in the background.
 */
class TrackingService : Service() {
    var bag = CompositeDisposable()

    private lateinit var repository: ExploreRepository
    private var client: FusedLocationProviderClient? = null
    private lateinit var sharedPrefHelper: SharedPrefHelper
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("TrackingService onCreate")
        repository = ExploreRepository(
            api = ExploreApiDataImpl(),
            db = ExploreDbDataImpl(DatabaseGenerator.getInstance(baseContext).exploreDao),
            sharedPrefHelper = SharedPrefHelper.getInstance(baseContext),
            errorListener = {errorHandling(it)}
        )
        sharedPrefHelper = SharedPrefHelper.getInstance(baseContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buildNotification()
        }

        requestLocationUpdates()

    }
    private fun errorHandling(message:String){
     //send error with broadcast to change the loading state
    }

    /**
     * Initiate the request to track the device's location
     */
    private fun requestLocationUpdates() {
        client = LocationServices.getFusedLocationProviderClient(this)
        //If the app currently has access to the location permission.
        if (checkLocationPermission()) {
            Timber.i("checkLocationPermission was true and start requestLocationUpdates")
            client?.lastLocation
                ?.addOnSuccessListener { location : Location? ->
                    // Got last known location. In some rare situations this can be null.
                    sendBroadcast(Intent(BROADCAST_UPDATE_DISTANCE).putExtra(DISTANCE, 0))
                    if (location!=null){
                        //call for first time with last known location
                        tempLocation = location
                        Timber.d("call for first time with last known location")
                        sendBroadcast(Intent(BROADCAST_UPDATE_EXPLORE_LIST)
                            .putExtra(LATLONG, "${location.latitude},${location.longitude}")
                            .putExtra(IS_NEED_CLEAR, true))
                    }else{
//                        call for first time if there is no last location with sample lat lng
                        Timber.d("tempLocation == null so call with sample lat long!")
//                        sendBroadcast(Intent(BROADCAST_UPDATE_EXPLORE_LIST)
//                            .putExtra(LATLONG, SAMPLE_LAT_LNG)
//                            .putExtra(IS_NEED_CLEAR, true))
                    }
                }

            client?.requestLocationUpdates(getLocationRequest(), locationListener, null)
        }
    }

    private val locationListener = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            Timber.i("onLocationResult...")
            val location = locationResult?.lastLocation
            if (location != null) {
                Timber.e("lastLocation is :$location")
                checkNewLocationDistance(location)

            }else{
                Timber.e("lastLocation is null!!")
            }
        }
    }


    //Temp variable to store last value of the location to calculate the distance.
    private var tempLocation: Location? = null

    /**
     * Check if the distance between the current location and previous location is equal or greater
     * than [DISTANCE_TO_SEARCH_NEW_EXPLORE] and accuracy lower than [MINIMUM_BLOCK_SIZE] .
     */
    fun checkNewLocationDistance(location: Location) {
        Timber.d("Got a now location: $location")
        if (tempLocation == null) {
            tempLocation = location
            sendBroadcast(Intent(BROADCAST_UPDATE_EXPLORE_LIST)
                .putExtra(LATLONG, "${location.latitude},${location.longitude}")
                .putExtra(IS_NEED_CLEAR, true))
            sendBroadcast(Intent(BROADCAST_UPDATE_DISTANCE).putExtra(DISTANCE, 0))
        } else {
            val distance = tempLocation!!.distanceBetween(location)
            sharedPrefHelper.write(SH_DISTANCE,distance.toFloat())
            sendBroadcast(Intent(BROADCAST_UPDATE_DISTANCE).putExtra(DISTANCE, distance) .putExtra(IS_NEED_CLEAR, true))
            if (location.accuracy <= MIN_ACCURACY && distance >= DISTANCE_TO_SEARCH_NEW_EXPLORE ) {
                Timber.d("The user walked $DISTANCE_TO_SEARCH_NEW_EXPLORE, get new data and save it.")
                tempLocation = location
                //call for every 100 meter
                sendBroadcast(Intent(BROADCAST_UPDATE_EXPLORE_LIST)
                    .putExtra(LATLONG, "${location.latitude},${location.longitude}")
                    .putExtra(IS_NEED_CLEAR, true))
                sendBroadcast(Intent(BROADCAST_UPDATE_DISTANCE).putExtra(DISTANCE, 0))

            }
        }
    }

    private fun getLocationRequest(): LocationRequest {
        val request = LocationRequest()

        //Specify how often the app should request the location
        request.interval = REQUEST_LOCATION_INTERVAL

        //Get the most accurate location data available.
        request.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        return request
    }


    override fun onDestroy() {
        super.onDestroy()
        //Make sure to clean the location listener when the service is finished.
        client?.removeLocationUpdates(locationListener)

        //Clear the walk photos and data.
        repository.clear()
        bag.dispose()
        bag.clear()
    }

    companion object {
        // X seconds interval to request location update.
        private const val REQUEST_LOCATION_INTERVAL = 10 * 1000L

        //Minimum location accuracy in meters. If the location accuracy greater than this value
        //Mean the location predication will have a bigger error.
        private const val MIN_ACCURACY = 25

        //The distance in meters that after it we will request place.
        private const val DISTANCE_TO_SEARCH_NEW_EXPLORE = 100

        private const val NOTIFICATION_ID = 838
        const val EXTRA_SERVICE_WORKING = "extra_service_working"
    }


    private fun buildNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        //Put extra to the activity to know if the serivce is running or not.
        val pendingIntent = PendingIntent.getActivity(this, 0,
            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, getNotificationChannelId())
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.tracking_walk_working))
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
        startForeground(NOTIFICATION_ID, builder.build())
    }

    /**
     * Create a notification channel when the device API level is above O.
     */
    private fun getNotificationChannelId(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId = getString(R.string.tracking_notification_channel_id),
                channelName = getString(R.string.tracking_notification_channel_name))
        } else {
            // If earlier version channel ID is not used
            ""
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_DEFAULT)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
    }


}
