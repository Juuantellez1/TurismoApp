package com.example.turismoapp.ui
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.turismoapp.databinding.ActivityRecommendationsBinding
import com.example.turismoapp.ui.MainActivity
import kotlin.random.Random
class RecommendationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showRecommendation()
    }
    private fun showRecommendation() {
        val favs = MainActivity.favorites
        if (favs.isEmpty()) { binding.tvRecoTitle.text = "NA";
            binding.tvRecoActivity.text = ""; return }
        val freq = favs.groupingBy { it.categoria }.eachCount()
        val topCategory = freq.maxByOrNull { it.value }?.key
        13
        val pool = favs.filter { it.categoria == topCategory }
        val pick = pool[Random.nextInt(pool.size)]
        binding.tvRecoTitle.text = pick.nombre
        binding.tvRecoActivity.text = pick.actividadRecomendada
    }
}