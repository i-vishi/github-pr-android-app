package com.vishalgaur.githubprapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vishalgaur.githubprapp.databinding.LayoutPrListItemBinding
import com.vishalgaur.githubprapp.network.PullRequest
import java.text.SimpleDateFormat
import java.util.*

class PullRequestAdapter(prList: List<PullRequest>) :
    RecyclerView.Adapter<PullRequestAdapter.PullRequestViewHolder>() {

    var data: List<PullRequest> = prList

    inner class PullRequestViewHolder(private var binding: LayoutPrListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pr: PullRequest) {
            binding.itemTitle.text = pr.title
            binding.itemSubTitle.text = getSubTitle(pr)
        }
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        return PullRequestViewHolder(LayoutPrListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bind(data[position])
    }

    private fun getSubTitle(pr: PullRequest): String {
        return "#${pr.number} by ${pr.user["login"]} created on ${getDateString(pr.created_at ?: "")}"
    }

    private fun getDateString(inputDate: String): String {
        if(inputDate.isEmpty())
            return ""
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date: Date? = inputFormat.parse(inputDate)
        return if (date != null) outputFormat.format(date) else ""
    }
}