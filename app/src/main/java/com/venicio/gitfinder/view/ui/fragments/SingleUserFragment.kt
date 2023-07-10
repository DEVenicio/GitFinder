package com.venicio.gitfinder.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.venicio.gitfinder.SingleUserFragmentArgs
import com.venicio.gitfinder.SingleUserFragmentDirections
import com.venicio.gitfinder.data.repository.Repository
import com.venicio.gitfinder.databinding.FragmentSingleUserBinding
import com.venicio.gitfinder.view.adapter.SingleUserAdapter
import com.venicio.gitfinder.viewmodel.SingleUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class SingleUserFragment : Fragment() {

    private lateinit var binding: FragmentSingleUserBinding
    private lateinit var recyclerAdapter: SingleUserAdapter
    private val args by navArgs<SingleUserFragmentArgs>()
    private val singleViewModel: SingleUserViewModel by viewModel{ parametersOf(Repository()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleUserBinding.inflate(layoutInflater)

        setupRecyclerUser()
        val listUser = listOf(args.user)
        recyclerAdapter.submitList(listUser)
        binding.singleProgressBar.visibility = View.GONE

        returnHome()

        return (binding.root)
    }

    private fun setupRecyclerUser() {
        val recycler = binding.recyclerSingleUser
        recycler.setHasFixedSize(true)
        recyclerAdapter = SingleUserAdapter()
        recycler.adapter = recyclerAdapter
    }

    private fun returnHome() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val direction =
                    SingleUserFragmentDirections.actionSingleUserFragmentToHomeFragment()
                findNavController().navigate(direction)
            }
        })
    }

}