package com.demo.picturefirebasekotlin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.item_image.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.bumptech.glide.Glide
import com.demo.picturefirebasekotlin.model.Post
import com.demo.picturefirebasekotlin.viewmodel.MainActivityViewModel

import com.google.firebase.auth.FirebaseAuth

private const val REQUEST_CODE_IMAGE_PICK = 0



@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var recyclerAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initViewModel()

        uploadPost.setOnClickListener {
            Intent(this@MainActivity,  UploadPostActivity::class.java)
        }

        signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity,  LoginActivity::class.java))
        }

    }

    private fun initRecyclerView() {
        rvImages.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = PostAdapter()
        rvImages.adapter = recyclerAdapter
    }

    private fun initViewModel() {
        val viewModel:MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer {
            if(it != null) {
                recyclerAdapter.setCountryList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeAPICall()

    }

}
