package com.proway.gitrepoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.proway.gitrepoapp.R
import com.proway.gitrepoapp.databinding.ItemListBinding
import com.proway.gitrepoapp.databinding.ItemListPrBinding
import com.proway.gitrepoapp.model.RepoPullRequestResponse


class AdapterRepoPrs(val OnItemClick: (RepoPullRequestResponse) -> Unit) :
    RecyclerView.Adapter<RepositoriesPrsViewHolder>() {
    private var listOfReposPrs = mutableListOf<RepoPullRequestResponse>()

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

    fun refresh(newList: List<RepoPullRequestResponse>) {
        listOfReposPrs.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listOfReposPrs.size

}

class RepositoriesPrsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var binding = ItemListPrBinding.bind(itemView)

    fun bind(repositoriesPrs: RepoPullRequestResponse) {
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