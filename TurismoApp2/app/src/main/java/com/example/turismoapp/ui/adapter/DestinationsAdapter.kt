// com/example/turismoapp/ui/adapter/DestinationsAdapter.kt
package com.example.turismoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turismoapp.R
import com.example.turismoapp.model.Destination

class DestinationsAdapter(
    private var items: List<Destination>,
    private val onClick: (Destination) -> Unit
) : RecyclerView.Adapter<DestinationsAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val tvName = v.findViewById<TextView>(R.id.tvName)
        private val tvCategory = v.findViewById<TextView>(R.id.tvCategory)
        private val tvCountry = v.findViewById<TextView>(R.id.tvCountry)
        fun bind(d: Destination) {
            tvName.text = d.nombre
            tvCategory.text = d.categoria
            tvCountry.text = d.pais
            itemView.setOnClickListener { onClick(d) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destination, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size

    fun submit(newItems: List<Destination>) {
        items = newItems
        notifyDataSetChanged()
    }
}
