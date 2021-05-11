package com.cemre.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cemre.newsapp.data.Article
import com.cemre.newsapp.data.FavoriteArticle
import com.cemre.newsapp.databinding.ItemNewsArticleBinding

class FavoriteArticleAdapter(private val listener: OnItemClickListener):
    RecyclerView.Adapter<FavoriteArticleAdapter.FavoriteArticleViewHolder>() {

    private var articleList = arrayListOf<FavoriteArticle>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteArticleViewHolder {
        val binding = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteArticleViewHolder, position: Int) {
        val currentItem = articleList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun setItems(article : ArrayList<FavoriteArticle>) {
        articleList = article
    }

    inner class FavoriteArticleViewHolder(private val binding: ItemNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(article: FavoriteArticle) {

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

        override fun onClick(p0: View?) {

            if (bindingAdapterPosition != RecyclerView.NO_POSITION){
                listener.onItemclick(articleList[bindingAdapterPosition])
            }
        }
    }

    interface OnItemClickListener {
        fun onItemclick(article: FavoriteArticle)
    }

}