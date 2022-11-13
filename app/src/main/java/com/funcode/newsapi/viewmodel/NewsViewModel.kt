package com.funcode.newsapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.funcode.newsapi.Util
import com.funcode.newsapi.api.ApiService
import com.funcode.newsapi.model.News
import com.funcode.newsapi.BuildConfig;
import com.funcode.newsapi.model.ListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel:ViewModel() {


    private val listNews : MutableLiveData<MutableList<News>> = MutableLiveData()
    private val listCategoryNews : MutableLiveData<MutableList<News>> = MutableLiveData()
    private val listSearchNews : MutableLiveData<MutableList<News>> = MutableLiveData()

    fun listNewsObserver() : MutableLiveData<MutableList<News>> = listNews
    fun listCategoryNewsObserver() : MutableLiveData<MutableList<News>> = listCategoryNews
    fun listSearchNewsObserver() : MutableLiveData<MutableList<News>> = listSearchNews

    fun getListNews(){
        ApiService.newsEndPoint().getListNews(Util.getCountry(),BuildConfig.NEWS_API_KEY)
            .enqueue(object : Callback<ListResponse<News>>{
                override fun onResponse(
                    call: Call<ListResponse<News>>,
                    response: Response<ListResponse<News>>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            listNews.postValue(body.articles)
                        }
                    }
                }

                override fun onFailure(call: Call<ListResponse<News>>, t: Throwable) {
                    Log.d("onFailure","${t.message}")
                }

            })
    }

    fun getNewsCategory(category : String){
        ApiService.newsEndPoint().getCategory(Util.getCountry(),category,BuildConfig.NEWS_API_KEY)
            .enqueue(object : Callback<ListResponse<News>>{
                override fun onResponse(
                    call: Call<ListResponse<News>>,
                    response: Response<ListResponse<News>>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            listCategoryNews.postValue(body.articles)
                            Log.d("Success","${body.articles}")
                        }
                    }else{
                        Log.d("notSuccess","${response.body()}")
                    }
                }

                override fun onFailure(call: Call<ListResponse<News>>, t: Throwable) {
                    Log.d("onFailure","${t.message}")
                }

            })
    }

    fun getNewsSearch(search : String){
        ApiService.newsEndPoint().getSearchNews(search,BuildConfig.NEWS_API_KEY)
            .enqueue(object : Callback<ListResponse<News>>{
                override fun onResponse(
                    call: Call<ListResponse<News>>,
                    response: Response<ListResponse<News>>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            listSearchNews.postValue(body.articles)
                        }
                    }
                }

                override fun onFailure(call: Call<ListResponse<News>>, t: Throwable) {
                    Log.d("onFailure","${t.message}")
                }

            })
    }



}