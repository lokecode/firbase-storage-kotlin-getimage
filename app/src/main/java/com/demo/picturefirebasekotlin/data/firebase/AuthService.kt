package com.demo.picturefirebasekotlin.data.firebase

import com.google.firebase.auth.FirebaseAuth

class AuthService {
    fun authInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}