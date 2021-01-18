package com.androiddevs.news.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.news.R
import com.androiddevs.news.adapter.NewsAdapter
import com.androiddevs.news.extension.bind
import com.androiddevs.news.extension.displayToast
import com.androiddevs.news.extension.makeVisible
import com.androiddevs.news.repository.NewsRepository
import com.androiddevs.news.viewmodel.BreakingNewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private val newsAdapter by lazy {
        NewsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        bindViewModel()
    }

    private fun bindViewModel() {
        val viewModelBreaking = BreakingNewsViewModel(NewsRepository())
        with(viewModelBreaking) {
            bind(progressBarVisibility) {
                paginationProgressBar.makeVisible(it)
            }
            bind(articles) {
                newsAdapter.differ.submitList(it)
            }
            bind(padding) {
                rvBreakingNews.setPadding(0, 0, 0, 0)
            }
            bind(errorMessage) {
                displayToast(it)
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
