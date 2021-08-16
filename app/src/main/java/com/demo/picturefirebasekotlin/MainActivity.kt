package com.demo.picturefirebasekotlin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.demo.picturefirebasekotlin.databinding.ActivityMainBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()

        binding.getImage.setOnClickListener {   // getiamge is a botton and binding is the layout

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Fetching image....")
            progressDialog.setCancelable(false)
            progressDialog.show()


            val imageName = binding.etImageId.text.toString()
            val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName.jpg")   // important



            val localfile = File.createTempFile("tempImage", "jpg") // important
            storageRef.getFile(localfile).addOnSuccessListener {

                if(progressDialog.isShowing)
                    progressDialog.dismiss()

                val bitmap = BitmapFactory.decodeFile(File.createTempFile("tempImage", "jpg").absolutePath)  // important
                binding.imageView.setImageBitmap(bitmap)   // important


            }.addOnFailureListener {

                if(progressDialog.isShowing)
                    progressDialog.dismiss()
                Toast.makeText(this, "Failed to retreave image", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun start() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}