package com.cemre.newsapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cemre.newsapp.R
import com.cemre.newsapp.data.Article
import com.cemre.newsapp.data.FavoriteArticle
import com.cemre.newsapp.databinding.FragmentNewsDetailsBinding
import com.cemre.newsapp.ui.vm.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    private val args by navArgs<NewsDetailsFragmentArgs>()

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FavoritesViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article

        _binding = FragmentNewsDetailsBinding.bind(view)

        binding.apply {

            Glide.with(view)
                .load(article.urlToImage)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)

            titleTextView.text = article.title
            authorTextView.text = article.author
            dateTextView.text = article.publishedAt?.take(10)
            descriptionTextView.text = article.description

            if (article.url != null) {
                sourceButton.setOnClickListener {
                    onButtonClick(article)
                }
            } else {
                sourceButton.visibility = View.INVISIBLE
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.details_menu, menu)

        viewModel.checkArticle(args.article.url!!)
        viewModel.checkResult.observe(this, Observer {
            if (it == true){
                menu.findItem(R.id.action_like).setIcon(R.drawable.ic_favorite)
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        var result_flag: Boolean = true
        if (id == R.id.action_like) {
            viewModel.checkArticle(args.article.url!!)
            viewModel.checkResult.observe(this, Observer {
                if (it == false){
                    item.setIcon(R.drawable.ic_favorite)
                    result_flag = false
                }

                else{
                    item.setIcon(R.drawable.ic_not_favorite)
                    viewModel.deleteArticle(args.article.url!!)
                }
            })

            if (!result_flag){
                                    viewModel.addArticle(
                        FavoriteArticle(
                            0,
                            args.article.author,
                            args.article.content,
                            args.article.description,
                            args.article.publishedAt,
                            args.article.title,
                            args.article.url,
                            args.article.urlToImage
                        )
                    )
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onButtonClick(article: Article) {

        val action = NewsDetailsFragmentDirections
            .actionNewsDetailsFragmentToNewsSourceFragment(article.url!!)

        findNavController().navigate(action)
    }

}