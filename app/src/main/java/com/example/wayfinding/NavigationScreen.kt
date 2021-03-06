package com.example.wayfinding


import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.wayfinding.classes.CheckPoint
import com.example.wayfinding.classes.Map
import com.example.wayfinding.classes.Node
import org.altbeacon.beacon.*
import java.util.*
import kotlin.collections.HashMap


// Code from the library reference for android beacon byu David Young
// https://github.com/davidgyoung/android-beacon-library-reference-kotlin
class NavigationScreen : AppCompatActivity() {
    lateinit var region: Region
    var map = Map()
    var checkPoints = Hashtable<String,CheckPoint>()
    var obstacles = Hashtable<Int, IntArray>()
    var checkPointDef = HashMap<String,String>()
    var currentRegion : CheckPoint? = null
    lateinit var path : Stack<Node>
    lateinit var frame : RelativeLayout
    var pointUUID : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_screen)
        checkPermissions()


        checkPointDef["Elevator"] = "c336aa38-054b-b048-3b0a-e75027061970"
        checkPointDef["Service Desk"] = "c336aa38-054b-b048-3b0a-e75027061971"
        checkPointDef["Entrance"] = "c336aa38-054b-b048-3b0a-e75027061972"
        checkPointDef["c336aa38-054b-b048-3b0a-e75027061972"] = "Entrance"
        checkPointDef["c336aa38-054b-b048-3b0a-e75027061971"] = "Service Desk"
        checkPointDef["c336aa38-054b-b048-3b0a-e75027061970"] = "Elevator"

        checkPoints["c336aa38-054b-b048-3b0a-e75027061970"] = CheckPoint("Elevator","c336aa38-054b-b048-3b0a-e75027061970",intArrayOf(20,23))
        checkPoints["c336aa38-054b-b048-3b0a-e75027061971"] = CheckPoint("Service Desk","c336aa38-054b-b048-3b0a-e75027061971", intArrayOf(14,27))
        var defaultPoint = CheckPoint("Entrance","c336aa38-054b-b048-3b0a-e75027061972", intArrayOf(20,28))
        checkPoints["c336aa38-054b-b048-3b0a-e75027061972"] = defaultPoint
        currentRegion = defaultPoint

        var obs = parseString("(20,24):(21,24):(22,24):(23,24):(16,25):(20,25):(21,25):(22,25):(23,25):(16,26):(16,29):(17,29):(18,29):(19,29)")
        for (s in obs){
            val split = s.split(",")
            val x = split[0].substring(1)
            val y = split[1].substring(0,split.get(1).length-1)
            putObstacles(Integer.parseInt(x),Integer.parseInt(y),obstacles)
        }
        map.setObstacles(obstacles)
        map.setCheckPoints(checkPoints)

        val desination = intent.getStringExtra("destination")
        pointUUID = checkPointDef.getValue(desination.toString())
        path = map.findPath(currentRegion!!.uuid,pointUUID)

        val beaconManager = BeaconManager.getInstanceForApplication(this)

        beaconManager.getBeaconParsers().add(BeaconParser().
                setBeaconLayout("m:0-1=4c00,i:2-24v,p:24-24"));

        // The example shows how to find iBeacon.
        beaconManager.getBeaconParsers().add(
                BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))


        // The code below will start "monitoring" for beacons matching the region definition below
        // the region definition is a wildcard that matches all beacons regardless of identifiers.
        // if you only want to detect beacons with a specific UUID, change the id1 paremeter to
        // a UUID like Identifier.parse("2F234454-CF6D-4A0F-ADF2-F4911BA9FFA6")
        val region = Region("radius-uuid", null, null, null)
        beaconManager.startRangingBeacons(region)
