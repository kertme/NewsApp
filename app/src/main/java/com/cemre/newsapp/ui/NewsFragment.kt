package com.cemre.newsapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cemre.newsapp.R
import com.cemre.newsapp.data.Article
import com.cemre.newsapp.databinding.FragmentNewsBinding
import com.cemre.newsapp.ui.adapter.NewsAdapter
import com.cemre.newsapp.ui.vm.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news), NewsAdapter.OnItemClickListener{

    private val viewModel by viewModels<NewsViewModel>()

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNewsBinding.bind(view)

        val adapter = NewsAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter
        }

        viewModel.news.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length > 3) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchNews(s.toString())
                }
            }
        })

    }

    override fun onItemClick(article: Article) {
        val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(article)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}