package com.venicio.gitfinder.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.venicio.gitfinder.databinding.FragmentHomeBinding
import com.venicio.gitfinder.view.ui.adapter.UserAdapter


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerAdapter: UserAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        setupSearchView()
        setupRecyclerUser()

        return (binding.root)
    }


    private fun setupSearchView() {
        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query)
                searchView.setQuery("", false)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    fun performSearch(query: String?) {

    }

    private fun setupRecyclerUser() {
        val recycler = binding.recyclerUser
        recycler.setHasFixedSize(true)
        recyclerAdapter = UserAdapter()
        recycler.adapter = recyclerAdapter
    }
}