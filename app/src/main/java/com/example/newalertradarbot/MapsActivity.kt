package com.example.newalertradarbot


import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newalertradarbot.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnMyLocationButtonClickListener, OnMyLocationClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var newAlertButton: AppCompatImageButton

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        enableLocation()
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
        val mockLocate = LatLng(40.23072225476209, -3.989952850355164)
        mMap.animateCamera( newLatLngZoom(mockLocate, 20f),3000,null
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        newAlertButton  = findViewById(R.id.btAlert)
        newAlertButton.setOnClickListener{
            val intent = Intent(this,NewAlert::class.java)
            intent.putExtra("OptionName","newAlert")
            startActivity(intent)
        }
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
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        )
        {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(
                grantResults.isNotEmpty()
                && grantResults[0]==PackageManager.PERMISSION_GRANTED
            ){
                mMap.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localizacion GPS, ve a ajustes y acepta los permisos", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Mostrando tu posición", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this,"Estas en ${p0.latitude}, ${p0.longitude}",Toast.LENGTH_SHORT).show()
    }
}