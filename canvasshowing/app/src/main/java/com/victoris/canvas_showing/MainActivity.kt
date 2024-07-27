package com.victoris.canvas_showing

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.victoris.canvas_showing.databinding.ActivityMainBinding
import kotlin.math.ceil
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    // binding
    private lateinit var binding: ActivityMainBinding

    // widgets
    private lateinit var rvDrawing: RecyclerView

    // data
    private val bitmapList = mutableListOf<Bitmap>()
    private lateinit var drawAdapter: DrawAdapter

    companion object {
        const val storageUrl = "gs://aspirasi-temp.appspot.com"
        const val databaseUrl = "https://aspirasi-temp-default-rtdb.asia-southeast1.firebasedatabase.app/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // remove action bar in activity
        supportActionBar?.hide()
        enableEdgeToEdge()

        // init widget
        rvDrawing = binding.rvDrawing
        // setup adapter
        drawAdapter = DrawAdapter(bitmapList, this@MainActivity)
        rvDrawing.layoutManager = GridLayoutManager(
            this,
            2)
        rvDrawing.adapter = drawAdapter

        val ref = FirebaseDatabase
            .getInstance(databaseUrl)
            .reference
            .child("images")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val imageUrl = snapshot.getValue(String::class.java)
                if (imageUrl != null) {
                    downloadImage(imageUrl)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) { }

            override fun onChildRemoved(snapshot: DataSnapshot) { }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed to load images", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun downloadImage(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                    drawAdapter.notifyItemInserted(bitmapList.size - 1)

                    // Update the span count based on the new size
                    val spanCount = ceil(sqrt((bitmapList.size/4).toDouble()))
                    (rvDrawing.layoutManager as GridLayoutManager).spanCount =
                        if (spanCount.toInt() == 0) 1 else spanCount.toInt()
                }

                override fun onLoadCleared(placeholder: Drawable?) { }
            })
    }
}