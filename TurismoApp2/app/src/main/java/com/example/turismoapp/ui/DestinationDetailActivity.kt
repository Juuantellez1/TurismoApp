package com.example.turismoapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.turismoapp.R
import com.example.turismoapp.data.JsonLoader
import com.example.turismoapp.databinding.ActivityDestinationDetailBinding
import com.example.turismoapp.model.Destination
import com.example.turismoapp.util.WeatherClient

class DestinationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDestinationDetailBinding
    private var destination: Destination? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadDestination()
        setupFavoriteButton()
        fetchWeatherIfPossible()
    }

    private fun loadDestination() {
        // 👇 Leer el extra como Int (coincide con tu modelo y con lo que envías)
        val id = intent.getIntExtra("dest_id", -1)
        if (id == -1) {
            Toast.makeText(this, "Destino no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        destination = JsonLoader.loadDestinations(this).firstOrNull { it.id == id }

        if (destination == null) {
            Toast.makeText(this, "Destino no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val d = destination!!
        binding.tvTitle.text = d.nombre
        binding.tvCategory.text = getString(R.string.category_format, d.categoria)
        binding.tvCountry.text = getString(R.string.country_format, d.pais)
        binding.tvDescription.text = d.descripcion
        binding.tvRecommended.text = getString(R.string.recommended_format, d.actividadRecomendada)
    }

    private fun setupFavoriteButton() {
        val already = MainActivity.favorites.any { it.id == destination?.id }
        binding.btnAddFavorite.isEnabled = !already
        binding.btnAddFavorite.setOnClickListener {
            destination?.let { d ->
                if (MainActivity.favorites.none { it.id == d.id }) {
                    MainActivity.favorites.add(d)
                }
                binding.btnAddFavorite.isEnabled = false
                Toast.makeText(this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchWeatherIfPossible() {
        val d = destination ?: return
        WeatherClient.updateWeatherLabel(this, d.lat, d.lon, binding.tvWeather)
    }
}
