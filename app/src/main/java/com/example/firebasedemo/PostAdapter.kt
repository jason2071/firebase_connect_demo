package com.example.firebasedemo

import android.annotation.SuppressLint
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.image_layout.view.*
import kotlinx.android.synthetic.main.item_post_layout.view.*

class PostAdapter(
    private val mActivity: MainActivity,
    private val list: ArrayList<Get>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_layout, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as PostViewHolder
        holder.bind(list[position], mActivity)
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("InflateParams")
        fun bind(get: Get, mActivity: MainActivity) {
            itemView.tvTitle.text = get.title
            itemView.tvContent.text = get.content
            itemView.tvStartAt.text = get.startAt

            if (get.image != "") {
                itemView.setOnClickListener {
                    val imageDialog = AlertDialog.Builder(mActivity)
                    val inflater = mActivity.layoutInflater
                    val imageView = inflater.inflate(R.layout.image_layout, null)

                    Glide.with(mActivity)
                        .load(get.image)
                        .apply(RequestOptions())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView.imageDisplay)

                    imageDialog.setView(imageView)
                    imageDialog.show()
                }
            }
        }

    }
}