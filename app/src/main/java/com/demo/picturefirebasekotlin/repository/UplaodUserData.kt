package com.demo.picturefirebasekotlin.repository

import com.demo.picturefirebasekotlin.User
import com.demo.picturefirebasekotlin.data.firebase.AuthService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class UplaodUserData(discription: String, profileImage: String, filename: String) {
    val auth = AuthService().authInstance()
    val uid = auth.uid.toString()
    val fileName = filename
    val post = listOf(discription, uid, profileImage)
    fun makePost() {
        Firebase.firestore.collection("post").add(post)
    }
    fun postImage(): StorageReference {
        val imageRef = Firebase.storage.reference.child("images/${fileName}.jpg")
        return imageRef
    }
}