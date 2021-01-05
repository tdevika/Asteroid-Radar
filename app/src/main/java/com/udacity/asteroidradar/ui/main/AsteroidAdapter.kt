package com.udacity.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidAdapter(val itemClickListener: ItemClickListener) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(diffUtils) {
    lateinit var binding: ItemAsteroidBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        binding = ItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AsteroidViewHolder(val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Asteroid?) {
            binding.asteroid = item
            binding.root.setOnClickListener {
                item?.let { itemClickListener.onClick(it) }
            }
        }
    }
}

val diffUtils = object : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
        oldItem == newItem
}
