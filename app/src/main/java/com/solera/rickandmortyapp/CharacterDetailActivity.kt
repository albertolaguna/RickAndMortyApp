package com.solera.rickandmortyapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.solera.rickandmortyapp.databinding.ActivityCharacterDetailBinding
import com.solera.rickandmortyapp.databinding.ActivityMainBinding

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val characterId = intent.getIntExtra("CHARACTER_ID", -1)
        binding.textView.text = "Character ID: $characterId"
    }
}