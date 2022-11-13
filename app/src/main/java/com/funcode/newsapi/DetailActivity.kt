package com.funcode.newsapi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.funcode.newsapi.Util.setDateFormat
import com.funcode.newsapi.Util.url
import com.funcode.newsapi.databinding.ActivityDetailBinding
import com.funcode.newsapi.model.News

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        binding.apply {
            progressBar.visibility = View.VISIBLE
            progressBar.max = 100
        }
        webViewConfig()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewConfig() {
        val news = intent.getParcelableExtra<News>(url)
        binding.apply {
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.loadsImagesAutomatically = true
            webView.settings.builtInZoomControls = true
            webView.settings.displayZoomControls = false
            webView.settings.setSupportZoom(true)
            webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            if(news != null){
                tvTitle.text = news.title
                tvSubTitle.text = setDateFormat(news.publishedAt)
                Log.d("URL YGY", news.url)
                webView.loadUrl(news.url)
                progressBar.progress = 0
                Handler(Looper.getMainLooper()).postDelayed({
                    progressBar.visibility = View.GONE
                },5000)
            }else{
                Toast.makeText(this@DetailActivity, "Url is null", Toast.LENGTH_SHORT).show()
            }

            imageShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT,news?.url)
                }
                startActivity(Intent.createChooser(intent,"Share to : "))
            }

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}