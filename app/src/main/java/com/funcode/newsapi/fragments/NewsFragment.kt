package com.funcode.newsapi.fragments



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.funcode.newsapi.DetailActivity
import com.funcode.newsapi.R
import com.funcode.newsapi.Util.url
import com.funcode.newsapi.adapter.NewsAdapter
import com.funcode.newsapi.databinding.FragmentNewsBinding
import com.funcode.newsapi.model.News
import com.funcode.newsapi.viewmodel.NewsViewModel


class NewsFragment : Fragment() {

    private lateinit var binding : FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

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
        binding.apply {
            val tittle = resources.getString(R.string.news)
            tvTittle.text = tittle
            recyclerNews.showShimmer()
        }
        setRecycler()

    }


    private fun setRecycler(){
        newsAdapter = NewsAdapter(object : NewsAdapter.ClickMe{
            override fun onClick(news: News) {
               startActivity(Intent(requireActivity(),DetailActivity::class.java).also{
                   it.putExtra(url,news)
               })
            }

        })
        newsViewModel.getListNews()
        newsViewModel.listNewsObserver().observe(requireActivity()){
            newsAdapter.submitData(it)
            binding.recyclerNews.hideShimmer()
        }

        binding.recyclerNews.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

}