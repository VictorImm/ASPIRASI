package com.victoris.canvas_drawing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Environment
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.victoris.canvas_drawing.CanvasFragment.Companion.paintBrush
import com.victoris.canvas_drawing.CanvasFragment.Companion.path
import com.victoris.canvas_drawing.MainActivity.Companion.databaseUrl
import com.victoris.canvas_drawing.MainActivity.Companion.storageUrl
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

class PaintView : View {

    private var params : ViewGroup.LayoutParams? = null

    companion object {
        var pathList = ArrayList<Path>()
        var colorList = ArrayList<Int>()
        var sizeList = ArrayList<Float>()

        var currentBrush = Color.BLACK
        var currentSize = 4.0f
    }

    constructor(context: Context) : this(context, null) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                path = Path()
                path.moveTo(x, y)
                pathList.add(path)
                colorList.add(currentBrush)
                sizeList.add(currentSize)

                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }
            else -> return false
        }
        postInvalidate()
        return false
    }

    override fun onDraw(canvas: Canvas) {
        Log.d("PathListSize", (pathList.size).toString())
        Log.d("ColorListSize", (colorList.size).toString())

        Log.d("PathList", pathList.toString())
        Log.d("ColorList", colorList.toString())

        for(i in pathList.indices) {
            paintBrush.isAntiAlias = true
            paintBrush.color = colorList[i]
            paintBrush.style = Paint.Style.STROKE
            paintBrush.strokeJoin = Paint.Join.ROUND
            paintBrush.strokeWidth = sizeList[i]

            canvas.drawPath(pathList[i], paintBrush)
            invalidate()
        }
    }

    fun undo() {
        if (pathList.isNotEmpty()) {
            pathList.removeLast()
            colorList.removeLast()
            sizeList.removeLast()

            postInvalidate()
        }
    }

    fun save() {
        if (pathList.isNotEmpty()) {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            this.draw(canvas)

            // save image to firebase
            saveImageToDatabase(bitmap)

            Toast.makeText(context, "Image Saved!", Toast.LENGTH_LONG).show()

            // clear canvas
            clearCanvas()


        } else {
            Toast.makeText(context, "No Image!", Toast.LENGTH_LONG).show()
        }

    }

    fun clearCanvas() {
        pathList.clear()
        colorList.clear()
        sizeList.clear()

        postInvalidate()
    }

    private fun saveImageToDatabase(bitmap: Bitmap) {
        val storage = FirebaseStorage
            .getInstance(storageUrl)
            .reference
            .child("images/${UUID.randomUUID()}.png")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = storage.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
            storage.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                saveImageUrlToDatabase(imageUrl)
            }
        }
    }

    private fun saveImageUrlToDatabase(imageUrl: String) {
        val ref = FirebaseDatabase
            .getInstance(databaseUrl)
            .reference
            .child("images")
            .push()

        ref.setValue(imageUrl).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Image URL Saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error Saving Image URL", Toast.LENGTH_SHORT).show()
            }
        }
    }
}