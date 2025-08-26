package com.example.turismoapp.util

import android.content.Context
import android.widget.TextView
import com.example.turismoapp.R
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

object WeatherClient {
    private val io = Executors.newSingleThreadExecutor()

    // Pon tu API key de OpenWeatherMap; si lo dejas así, no hace la llamada
    private const val API_KEY = "TU_API_KEY"

    fun updateWeatherLabel(
        context: Context,
        lat: Double?,
        lon: Double?,
        label: TextView
    ) {
        if (lat == null || lon == null || API_KEY == "TU_API_KEY") return

        io.execute {
            try {
                val url = URL(
                    "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$API_KEY&units=metric&lang=es"
                )
                val conn = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "GET"
                    connectTimeout = 8000
                    readTimeout = 8000
                }

                val body = conn.inputStream.bufferedReader().use { it.readText() }
                conn.disconnect()

                val json = JSONObject(body)
                val temp = json.getJSONObject("main").getDouble("temp")
                val desc = json.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description")

                label.post {
                    label.text = context.getString(R.string.weather_format, temp, desc)
                }
            } catch (_: Exception) {
                // si falla, no hacemos nada; podrías registrar con Log.e si quieres
            }
        }
    }
}

