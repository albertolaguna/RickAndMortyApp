package com.solera.rickandmortyapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.solera.rickandmortyapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCharacters()
    }

    private fun getCharacters() {
        RetrofitClient.service.getCharacters().enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful) {
                    val characters = response.body()?.results ?: emptyList()
                    binding.rvCharacters.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.rvCharacters.adapter = CharacterAdapter(characters)
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Connection error", Toast.LENGTH_LONG).show()
            }
        })
    }
}