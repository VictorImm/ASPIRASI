package com.victoris.canvas_showing

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class DrawAdapter(
    private val listBitmap: List<Bitmap>,
    private val context: Context
): RecyclerView.Adapter<DrawAdapter.DrawViewHolder>() {

    class DrawViewHolder(drawView: View): RecyclerView.ViewHolder(drawView) {
        val ivDraw: ImageView = itemView.findViewById(R.id.iv_draw)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DrawViewHolder {
        val view:View =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.row_draw,
                    parent,
                    false
                )
        return DrawViewHolder(view)
    }

    override fun getItemCount() = listBitmap.size

    override fun onBindViewHolder(holder: DrawViewHolder, position: Int) {
        val draw = listBitmap[position]

        holder.ivDraw.setImageBitmap(draw)
    }
}