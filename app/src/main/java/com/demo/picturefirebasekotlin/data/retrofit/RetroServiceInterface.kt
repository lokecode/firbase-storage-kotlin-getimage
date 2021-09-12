package com.demo.picturefirebasekotlin.retrofit

import com.demo.picturefirebasekotlin.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {

    @GET("v2")
    fun getCountryList(): Call<List<Post>>
}