
package com.example.turismoapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.turismoapp.R
import com.example.turismoapp.data.JsonLoader
import com.example.turismoapp.model.Destination
import java.text.Normalizer

class DestinationsListActivity : AppCompatActivity() {

    private fun normalize(s: String) = Normalizer.normalize(s, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
        .trim()
        .lowercase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations_list)

        val listView = findViewById<ListView>(R.id.lvDestinations)

        val rawCategory = intent.getStringExtra(MainActivity.EXTRA_CATEGORY).orEmpty()
        val selected = normalize(rawCategory)
        Toast.makeText(this, "Categoría recibida: $rawCategory", Toast.LENGTH_SHORT).show()

        val all: List<Destination> = try {
            JsonLoader.loadDestinations(this)
        } catch (e: Exception) {
            Toast.makeText(this, "Error leyendo JSON: ${e.message}", Toast.LENGTH_LONG).show()
            emptyList()
        }

        val items = if (selected.isBlank() || selected == "todos") {
            all
        } else {
            val sel = selected
            all.filter { normalize(it.categoria) == sel }
        }

        Toast.makeText(this, "Destinos cargados: ${items.size}", Toast.LENGTH_SHORT).show()

        val names = items.map { it.nombre }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names)

        listView.setOnItemClickListener { _, _, position, _ ->
            val dest = items[position]
            startActivity(Intent(this, DestinationDetailActivity::class.java).apply {
                putExtra("dest_id", dest.id) // id Int
            })
        }
    }
}