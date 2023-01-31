package com.example.forest_firefighters

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.forest_firefighters.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity() : AppCompatActivity(), OnMapReadyCallback {

    private var latitud: Double = 0.0
    private var longitud: Double = 0.0
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var ultimoMarcador: Marker? = null
    private lateinit var handler: DBHelper

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtenga SupportMapFragment y reciba una notificación cuando el mapa esté listo para usarse.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        handler = DBHelper(this)

    }

    @SuppressLint("Recycle")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        enableLocation()
        todosMarcadores(this)

        val cyl = LatLng(41.7544, -4.7819)
        mMap.addMarker(
            MarkerOptions()
                .position(cyl)
                .visible(false)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cyl,7f))

        mMap.setOnMapLongClickListener {

            val markerOptions = MarkerOptions().position(it)
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_llama3))

            //borra el marcador anterior si no ha sido registrado
            if (ultimoMarcador != null)
                ultimoMarcador!!.remove()
            ultimoMarcador = mMap.addMarker(markerOptions)
            mMap.animateCamera(CameraUpdateFactory.newLatLng(it))
            latitud = it.latitude
            longitud = it.longitude
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.fragment_menu_incendio,null)
            val btnAniadir: TextView = view.findViewById(R.id.nuevoIncendio)
            val btnModificar: TextView = view.findViewById(R.id.btnModificar)
            val btnEliminar: TextView = view.findViewById(R.id.eliminar)
            btnAniadir.isEnabled
            btnModificar.isEnabled = false
            btnEliminar.isEnabled = false

            builder.setView(view)
            //Creando dialog
            val dialogo = builder.create()

            btnAniadir.setOnClickListener {
                startActivity(Intent(this,RegistrarIncendioActivity::class.java)
                    .putExtra("latitud",latitud)
                    .putExtra("longitud",longitud))
            }
            dialogo.show()
        }

        mMap.setOnMarkerClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.fragment_menu_incendio,null)
            val btnAniadir: TextView = view.findViewById(R.id.nuevoIncendio)
            val btnModificar: TextView = view.findViewById(R.id.btnModificar)
            val btnEliminar: TextView = view.findViewById(R.id.eliminar)

            latitud = it.position.latitude
            longitud = it.position.longitude


            btnAniadir.isEnabled = false
            btnModificar.isEnabled
            btnEliminar.isEnabled

            btnModificar.setOnClickListener {
                startActivity(Intent(this,ModificarIncendioActivity::class.java)
                    .putExtra("latitud", latitud)
                    .putExtra("longitud", longitud))
            }

            btnEliminar.setOnClickListener {

            val db = handler.readableDatabase
            val c: Cursor = db.rawQuery("SELECT * FROM incendio WHERE latitud = ${latitud} AND longitud = ${longitud}",null)
            if(c.moveToFirst() == true){
                do {
                    val marcador: String = c.getString(1)
                    handler.eliminar(nombre_incendio = marcador)
                    Toast.makeText(this, "Incendio eliminado correctamente", Toast.LENGTH_SHORT).show()
                    mMap.clear()
                    todosMarcadores(this)
                }while (c.moveToNext())
            }
            }
            builder.setView(view)
            //Creando dialog
            val dialogo = builder.create()

            dialogo.show()
            true
        }

    }

    fun lectura(context: Context): SQLiteDatabase {
        val dbHelper = DBHelper(context)
        return dbHelper.readableDatabase
    }

    @SuppressLint("Recycle")
    fun todosMarcadores(context: Context){
        val c: Cursor = lectura(context).rawQuery("SELECT * FROM incendio", null)
        if(c.moveToFirst() == true) {
            do {
                val titulo: String = c.getString(1)
                val latitud: Double = c.getDouble(10)
                val longitud: Double = c.getDouble(11)
                val incendio = LatLng(latitud,longitud)
                if(::mMap.isInitialized){
                    mMap.addMarker(MarkerOptions().position(incendio).title(titulo).icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_llama3)))
                }

            }while (c.moveToNext())

        }
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if (!::mMap.isInitialized) return
        if (isLocationPermissionGranted()) {
            mapSettings()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapSettings()
            } else {
                Toast.makeText(
                    this,
                    "Para activar la localización ve a ajustes y acepta los permisos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::mMap.isInitialized) return
        if (!isLocationPermissionGranted()) {
            mapSettings()
            Toast.makeText(
                this,
                "Para activar la localización ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun mapSettings() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

    }
}






