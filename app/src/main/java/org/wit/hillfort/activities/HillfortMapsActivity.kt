package org.wit.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hillfort.R
import kotlinx.android.synthetic.main.activity_hillfort_maps.*
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.main.MainApp


class HillfortMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var map: GoogleMap
    lateinit var app: MainApp


    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        setContentView(R.layout.activity_hillfort_maps)
        toolbar.title = title
        setSupportActionBar(toolbar)


        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            configureMap()
        }

    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        var Hillforts = app.hillforts.findAll()
        val currentHill = Hillforts.find{ it.title == marker.title }
        currentTitle.text = currentHill!!.title
        currentDescription.text = currentHill!!.description
        currentImage.setImageBitmap(readImageFromPath(this, currentHill!!.image))
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    fun configureMap() {
        map.uiSettings.setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(this)
        app.hillforts.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }

    }

}



