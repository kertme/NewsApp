package com.cemre.newsapp.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cemre.newsapp.R
import com.cemre.newsapp.databinding.FragmentNewsBinding
import com.cemre.newsapp.databinding.FragmentNewsSourceBinding
import kotlinx.android.synthetic.main.fragment_news_source.*

class NewsSourceFragment : Fragment(R.layout.fragment_news_source) {

    private var _binding : FragmentNewsSourceBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<NewsSourceFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNewsSourceBinding.bind(view)

        val url = args.articleUrl
        binding.apply {
            sourceWebView.webViewClient = WebViewClient()
            sourceWebView.loadUrl(url)
            sourceWebView.settings.safeBrowsingEnabled = true
            sourceWebView.settings.javaScriptEnabled = true
        }

    }


}