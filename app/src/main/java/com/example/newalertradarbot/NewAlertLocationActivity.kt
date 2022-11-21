package com.example.newalertradarbot


import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newalertradarbot.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class NewAlertLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var saveAlert: AppCompatButton

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_new_alert_location)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)
        saveAlert  = findViewById(R.id.btAlertMap)
        saveAlert.setOnClickListener{
            val intent = Intent(this,MapsActivity::class.java)
            intent.putExtra("OptionName","MapsActivity")
            Toast.makeText(this, "Alerta enviada", Toast.LENGTH_LONG).show()
            startActivity(intent)

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val mockLocate = LatLng(40.23072225476209, -3.989952850355164)
        mMap.animateCamera( newLatLngZoom(mockLocate, 20f),3000,null
        )
    }
    fun ChangeType(view: View) {
        if(mMap.mapType == GoogleMap.MAP_TYPE_NORMAL){
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        }
        else if(mMap.mapType == GoogleMap.MAP_TYPE_HYBRID){
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }
        else if(mMap.mapType == GoogleMap.MAP_TYPE_SATELLITE){
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }
    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if (!::mMap.isInitialized)
            return
        if (isLocationPermissionGranted()){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mMap.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }

}