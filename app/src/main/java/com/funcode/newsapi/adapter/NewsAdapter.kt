package com.funcode.newsapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.funcode.newsapi.Util.setDateFormat
import com.funcode.newsapi.databinding.ListItemBinding
import com.funcode.newsapi.model.News

class NewsAdapter(private val listener : ClickMe):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.source?.id == newItem.source?.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
           return when{
               oldItem.source?.id != newItem.source?.id -> false
               oldItem.source?.name != newItem.source?.name -> false
               oldItem.author != newItem.author -> false
               oldItem.title != newItem.title  -> false
               oldItem.url != newItem.url -> false
               oldItem.urlToImage != newItem.urlToImage  -> false
               oldItem.publishedAt != newItem.publishedAt  -> false
               oldItem.description != newItem.description -> false
               oldItem.content != newItem.content -> false
               else -> true
           }
        }
    }

    private val differ = AsyncListDiffer(this,diffUtil)

    fun submitData(datas : MutableList<News>) = differ.submitList(datas)

    inner class NewsViewHolder(val binding : ListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       return NewsViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       holder.binding.apply {
           val (source,author,title,_,_,urlToImage,publishedAt,_) = differ.currentList[position]
           if(source != null){
               val sources = "$author, 2022  ${source.name}"
               tvNameSource.text =  sources
           }
           tvTitleNews.text = title
           tvDateTime.text = setDateFormat(publishedAt)
//           tvTimeAgo.text = setDateTimeHourAgo(publishedAt) Only API level 26 (android 8) or higher can use this code
           Glide.with(root.context).load(urlToImage).transition(DrawableTransitionOptions.withCrossFade(500)).into(imageThumbnail)

           frameListNews.setOnClickListener {
               listener.onClick(differ.currentList[position])
           }
       }
    }

    override fun getItemCount(): Int = differ.currentList.size

    interface ClickMe{
        fun onClick(news : News)
    }
}