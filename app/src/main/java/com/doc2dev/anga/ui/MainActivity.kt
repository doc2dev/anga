package com.doc2dev.anga.ui

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.doc2dev.anga.R
import com.doc2dev.anga.databinding.ActivityMainBinding
import com.doc2dev.anga.databinding.TempDisplayBinding
import com.doc2dev.anga.domain.models.CurrentWeather
import com.doc2dev.anga.ui.viewmodel.WeatherViewModel
import com.google.android.gms.location.*
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.math.round


const val RC_LOCATION  = 200

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val weatherViewModel: WeatherViewModel by viewModel()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val permission = Manifest.permission.ACCESS_FINE_LOCATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding!!.root)
        with(binding!!) {
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
        extractColorsFromImage()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLatestWeather()
        checkPermissionAndGetLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getLocationUpdates() {
        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                Timber.tag("FOO").d("Received location")
                locationResult.locations.firstOrNull()?.let { location ->
                    with (location) {
                        weatherViewModel.saveLocation(latitude.toFloat(), longitude.toFloat())
                    }
                    weatherViewModel.refreshWeather()
                }
            }
        }
        val request = LocationRequest.create()
        request.fastestInterval = 60 * 15
        fusedLocationClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    @AfterPermissionGranted(RC_LOCATION)
    fun checkPermissionAndGetLocation() {
        if (EasyPermissions.hasPermissions(this, permission)) {
            Timber.tag("FOO").d("Permission was previously granted")
            getLocationUpdates()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your location to function properly",
                RC_LOCATION,
                permission
            )
        }
    }

    private fun getLatestWeather() {
        Timber.tag("FOO").d("Getting weather...")
        weatherViewModel.weatherLiveData.observe(this) {
            Timber.tag("FOO").d("Received current weather: $it")
            if (it != null) {
                showCurrentWeather(it)
            }
        }
        weatherViewModel.messageLiveData.observe(this) {
            Timber.tag("FOO").d("Error message: $it")
        }
        weatherViewModel.getLatestWeather()
    }

    private fun showCurrentWeather(weather: CurrentWeather) {
        with(binding!!) {
            dayWeatherFrame.hideShimmer()
            forecastFrame.hideShimmer()
            val summary = weather.weatherSummary.toLowerCase()
            var backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.bg_sunny)
            var drawableId = R.drawable.forest_sunny
            if (summary.contains("cloud")) {
                backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.bg_cloudy)
                drawableId = R.drawable.forest_cloudy
            } else if (summary.contains("rain")) {
                backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.bg_rainy)
                drawableId = R.drawable.forest_rainy
            }
            weatherImage.setImageResource(drawableId)
            root.setBackgroundColor(backgroundColor)
            val roundedTemp = round(weather.currentTemperature).toInt().toString()
            tempTextView.text = "$roundedTemp°"
            weatherTextView.text = weather.weatherDescription
            showTemperature(minTempDisplay, weather.minTemperature, "min")
            showTemperature(currentTempDisplay, weather.currentTemperature, "current")
            showTemperature(maxTempDisplay, weather.maxTemperature, "max")
            extractColorsFromImage()
        }
    }

    private fun showTemperature(binding: TempDisplayBinding, temp: Double, tempType: String) {
        with (binding) {
            val roundedTemp = round(temp).toInt().toString()
            tempTextView.text = "$roundedTemp°"
            tempTypeTextView.text = tempType
        }
    }

    private fun extractColorsFromImage() {
        val drawable = binding!!.weatherImage.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val builder = Palette.Builder(bitmap).setRegion(0, 0, 100, 30)
        builder.generate {
            it?.let { palette ->
                val default = ContextCompat.getColor(this, R.color.black)
                setColors(palette.swatches[0].rgb)
            }
        }
    }

    private fun setColors(color: Int) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}