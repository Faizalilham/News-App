package com.funcode.newsapi.model

data class ListResponse<T>(
    val status : String,
    val totalResults : Int,
    val articles : MutableList<T>
)

data class SingleResponse<T>(
    val status : String,
    val totalResults : Int,
    val articles : T
)