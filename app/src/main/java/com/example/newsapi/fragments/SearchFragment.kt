package com.example.newsapi.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.DetailActivity
import com.example.newsapi.R
import com.example.newsapi.Util
import com.example.newsapi.adapter.NewsAdapter
import com.example.newsapi.databinding.FragmentSearchBinding
import com.example.newsapi.model.News
import com.example.newsapi.viewmodel.NewsViewModel
import java.util.*


class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(layoutInflater)
        newsViewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keyEventSearch()
        binding.btnVoice.setOnClickListener {
            askToSpeech()
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
        newsViewModel.getListNews()
        newsViewModel.listSearchNewsObserver().observe(requireActivity()){
            if(it != null){
                newsAdapter.submitData(it)
                binding.imageSearchResult.setImageDrawable(null)
            }else{
                binding.imageSearchResult.setImageResource(R.drawable.not_found)
            }
        }

        binding.recyclerListSearch.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun askToSpeech(){
        if(!SpeechRecognizer.isRecognitionAvailable(requireActivity())){
            Toast.makeText(requireActivity(), "Mic Error", Toast.LENGTH_SHORT).show()
        }else{
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Anything")
            }
            startActivityForResult(i,201)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 201 && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if(result != null){
                newsViewModel.getNewsSearch(result[0].toString())
                binding.etSearch.setText(result[0].toString())
            }
        }
    }

    private fun keyEventSearch(){
        binding.etSearch.setOnEditorActionListener { _, actionId,_ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val search = binding.etSearch.text.toString()
                if(search.isNotBlank()){
                    newsViewModel.getNewsSearch(search)

                }else{
                    Toast.makeText(requireActivity(), "Kolom search tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
                true
            } else false
        }
    }
}