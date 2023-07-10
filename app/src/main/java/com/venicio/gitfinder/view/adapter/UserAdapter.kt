package com.venicio.gitfinder.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.venicio.gitfinder.data.model.User
import com.venicio.gitfinder.databinding.ItemUserBinding
import com.venicio.gitfinder.view.ui.fragments.HomeFragmentDirections

class UserAdapter() : ListAdapter<User, UserAdapter.UserVH>(GitHubSearcherDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        return UserVH(binding)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bindUser(getItem(position))
    }

    class UserVH(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindUser(dataUser: User) {
            binding.tvUserLogin.text = dataUser.login

            Glide.with(binding.ivUser)
                .load(dataUser.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivUser)

            binding.root.setOnClickListener {
                val direction = HomeFragmentDirections.actionHomeFragmentToDetailsUserFragment(dataUser)
                it.findNavController().navigate(direction)
            }
        }
    }


    class GitHubSearcherDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.login == newItem.login
        }
    }
}