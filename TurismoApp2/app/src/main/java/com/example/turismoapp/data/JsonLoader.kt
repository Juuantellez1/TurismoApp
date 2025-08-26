package com.example.turismoapp.data

import android.content.Context
import com.example.turismoapp.R
import com.example.turismoapp.model.Destination
import org.json.JSONArray
import org.json.JSONObject

object JsonLoader {

    fun loadDestinations(context: Context): List<Destination> {
        // 1) Intenta leer desde assets
        val fromAssets = try {
            context.assets.open("destinos.json")
                .bufferedReader(Charsets.UTF_8).use { it.readText() }
        } catch (_: Exception) { null }

        // 2) Si no, usa raw
        val rawText = fromAssets ?: context.resources.openRawResource(R.raw.destinos)
            .bufferedReader(Charsets.UTF_8).use { it.readText() }

        val text = rawText.replaceFirst("\uFEFF", "").trim { it <= ' ' }

        // Si es objeto con "destinos", lo parseamos
        val arr: JSONArray = try {
            val root = JSONObject(text)
            root.optJSONArray("destinos") ?: JSONArray()
        } catch (_: Exception) {
            try { JSONArray(text) } catch (_: Exception) { JSONArray() }
        }

        val out = mutableListOf<Destination>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            out.add(
                Destination(
                    id = o.optInt("id", 0),
                    nombre = o.optString("nombre"),
                    categoria = o.optString("categoria"),
                    pais = o.optString("pais"),
                    descripcion = o.optString("descripcion", ""),
                    actividadRecomendada = o.optString("actividadRecomendada", ""),
                    lat = o.optDouble("lat", Double.NaN).let { if (it.isNaN()) null else it },
                    lon = o.optDouble("lon", Double.NaN).let { if (it.isNaN()) null else it }
                )
            )
        }
        return out
    }
}
