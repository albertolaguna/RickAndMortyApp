package com.solera.rickandmortyapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharacterAdapter(private val characters: List<Character>): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgAvatar: ImageView = view.findViewById(R.id.imgAvatar)
        val name: TextView = view.findViewById(R.id.tvName)
        val spieces: TextView = view.findViewById(R.id.tvSpecies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.name.text = character.name
        holder.spieces.text = character.species
        Glide.with(holder.itemView.context)
            .load(character.image)
            .into(holder.imgAvatar)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra("CHARACTER_ID", character.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = characters.size
}