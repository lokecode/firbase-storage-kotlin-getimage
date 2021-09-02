package com.demo.picturefirebasekotlin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.item_image.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.ktx.storage
import com.google.firebase.auth.FirebaseAuth

private const val REQUEST_CODE_IMAGE_PICK = 0



@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var curFile: Uri? = null

    private val firestoreRef = Firebase.firestore
    private val storageRef = Firebase.storage.reference
    private val profileImg = StringBuilder()


    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listFiles()

        uploadPost.setOnClickListener {
            Intent(this@MainActivity,  UploadPostActivity::class.java).also {
                it.putExtra("EXTRA_PROFILEIMG", profileImg.toString())
                startActivity(it)
            }
        }

        signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity,  LoginActivity::class.java))
        }

    }

    private fun listFiles() = CoroutineScope(Dispatchers.IO).launch {
        try {

            //getting data from firebase
            val postImages = storageRef.child("images/").listAll().await()
            val profileImage = storageRef.child("profilePic/${FirebaseAuth.getInstance().uid}.jpg").downloadUrl.await()
            val querySnapshot2 = firestoreRef.collection("user").get().await()
            val querySnapshot = firestoreRef.collection("post").get().await()
            //lists of data from firebase
            val postList = arrayListOf<Post?>()
            val imageUrls = mutableListOf<String>()
            val userList = mutableListOf<User?>()

            //making profile picture public so i can send it to uploadActivity
            profileImg.append(profileImage).toString()

            //adding all img urls to a mutableList
            for(image in postImages.items) {
                val url = image.downloadUrl.await()
                imageUrls.add(url.toString())
            }

            //adding all Post String data to a arrayList
            for(document in querySnapshot.documents) {
                val person = document.toObject<Post?>()
                postList.add(person)
            }
            //adding all Users names and uid to a mutableListList
            for(document2 in querySnapshot2.documents) {
                val user = document2.toObject<User?>()
                userList.add(user)
            }

            //set up the layout
            withContext(Dispatchers.Main) {
                Glide.with(this@MainActivity).load(profileImage.toString()).into(signOut)
                val imageAdapter = PostAdapter(imageUrls, userList, postList)
                rvImages.apply {
                    adapter = imageAdapter
                    layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK) {
            data?.data?.let {
                curFile = it
                ivImage.setImageURI(it)
            }
        }
    }

}
