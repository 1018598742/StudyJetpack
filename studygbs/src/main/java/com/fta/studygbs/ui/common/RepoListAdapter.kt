package com.fta.studygbs.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fta.studygbs.AppExecutors
import com.fta.studygbs.R
import com.fta.studygbs.vo.Repo

class RepoListAdapter(
    appExecutors: AppExecutors,
    private val repoClickCallback: ((Repo) -> Unit)?
) : ListAdapter<Repo,
        RepoListAdapter.RepoViewHolder>(
    AsyncDifferConfig.Builder<Repo>(object :
        DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.owner == newItem.owner && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.description == newItem.description && oldItem.stars == newItem.stars
        }
    }).setBackgroundThreadExecutor(appExecutors.diskIO()).build()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        return RepoViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bindItem(getItem(position))
        holder.itemView.setOnClickListener {
            repoClickCallback?.invoke(getItem(position))
        }
    }


    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val desc = itemView.findViewById<TextView>(R.id.desc)
        val stars = itemView.findViewById<TextView>(R.id.stars)
        fun bindItem(repo: Repo) {
            name.text = repo.fullName
            desc.text = repo.description
            stars.text = repo.stars.toString()
        }
    }

}