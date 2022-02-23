package com.vishalgaur.githubprapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vishalgaur.githubprapp.R
import com.vishalgaur.githubprapp.adapters.PullRequestAdapter
import com.vishalgaur.githubprapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var prAdapter: PullRequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        setViews()
        setObservers()
    }

    private fun setViews() {
        // Initializing Title
        viewModel.repoName?.let {
            activity?.actionBar?.title = it
        }
        binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
        binding.noPrTv.visibility = View.GONE
        //
        prAdapter = PullRequestAdapter(viewModel.pullRequests.value ?: emptyList())
        binding.prRecyclerView.adapter = prAdapter
    }

    private fun setObservers() {
        viewModel.status.observe(viewLifecycleOwner) {status ->
            when(status) {
                GitHubApiStatus.LOADING -> {
                    binding.noPrTv.visibility = View.GONE
                    binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
                    binding.loaderLayout.circularLoader.showAnimationBehavior
                }
                else -> {
                    binding.loaderLayout.circularLoader.hideAnimationBehavior
                    binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                }
            }

            if(status != null && status != GitHubApiStatus.LOADING) {
                viewModel.pullRequests.observe(viewLifecycleOwner) { prList ->
                    if(prList.isNotEmpty()) {
                        prAdapter.data = prList
                        binding.prRecyclerView.adapter = prAdapter
                        binding.prRecyclerView.adapter?.notifyDataSetChanged()
                    } else {
                        binding.prRecyclerView.visibility = View.GONE
                        binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                        binding.loaderLayout.circularLoader.hideAnimationBehavior
                        binding.noPrTv.visibility = View.VISIBLE
                        binding.noPrTv.text = getString(R.string.no_pr_found)
                    }
                }
            }
        }
    }

}