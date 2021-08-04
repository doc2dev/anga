package com.doc2dev.anga.data.remote

import android.content.Context
import com.doc2dev.anga.BuildConfig
import com.doc2dev.anga.R
import com.doc2dev.anga.data.local.preferences.Preferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create(buildGson()))
        .client(buildOkClient())
        .build()
}

fun buildGson(): Gson = GsonBuilder()
    .excludeFieldsWithoutExposeAnnotation()
    .create()

fun buildOkClient(): OkHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(buildQueryParamInterceptor())
    .addInterceptor(buildLoggingInterceptor())
    .build()

fun buildQueryParamInterceptor(): Interceptor = Interceptor { chain ->
    val original = chain.request()
    val originalHttpUrl = original.url
    val prefs = inject(Preferences::class.java).value
    val context = inject(Context::class.java).value
    val apiKey = context.resources.getString(R.string.weather_api_key)
    val newUrl = originalHttpUrl.newBuilder()
        .addQueryParameter("lat", prefs.getLatitude().toString())
        .addQueryParameter("lon", prefs.getLongitude().toString())
        .addQueryParameter("appId", apiKey)
        .addQueryParameter("units", "metric")
        .build()
    val requestBuilder = original.newBuilder()
        .url(newUrl)
    return@Interceptor chain.proceed(requestBuilder.build())
}

fun buildLoggingInterceptor(): Interceptor = HttpLoggingInterceptor { message ->
    Timber.tag("OkHttp").d(message)
}.apply {
    level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}
