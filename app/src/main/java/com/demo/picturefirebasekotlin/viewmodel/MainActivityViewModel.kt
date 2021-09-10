package com.demo.picturefirebasekotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.picturefirebasekotlin.model.Post
import com.demo.picturefirebasekotlin.retrofit.RetroInstance
import com.demo.picturefirebasekotlin.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {

    lateinit var liveDataList: MutableLiveData<List<Post>>

    init {
        liveDataList = MutableLiveData()
    }


    fun getLiveDataObserver(): MutableLiveData<List<Post>> {
        return liveDataList
    }

    fun makeAPICall() {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService  = retroInstance.create(RetroServiceInterface::class.java)
        val call  = retroService.getCountryList()
        call.enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                    call: Call<List<Post>>,
                    response: Response<List<Post>>
            ) {
                liveDataList.postValue(response.body())
            }
        })


    }
}