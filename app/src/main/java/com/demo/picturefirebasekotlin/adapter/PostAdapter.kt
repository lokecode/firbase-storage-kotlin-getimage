package com.demo.picturefirebasekotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.picturefirebasekotlin.model.Post
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_image.view.*

class PostAdapter(): RecyclerView.Adapter<PostAdapter.ImageViewHolder>() {

    private var postList: List<Post>? = null


    fun setCountryList(getPost: List<Post>?) {
        this.postList = getPost
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ImageViewHolder {
        return ImageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_image,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        if (postList == null) return 0
        else  return postList?.size!!
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(postList?.get(position)!!)
    }
    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val region = itemView.textView2
        val userName = itemView.textView3

        fun bind(data: Post) {
            region.text = data.region
            userName.text = data.name
        }

    }
}