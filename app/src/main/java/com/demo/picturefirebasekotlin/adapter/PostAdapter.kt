package com.demo.picturefirebasekotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*

class PostAdapter(
        private val imagesData: List<String>,
        private val user: List<User?>,
        private val postData: ArrayList<Post?>,
): RecyclerView.Adapter<PostAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return imagesData.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val imagePost = imagesData[position]
            val post = postData[position]
            val postUserName = StringBuilder()

            // getting the post name by cheking if the user uid if the same as post uid
            for(user in user) {
                if(user?.uid == post?.uid) {
                    postUserName.append(user?.name)
                }
            }
            Glide.with(holder.itemView).load(post?.url).into(holder.itemView.ProfileImage)
            holder.itemView.textView2.text = post?.discription
            holder.itemView.textView3.text = postUserName.toString()

        Glide.with(holder.itemView).load(imagePost).into(holder.itemView.ivImage)
    }
}