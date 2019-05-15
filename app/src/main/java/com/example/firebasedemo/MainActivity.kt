package com.example.firebasedemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var database: FirebaseFirestore
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseFirestore.getInstance()
        dialog = SpotsDialog.Builder().setContext(this@MainActivity).build()

        btnPost.setOnClickListener {
            postComment()
        }

        btnGet.setOnClickListener {
            getComment()
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun postComment() {

        if (editTitle.text.isEmpty()) {
            toast("Title isEmpty")
            return
        }

        if (editContent.text.isEmpty()) {
            toast("Content isEmpty")
            return
        }

        dialog.show()

        val title = editTitle.text.toString()
        val content = editContent.text.toString()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val startAt = sdf.format(Date())
        val post = Post(title, content, startAt)

        database.collection("posts")
            .add(post)
            .addOnSuccessListener {
                //dialog.dismiss()
                getComment()
            }
            .addOnFailureListener { log("Failure: $it") }

    }

    private fun getComment() {

        dialog.show()

        database.collection("posts")
            .orderBy("startAt", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                val list = arrayListOf<Get>()
                if (task.isComplete) {
                    for (document in task.result!!) {
                        val get = document.toObject(Get::class.java)
                        list.add(get)
                    }
                    buildRecyclerView(list)
                    dialog.dismiss()
                }
            }
            .addOnFailureListener { log("Failure: $it") }
    }

    private fun buildRecyclerView(list: ArrayList<Get>) {

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PostAdapter(this@MainActivity, list)
        recyclerView.adapter = adapter

    }

    private fun log(s: String) {
        Log.d("MainActivityAA",s)
    }

    private fun toast(s: String) {
        Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
    }
}
