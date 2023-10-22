package com.example.mypub

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        initToolbar()
        initMap()

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
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(52.43339094662937, 16.937463959198137))
                .title("Marker")
        )
        val point : CameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(52.43339094662937, 16.937463959198137), 16F)

        googleMap.moveCamera(point)
        googleMap.animateCamera(point)

        val toast = Toast.makeText(applicationContext, "Chuj ci w dupę", Toast.LENGTH_LONG)
        toast.show()
    }

}