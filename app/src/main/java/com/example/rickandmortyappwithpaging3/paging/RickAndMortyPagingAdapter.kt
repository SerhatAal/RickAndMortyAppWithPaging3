package com.example.rickandmortyappwithpaging3.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmortyappwithpaging3.databinding.ItemCharacterBinding
import com.example.rickandmortyappwithpaging3.model.CharacterItem

class RickAndMortyPagingAdapter :
    PagingDataAdapter<CharacterItem, RickAndMortyPagingAdapter.CharacterViewHolder>(diffCallBack) {

    inner class CharacterViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<CharacterItem>() {
            override fun areItemsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.binding.apply {
            tvCharacter.text = "${currentItem?.name}"

            val imageUrl = currentItem?.image
            ivCharacter.load(imageUrl) {
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }
}