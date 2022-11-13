package com.funcode.newsapi.api

import com.funcode.newsapi.model.ListResponse
import com.funcode.newsapi.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsEndPoint {

    @GET("top-headlines/")
    fun getListNews(@Query("country")country:String,
                    @Query("apiKey")apiKey:String): Call<ListResponse<News>>

    @GET("top-headlines/")
    fun getCategory(@Query("country")country:String,
                 @Query("category")category:String,
                 @Query("apiKey")apiKey:String): Call<ListResponse<News>>

    @GET("everything/")
    fun getSearchNews(@Query("q")q:String,
                    @Query("apiKey")apiKey:String): Call<ListResponse<News>>
//    @GET("top-headlines/")
//    fun getEntertainment(@Query("country")country:String,
//                 @Query("category")category:String,
//                 @Query("apiKey")apiKey:String): Call<ListResponse<News>>
//
//    @GET("top-headlines/")
//    fun getBitcoin(@Query("country")country:String,
//                 @Query("category")category:String,
//                 @Query("apiKey")apiKey:String): Call<ListResponse<News>>
//
//    @GET("top-headlines/")
//    fun getSport(@Query("country")country:String,
//                 @Query("category")category:String,
//                 @Query("apiKey")apiKey:String): Call<ListResponse<News>>
//
//    @GET("top-headlines/")
//    fun getHealth(@Query("country")country:String,
//                 @Query("category")category:String,
//                 @Query("apiKey")apiKey:String): Call<ListResponse<News>>
//
//    @GET("top-headlines/")
//    fun getTechnology(@Query("country")country:String,
//                      @Query("category")category:String,
//                      @Query("apiKey")apiKey:String): Call<ListResponse<News>>





}