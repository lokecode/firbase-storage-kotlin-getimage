package com.demo.picturefirebasekotlin

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.picturefirebasekotlin.data.firebase.AuthService
import com.demo.picturefirebasekotlin.repository.UplaodUserData
import kotlinx.android.synthetic.main.activity_upload_pp.*


class UploadUserDataActivity : AppCompatActivity() {

    private val auth = AuthService().authInstance()
    private lateinit var filepath : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_pp)

        startFileChooser()

        button2.setOnClickListener {
            uploadFile()
            if(auth.currentUser != null) {
                finish()
                startActivity(Intent(this@UploadUserDataActivity,  MainActivity::class.java))
            } else {
                startActivity(Intent(this@UploadUserDataActivity,  LoginActivity::class.java))
            }
        }
    }


    private fun uploadFile() {
        if(filepath != null) {
            var pd = ProgressDialog(this)
            pd.show()
            val name = editTText2.text.toString()
            val UserData = UplaodUserData(name)

            UserData.makeUser()

            UserData.getImage().putFile(filepath)
                .addOnSuccessListener {
                    pd.dismiss()
                    Toast.makeText(applicationContext, "File uploaded", Toast.LENGTH_LONG).show()

                }
                .addOnFailureListener { p0 ->
                    pd.dismiss()
                    Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener { p0 ->
                    var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                    pd.setMessage("uploaded ${progress.toInt()}%")
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