package com.demo.picturefirebasekotlin.repository

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.demo.picturefirebasekotlin.UploadUserDataActivity
import com.demo.picturefirebasekotlin.data.firebase.AuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignUp(email: String, password: String) {

    private val auth = AuthService().authInstance()

    val email = email
    val password = password

    fun signUp(): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }
}

