package com.proway.gitrepoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.proway.gitrepoapp.R
import com.proway.gitrepoapp.databinding.ItemListPrBinding
import com.proway.gitrepoapp.model.GitHubPullRequestResponse


class AdapterRepoPrs(val OnItemClick: (GitHubPullRequestResponse) -> Unit) :
    RecyclerView.Adapter<RepositoriesPrsViewHolder>() {
    private var listOfReposPrs = mutableListOf<GitHubPullRequestResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesPrsViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_list_pr, parent, false).apply {
            return RepositoriesPrsViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: RepositoriesPrsViewHolder, position: Int) {
        listOfReposPrs[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener { OnItemClick(this) }
        }
    }

    fun refresh(newList: List<GitHubPullRequestResponse>) {
        listOfReposPrs.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listOfReposPrs.size

}

class RepositoriesPrsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var binding = ItemListPrBinding.bind(itemView)

    fun bind(repositoriesPrs: GitHubPullRequestResponse) {
        binding.textViewTittlePullRequest.text = "Title: ${repositoriesPrs.titleOfThePr}"
        binding.textViewOpenDate.text = "Open at: ${repositoriesPrs.createdAt}"
        binding.textViewClosedDate.text = "Closed at: ${repositoriesPrs.updatedAt}"
        binding.textViewAuthorName.text = "Author: ${repositoriesPrs.userOfThePr.loginOfUserOfPr}"
        binding.imageViewAuthor.apply {
            Glide.with(this)
                .load(repositoriesPrs.userOfThePr.avatarUserPr)
                .placeholder(R.drawable.github_logo)
                .into(this)

        }
    }
}