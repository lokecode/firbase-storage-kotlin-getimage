package com.demo.picturefirebasekotlin

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.activity_upload.button2
import kotlinx.android.synthetic.main.activity_upload.editText2
import kotlinx.android.synthetic.main.activity_upload.imageView
import kotlinx.android.synthetic.main.activity_upload_pp.*
import kotlin.random.Random



class UploadPostActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var filepath : Uri
    private var profileImage = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        val profileImg = intent.getStringExtra("EXTRA_PROFILEIMG")
        profileImage.append(profileImg).toString()
        startFileChooser()

        button2.setOnClickListener {
            uploadFile()
        }

        imageView3.setOnClickListener {
            startActivity(Intent(this@UploadPostActivity,  MainActivity::class.java))
        }
    }

    val imageRef = Firebase.storage.reference
    val FileName = mutableListOf<String>()

    private fun generateFileName() {
        val letters = listOf("q","a","z","w","s","x","e","d","c","r","f","v","t")
        repeat(8) {
            val random = Random.nextInt(letters.size)
            FileName.add(letters[random])
        }
    }

    private fun uploadFile() {
        if(filepath != null) {
            val pd = ProgressDialog(this)
            pd.show()

            val discription = editText2.text.toString()
            val uid = auth.uid.toString()
            val post = Post(discription, uid, profileImage.toString())
            generateFileName()
            val Filename = "${FileName}"

            Firebase.firestore.collection("post").add(post)

            var imageRef = imageRef.child("images/${Filename}.jpg")
            imageRef.putFile(filepath)
                    .addOnSuccessListener {
                        pd.dismiss()
                        Toast.makeText(applicationContext, "File uploaded", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { p0 ->
                        pd.dismiss()
                        Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener { p0 ->
                        val progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                        pd.setMessage("uploaded ${progress.toInt()}%")

                        if(progress.toInt() < 99) {
                            startActivity(Intent(this@UploadPostActivity,  MainActivity::class.java))
                        }
                    }

        }
    }




    private fun startFileChooser() {
        val i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), 111)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==111 && resultCode == RESULT_OK && data != null) {
            filepath = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            imageView.setImageBitmap(bitmap)


        }
    }
}