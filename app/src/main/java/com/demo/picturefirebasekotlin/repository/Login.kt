package com.demo.picturefirebasekotlin.repository

import android.widget.Toast
import com.demo.picturefirebasekotlin.data.firebase.AuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class Login(userName: String, password: String) {

    private val auth = AuthService().authInstance()

    val username = userName
    val password = userName

    fun login(): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(username, password)
    }

    fun user(): FirebaseUser? {
        return auth.currentUser
    }
}