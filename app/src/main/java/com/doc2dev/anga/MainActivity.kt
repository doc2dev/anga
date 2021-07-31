package com.doc2dev.anga

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.doc2dev.anga.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding!!.root)
        with(binding!!) {
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
        extractColorsFromImage()
    }

    private fun extractColorsFromImage() {
        val drawable = binding!!.weatherImage.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val builder = Palette.Builder(bitmap).setRegion(0, 0, 100, 30)
        builder.generate {
            Timber.d("Palette returning")
            it?.let { palette ->
                Timber.d("Palette generated")
                val default = ContextCompat.getColor(this, R.color.black)
                setColors(palette.getLightVibrantColor(default))
            }
        }
    }

    private fun setColors(color: Int) {
        //binding!!.dayWeatherFrame.setBackgroundColor(color)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}