package com.cemre.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.get
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cemre.newsapp.data.Article
import com.cemre.newsapp.databinding.ItemNewsArticleBinding

class NewsAdapter(private val listener: OnItemClickListener) : PagingDataAdapter<Article, NewsAdapter.ArticleViewHolder>
    (ARTICLE_COMPARATOR){
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null){
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }


    inner class ArticleViewHolder(private val binding: ItemNewsArticleBinding) : RecyclerView.ViewHolder(binding.root){

        init {

            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null){
                        listener.onItemClick(item)
                    }
                }

            }
        }

        fun bind(article: Article){
            binding.apply {
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)

                textViewTitle.text = article.title
                textViewDescription.text = article.description
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) =
                oldItem.source?.id == newItem.source?.id

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ) = oldItem == newItem
        }
    }


}