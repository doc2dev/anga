package com.doc2dev.anga

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.doc2dev.anga.data.local.AngaDb
import com.doc2dev.anga.data.local.dao.WeatherDao
import com.doc2dev.anga.data.local.di.localModule
import com.doc2dev.anga.data.remote.WeatherApiService
import com.doc2dev.anga.data.remote.di.networkModule
import com.doc2dev.anga.data.remote.models.CurrentWeatherJson
import com.doc2dev.anga.data.remote.models.ForecastWrapper
import com.doc2dev.anga.domain.models.CurrentWeather
import com.doc2dev.anga.domain.models.Forecast
import com.doc2dev.anga.domain.repository.WeatherRepository
import com.doc2dev.anga.domain.repository.di.weatherRepoModule
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import org.robolectric.RobolectricTestRunner

/**
 * Integration test for WeatherRepository. We simulate a fetch from API and assert for the
 * expected data in the local DB.
 * */
@KoinApiExtension
@RunWith(RobolectricTestRunner::class)
class WeatherRepositoryTest : KoinComponent {
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<AngaApp>()
        val mockDatabase = Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            AngaDb::class.java,
        )
            .allowMainThreadQueries()
            .build()
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        val currentWeatherResponse =
            gson.fromJson(CURRENT_WEATHER_RESPONSE, CurrentWeatherJson::class.java)
        val forecastResponse = gson.fromJson(FORECAST_RESPONSE, ForecastWrapper::class.java)
        val apiService = object : WeatherApiService {
            override suspend fun getCurrentWeather(): CurrentWeatherJson = currentWeatherResponse

            override suspend fun getForecast(): ForecastWrapper = forecastResponse
        }
        val mockModule = module {
            single(override = true) { mockDatabase as AngaDb }
            single(override = true) { apiService as WeatherApiService }
        }
        val modules = listOf(
            networkModule,
            localModule,
            weatherRepoModule,
        )
        stopKoin()
        startKoin {
            androidContext(context)
            modules(modules)
        }
        loadKoinModules(mockModule)
        weatherRepository = inject(WeatherRepository::class.java).value
    }

    @Test
    fun `should fetch from API and save correctly to DB`() {
        runBlocking {
            weatherRepository.refreshWeather()
        }
        val dao = inject(WeatherDao::class.java).value
        var currentWeather: CurrentWeather
        var fiveDayForecast: List<Forecast>
        runBlocking {
            currentWeather = dao.getLatestWeather().first()
            fiveDayForecast = dao.getFiveDayForecast().first()
        }
        with(currentWeather) {
            assertThat(weatherSummary, `is`("Clouds"))
            assertThat(weatherDescription, `is`("few clouds"))
            assertThat(currentTemperature, `is`(13.62))
        }
        assertThat(fiveDayForecast.size, `is`(5))
        val secondDayForecast = fiveDayForecast[1]
        assertThat(secondDayForecast.dayOfWeek, `is`("FRIDAY"))
        val fifthDayForecast = fiveDayForecast.last()
        assertThat(fifthDayForecast.temperature, `is`(21.03))
    }
}