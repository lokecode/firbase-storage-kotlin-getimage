package com.demo.picturefirebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.demo.picturefirebasekotlin.repository.Login
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val currentuser = Login("", "").user()
        if(currentuser != null) {
            startActivity(Intent(this@LoginActivity,  MainActivity::class.java))
            finish()
        }
        login()
    }

    private fun login() {

        loginButton.setOnClickListener {

            if(TextUtils.isEmpty(usernameInput.text.toString())){
                usernameInput.setError("Please enter username")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(passwordInput.text.toString())){
                usernameInput.setError("Please enter password")
                return@setOnClickListener
            }
            val login = Login(usernameInput.text.toString(), passwordInput.text.toString())

            if (login.login().isSuccessful) {
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Login failed, please try again! ", Toast.LENGTH_LONG).show()
            }


        }

        registerText2.setOnClickListener{
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))

        }
    }
}