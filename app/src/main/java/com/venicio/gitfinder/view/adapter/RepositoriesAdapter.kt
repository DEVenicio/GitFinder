package com.venicio.gitfinder.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.venicio.gitfinder.data.model.Repositorie
import com.venicio.gitfinder.databinding.ItemRepoBinding

class RepositoriesAdapter() :
    ListAdapter<Repositorie, RepositoriesAdapter.RepositorieVH>(GitFinderDiffRepositorie()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositorieVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(inflater, parent, false)

        return RepositorieVH(binding)
    }

    override fun onBindViewHolder(holder: RepositorieVH, position: Int) {

        holder.bindRepo(getItem(position))
    }

    class RepositorieVH(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindRepo(dataRepo: Repositorie) {

            binding.tvNameRepoValue.text = dataRepo.name
        }
    }

    class GitFinderDiffRepositorie : DiffUtil.ItemCallback<Repositorie>() {
        override fun areItemsTheSame(oldItem: Repositorie, newItem: Repositorie): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Repositorie, newItem: Repositorie): Boolean {
            return oldItem.name == newItem.name
        }
    }
}

