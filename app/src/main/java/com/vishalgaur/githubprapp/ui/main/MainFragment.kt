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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //
        setTitle()
        setRecyclerView()
        setObservers()
    }

    private fun setTitle() {
        // Initializing Title
        binding.mainTitle.text = viewModel.repoName
    }

    private fun setRecyclerView() {
        // initializing Recycler View
        val prList = viewModel.pullRequests.value ?: emptyList()
        prAdapter = PullRequestAdapter(prList)
        binding.prRecyclerView.adapter = prAdapter
    }

    private fun setObservers() {
        viewModel.status.observe(viewLifecycleOwner) {status ->
            when(status) {
                GitHubApiStatus.DONE -> {
                    binding.noPrTv.visibility = View.GONE
                }
                else -> {
                    binding.prRecyclerView.visibility = View.GONE
                    binding.noPrTv.visibility = View.VISIBLE
                    binding.noPrTv.text = getString(R.string.no_pr_found)
                }
            }
        }
        viewModel.pullRequests.observe(viewLifecycleOwner) { prList ->
            if(prList.isNotEmpty()) {
                binding.prRecyclerView.visibility = View.VISIBLE
                prAdapter.data = prList
                binding.prRecyclerView.adapter = prAdapter
                binding.prRecyclerView.adapter?.notifyDataSetChanged()
            } else {
                binding.prRecyclerView.visibility = View.GONE
                binding.noPrTv.visibility = View.VISIBLE
                binding.noPrTv.text = getString(R.string.no_pr_found)
            }
        }
    }

}