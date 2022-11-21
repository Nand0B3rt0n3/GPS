package com.example.newalertradarbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class NewAlert : AppCompatActivity() {

    private lateinit var buttonSelectLocation : AppCompatButton
    private lateinit var buttonSendAlert : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_alert)
        buttonSelectLocation = findViewById(R.id.btSelectLocation)
        buttonSelectLocation.setOnClickListener {
            val intent = Intent(this,NewAlertLocationActivity::class.java)
            Toast.makeText(this, "Selecciona la nueva ubicacion", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

        buttonSendAlert = findViewById(R.id.btAddAlert)
        buttonSendAlert.setOnClickListener{
            val intent = Intent(this,MapsActivity::class.java)
            intent.putExtra("OptionName","MapsActivity")
            Toast.makeText(this, "Alerta enviada", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
    }
}