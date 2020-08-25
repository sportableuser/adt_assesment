package com.example.adt.ui.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adt.R
import com.example.adt.modal.Article
import kotlinx.android.synthetic.main.article_item.view.*

class ArticlesAdapter(val onClick: (Int) -> Unit): ListAdapter<Article, ArticlesAdapter.UsersViewHolder>(DiffCallback()){

    class UsersViewHolder(private val containerView: View, val onClick: (Int) -> Unit): RecyclerView.ViewHolder(containerView) {

        init {
            containerView.setOnClickListener {
                onClick(adapterPosition)
            }
        }
        fun bind(item: Article) {
            containerView.article_title.text = item.title
            containerView.article_desc.text = item.description
            Glide.with(containerView.context)
                .load(item.urlToImage)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(containerView.article_image)
        }
    }

    private class DiffCallback: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source.id == newItem.source.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == (newItem)
        }

    }

    public override fun getItem(position: Int): Article {
        return super.getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return UsersViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}