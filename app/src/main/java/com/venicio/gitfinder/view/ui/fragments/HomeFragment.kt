package com.venicio.gitfinder.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.venicio.gitfinder.data.repository.Repository
import com.venicio.gitfinder.databinding.FragmentHomeBinding
import com.venicio.gitfinder.view.adapter.UserAdapter
import com.venicio.gitfinder.viewmodel.GitFinderViewModel
import com.venicio.gitfinder.viewmodel.SingleUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerAdapter: UserAdapter
    private lateinit var searchView: SearchView
    private val gitFinderViewModel: GitFinderViewModel by viewModel{
        parametersOf(Repository())
    }
    private val singleUserViewModel: SingleUserViewModel by viewModel{
        parametersOf(Repository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        setupSearchView()

        gitFinderViewModel.progressBar.observe(this, Observer {
            binding.progressBar.visibility = View.GONE
        })

        gitFinderViewModel
        gitFinderViewModel.usersData.observe(this, Observer {
            setupRecyclerUser()
            val listUsers = it.toList()
            recyclerAdapter.submitList(listUsers)
        })

        singleUserViewModel.notFound.observe(this, Observer {isError ->
            if(isError) {
                Toast.makeText(context, "Usuario não encontrado", Toast.LENGTH_SHORT).show()
                singleUserViewModel.notFoundReset()
            }
        })

        singleUserViewModel.singleUserData.observe(this, Observer {user ->
            if(user != null) {
                val direction = HomeFragmentDirections.actionHomeFragmentToSingleUserFragment(user)
                findNavController().navigate(direction)
            }
        })

        gitFinderViewModel.userDataFail.observe(this, Observer {
            Toast.makeText(context,"Tivemos um problema, tente novamente mais tarde.",Toast.LENGTH_LONG).show()
            gitFinderViewModel.failUserReset()
        })

        singleUserViewModel.userDataFail.observe(this, Observer {
            Toast.makeText(context,"Tivemos um problema, tente novamente mais tarde.",Toast.LENGTH_LONG).show()
            singleUserViewModel.failSingleUserDataReset()
        })

       requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
           override fun handleOnBackPressed() {
               activity?.finishAffinity()
           }
       })

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
        if (query != null) {
            singleUserViewModel.getSingleUserData(query)
            searchView.clearFocus()
        }
    }

    private fun setupRecyclerUser() {
        val recycler = binding.recyclerUser
        recycler.setHasFixedSize(true)
        recyclerAdapter = UserAdapter()
        recycler.adapter = recyclerAdapter
    }
}