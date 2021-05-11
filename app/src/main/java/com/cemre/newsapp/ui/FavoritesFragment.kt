package com.cemre.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cemre.newsapp.R
import com.cemre.newsapp.data.Article
import com.cemre.newsapp.data.FavoriteArticle
import com.cemre.newsapp.databinding.FragmentFavoritesBinding
import com.cemre.newsapp.ui.adapter.FavoriteArticleAdapter
import com.cemre.newsapp.ui.vm.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), FavoriteArticleAdapter.OnItemClickListener{

    private val viewModel by viewModels<FavoritesViewModel>()

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavoritesBinding.bind(view)

        val adapter = FavoriteArticleAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter
        }

        viewModel.articles.observe(viewLifecycleOwner) {
            adapter.setItems(it as ArrayList<FavoriteArticle>)
            adapter.notifyDataSetChanged()
        }
    }


    override fun onItemclick(article: FavoriteArticle) {

        val convertedArticle = Article(
            article.author,
            article.content,
            article.description,
            article.publishedAt,
            null,
            article.title,
            article.url,
            article.urlToImage)
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToNewsDetailsFragment(convertedArticle)
        findNavController().navigate(action)
    }
}