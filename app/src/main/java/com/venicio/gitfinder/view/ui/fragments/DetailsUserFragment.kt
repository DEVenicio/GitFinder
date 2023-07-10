package com.venicio.gitfinder.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.venicio.gitfinder.data.model.Repositorie
import com.venicio.gitfinder.data.repository.Repository
import com.venicio.gitfinder.databinding.FragmentDetailsUserBinding
import com.venicio.gitfinder.view.adapter.RepositoriesAdapter
import com.venicio.gitfinder.viewmodel.SingleUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailsUserFragment : Fragment() {

    private lateinit var binding: FragmentDetailsUserBinding
    private val args by navArgs<DetailsUserFragmentArgs>()
    private val singleViewModel: SingleUserViewModel by viewModel { parametersOf(Repository()) }
    private lateinit var recyclerAdapter: RepositoriesAdapter
    private lateinit  var listRepos: List<Repositorie>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsUserBinding.inflate(layoutInflater)

        singleViewModel.progressBar.observe(this, Observer {
            binding.detailsProgressBar.visibility = View.GONE
        })

        args.user.login?.let { singleViewModel.getSingleUserData(it) }

        singleViewModel.singleUserData.observe(viewLifecycleOwner, Observer {

            binding.tvUserLogin.text = it.login
            binding.tvUserFollowersValue.text = it.followers
            binding.tvUserFollowingValue.text = it.following
            binding.tvUserPublicRepoValue.text = it.public_repos

            Glide.with(binding.ivUser)
                .load(it.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivUser)
        })

        singleViewModel.dataRepo.observe(this, Observer { response ->
            setupRecyclerRepos()
            listRepos = response.toList()
            recyclerAdapter.submitList(listRepos)
        })

        returnHome()

        return (binding.root)

    }

    private fun setupRecyclerRepos() {
        val recycler = binding.recyclerRepos
        recycler.setHasFixedSize(true)
        recyclerAdapter = RepositoriesAdapter()
        recycler.adapter = recyclerAdapter
    }

    private fun returnHome() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val direction = DetailsUserFragmentDirections.actionDetailsUserFragmentToHomeFragment()
                findNavController().navigate(direction)
            }
        })
    }

}