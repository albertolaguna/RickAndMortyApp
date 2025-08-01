package com.solera.rickandmortyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.solera.rickandmortyapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.AdapterView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var allCharacters: List<Character> = emptyList()
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchAllCharacters()
    }

    private fun fetchAllCharacters() {
        val characters = mutableListOf<Character>()
        var page = 1

        fun fetchPage() {
            RetrofitClient.service.getCharacters(page).enqueue(object : Callback<CharacterResponse> {
                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            characters.addAll(body.results)
                            if (body.info.next != null) {
                                page++
                                fetchPage()
                            } else {
                                setupUI(characters)
                            }
                        } else {
                            setupUI(characters)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "API error", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Connection error", Toast.LENGTH_LONG).show()
                }
            })
        }
        fetchPage()
    }

    private fun setupUI(characters: List<Character>) {
        allCharacters = characters

        val uniqueSpecies = listOf("") + characters.map { it.species }.distinct().sorted()
        val uniqueOrigins = listOf("") + characters.map { it.origin.name }.distinct().sorted()

        val speciesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, uniqueSpecies)
        speciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = speciesAdapter

        val originAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, uniqueOrigins)
        originAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter = originAdapter


        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterCharacters()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        characterAdapter = CharacterAdapter(allCharacters.toMutableList())
        binding.rvCharacters.layoutManager = LinearLayoutManager(this)
        binding.rvCharacters.adapter = characterAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterCharacters()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterCharacters()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun filterCharacters() {
        val selectedSpecies = binding.spinner.selectedItem as String
        val selectedOrigin = binding.spinner2.selectedItem as String

        val nameQuery = binding.editTextText.text.toString().trim().lowercase()

        val filtered = allCharacters.filter { character ->
            (selectedSpecies.isEmpty() || character.species == selectedSpecies) &&
                    (selectedOrigin.isEmpty() || character.origin.name == selectedOrigin) &&
                    (nameQuery.isEmpty()|| character.name.lowercase().contains(nameQuery))
        }
        characterAdapter.updateList(filtered)
    }
}