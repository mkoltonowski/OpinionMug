package com.example.mypub

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.mypub.database.DatabaseHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var gMap: GoogleMap;
    val dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest
            .Builder(Priority.PRIORITY_HIGH_ACCURACY, 300)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { location: Location ->
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 16f);
                    gMap.animateCamera(cameraUpdate)
                }
            }
        }
        initToolbar()
        initMap()

        dbHelper.clearDatabase();
        dbHelper.getLocations();
        dbHelper.addLocation(com.example.mypub.database.entity.Location(1, "Test", "Test"));
        dbHelper.getLocations();
        dbHelper.addHistory(1, "2021-05-01", "Test");
        dbHelper.getHistory(1);
        dbHelper.addGrade(1, 5);
        dbHelper.getGrades(1);
        dbHelper.getAverageGrade(1);
        dbHelper.getBestGrade(1);
        dbHelper.getWorstGrade(1);
    }

    private fun initMap() {
        mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerView, mapFragment)
            .commit()

        mapFragment.getMapAsync(this)
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("MapDebug", "onMapReady called")
        gMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }
        gMap.isMyLocationEnabled = true;


        val toast = Toast.makeText(
            applicationContext,
            "Witam cię przyjacielu, znalazłem cię :)",
            Toast.LENGTH_LONG
        )
        toast.show()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}