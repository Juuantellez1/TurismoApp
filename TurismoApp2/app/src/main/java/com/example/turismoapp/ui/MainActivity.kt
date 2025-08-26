package com.example.turismoapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.turismoapp.databinding.ActivityMainBinding
import com.example.turismoapp.model.Destination

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        val favorites = mutableListOf<Destination>()
        const val EXTRA_CATEGORY = "extra_category"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupClicks()
    }

    private fun setupSpinner() {
        val categories = listOf(
            "Todos", "Playas", "Montañas", "Ciudades Históricas",
            "Maravillas del Mundo", "Selvas"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,  // 👈 usa el R de Android, calificado
            categories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategories.adapter = adapter
    }

    private fun setupClicks() {
        binding.btnExplore.setOnClickListener {
            val selected = binding.spCategories.selectedItem?.toString() ?: "Todos"
            startActivity(
                Intent(this, DestinationsListActivity::class.java).apply {
                    putExtra(EXTRA_CATEGORY, selected)
                }
            )
        }

        binding.btnFavorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        binding.btnRecommendations.setOnClickListener {
            startActivity(Intent(this, RecommendationsActivity::class.java))
        }
    }
}
