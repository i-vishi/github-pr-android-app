package com.vishalgaur.githubprapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vishalgaur.githubprapp.databinding.LayoutPrListItemBinding
import com.vishalgaur.githubprapp.network.PullRequest

class PullRequestAdapter(prList: List<PullRequest>) :
    RecyclerView.Adapter<PullRequestAdapter.PullRequestViewHolder>() {

    var data: List<PullRequest> = prList

    class PullRequestViewHolder(private var binding: LayoutPrListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pr: PullRequest) {
            binding.itemTitle.text = pr.title
            if(pr.body?.isEmpty() == true)
                binding.itemSubTitle.visibility = View.GONE
            else
                binding.itemSubTitle.text = pr.body
        }
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        return PullRequestViewHolder(LayoutPrListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bind(data[position])
    }
}