package com.funcode.newsapi.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.funcode.newsapi.DetailActivity
import com.funcode.newsapi.R
import com.funcode.newsapi.Util
import com.funcode.newsapi.adapter.NewsAdapter
import com.funcode.newsapi.databinding.FragmentNewsBinding
import com.funcode.newsapi.model.News
import com.funcode.newsapi.viewmodel.NewsViewModel


class HealthFragment : Fragment() {

    private lateinit var binding : FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel
    private var tittle = ""
    private var category = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewsBinding.inflate(layoutInflater)
        newsViewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category = resources.getString(R.string.health)
        tittle = resources.getString(R.string.news_health)
        binding.apply {
            tvTittle.text = tittle
            recyclerNews.showShimmer()
        }
        setRecycler()
    }

    private fun setRecycler(){
        newsAdapter = NewsAdapter(object : NewsAdapter.ClickMe{
            override fun onClick(news: News) {
                startActivity(Intent(requireActivity(), DetailActivity::class.java).also{
                    it.putExtra(Util.url,news)
                })
            }

        })
        newsAdapter.submitData(mutableListOf())
        newsViewModel.getNewsCategory(category)
        newsViewModel.listCategoryNewsObserver().observe(requireActivity()){
            if(it != null){
                newsAdapter.submitData(it)
                binding.recyclerNews.hideShimmer()
            }
        }

        binding.recyclerNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }


}