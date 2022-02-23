package com.vishalgaur.githubprapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        // add on scroll listener
        binding.prRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager
                val prListSize = viewModel.pullRequests.value?.size ?: 0
                if(linearLayoutManager?.findLastCompletelyVisibleItemPosition() == prListSize - 1)
                    viewModel.getMorePullRequests()
            }
        })
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
                        if(prAdapter.data.isNullOrEmpty()) {
                            prAdapter.data = prList
                            binding.prRecyclerView.adapter = prAdapter
                        } else {
                            prAdapter.data = prList
                            prAdapter.notifyDataSetChanged()
                            val newPrs = viewModel.newPullRequests.value ?: emptyList()
                            if(newPrs.isNotEmpty()) {
                                val linearLayoutManager = binding.prRecyclerView.layoutManager as? LinearLayoutManager
                                val firstPos = linearLayoutManager?.findFirstCompletelyVisibleItemPosition() ?: 0
                                linearLayoutManager?.scrollToPositionWithOffset(firstPos, 0)
                            }
                        }
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