//        beaconManager.getRegionViewModel(region).rangedBeacons.observe(this,rangingObserver)
        // These two lines set up a Live Data observer so this Activity can get beacon data from the Application class
        val regionViewModel = BeaconManager.getInstanceForApplication(this).getRegionViewModel(region)
        regionViewModel.rangedBeacons.observeForever(rangingObserver)
        var canvas = GrowingLine(this,null,path, checkPointDef)
        frame = findViewById<RelativeLayout>(R.id.canvasFrame)
        frame.addView(canvas)
    }

    private val rangingObserver = Observer<Collection<Beacon>> { beacons ->
        Log.d("output", "Detected " + beacons.count())
        var currentClosest = Double.MAX_VALUE
        for (beacon: Beacon in beacons) {
            val distance = beacon.distance
            val s = beacon.id1.toString()
            if (checkPoints.containsKey(s) && distance < 2.5) {
                Log.d("output", beacon.id1.toString())
                if (currentRegion != checkPoints.getValue(s) && currentClosest > distance) {
                    currentRegion = checkPoints.getValue(s)
                    currentClosest = distance
                    path = map.findPath(currentRegion!!.uuid, pointUUID)
                    var canvas = GrowingLine(this, null, path, checkPointDef)

                    frame = findViewById<RelativeLayout>(R.id.canvasFrame)
                    frame.addView(canvas)
                }
            }
//            if(beacon.id1.toString().contentEquals("c336aa38-054b-b048-3b0a-e75027061971")) {
//                Log.d("output", "HERE")
//                Log.d("output", beacon.id1.toString())
        }
    }

    fun hash(x: Int, y: Int): Int {
        return x * 40 + y
    }
    fun putObstacles(x: Int, y: Int,table: Hashtable<Int,IntArray>){
        table[hash(x,y)] = intArrayOf(x,y)
    }
    fun parseString(s: String): List<String>{
        var string = s.split(":")
        return string
    }
    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }
    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in 1..permissions.size-1) {
            Log.d(TAG, "onRequestPermissionResult for "+permissions[i]+":" +grantResults[i])
        }
    }

    fun checkPermissions() {
        // basepermissions are for M and higher
        var permissions = arrayOf( Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        var permissionRationale ="This app needs both fine location permission and background location permission to detect beacons in the background.  Please grant both now."
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            permissions = arrayOf( Manifest.permission.ACCESS_FINE_LOCATION)
            permissionRationale ="This app needs fine location permission and nearby devices permission to detect beacons.  Please grant this now."
        }
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            // Uncomment line below if targeting SDK 31
            // permissions = arrayOf( Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_SCAN)
            permissionRationale ="This app needs both fine location permission and nearby devices permission to detect beacons.  Please grant both now."
        }
        var allGranted = true
        for (permission in permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) allGranted = false;
        }
        if (!allGranted) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                val builder =
                        AlertDialog.Builder(this)
                builder.setTitle("This app needs permissions to detect beacons")
                builder.setMessage(permissionRationale)
                builder.setPositiveButton(android.R.string.ok, null)
                builder.setOnDismissListener {
                    requestPermissions(
                            permissions,
                            PERMISSION_REQUEST_FINE_LOCATION
                    )
                }
                builder.show()
            }
            else {
                val builder =
                        AlertDialog.Builder(this)
                builder.setTitle("Functionality limited")
                builder.setMessage("Since location and device permissions have not been granted, this app will not be able to discover beacons.  Please go to Settings -> Applications -> Permissions and grant location and device discovery permissions to this app.")
                builder.setPositiveButton(android.R.string.ok, null)
                builder.setOnDismissListener { }
                builder.show()
            }
        }
        else {
            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                if (checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                ) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                        val builder =
                                AlertDialog.Builder(this)
                        builder.setTitle("This app needs background location access")
                        builder.setMessage("Please grant location access so this app can detect beacons in the background.")
                        builder.setPositiveButton(android.R.string.ok, null)
                        builder.setOnDismissListener {
                            requestPermissions(
                                    arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                                    PERMISSION_REQUEST_BACKGROUND_LOCATION
                            )
                        }
                        builder.show()
                    } else {
                        val builder =
                                AlertDialog.Builder(this)
                        builder.setTitle("Functionality limited")
                        builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.")
                        builder.setPositiveButton(android.R.string.ok, null)
                        builder.setOnDismissListener { }
                        builder.show()
                    }
                }
            }
        }
    }

    companion object {
        val TAG = "MainActivity"
        val PERMISSION_REQUEST_BACKGROUND_LOCATION = 0
        val PERMISSION_REQUEST_FINE_LOCATION = 1
    }

}