package com.victoris.canvas_drawing

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.slider.Slider
import com.victoris.canvas_drawing.PaintView.Companion.currentBrush
import com.victoris.canvas_drawing.databinding.ActivityMainBinding
import kotlin.io.path.Path

class MainActivity : AppCompatActivity() {

    // binding
    private lateinit var binding: ActivityMainBinding

    // widgets
    private lateinit var paintBlk: ImageButton
    private lateinit var paintRed: ImageButton
    private lateinit var paintOrg: ImageButton
    private lateinit var paintYel: ImageButton
    private lateinit var paintGrn: ImageButton
    private lateinit var paintBlu: ImageButton
    private lateinit var paintInd: ImageButton
    private lateinit var paintVio: ImageButton

    private lateinit var btnUndo: ImageView
    private lateinit var btnSave: ImageView
    private lateinit var btnClear: ImageView
    private lateinit var sliderSize: Slider
    private lateinit var paintView: PaintView

    // data
    companion object {
        var path = android.graphics.Path()
        var paintBrush = Paint()
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

        // widget init
        paintBlk = binding.blackColor
        paintRed = binding.redColor
        paintOrg = binding.orangeColor
        paintYel = binding.yellowColor
        paintGrn = binding.greenColor
        paintBlu = binding.blueColor
        paintInd = binding.indigoColor
        paintVio = binding.violetColor

        paintBlk.isSelected = true

        btnUndo = binding.btnUndo
        btnSave = binding.btnSave
        btnClear = binding.btnClear
        sliderSize = binding.sliderSize
        paintView = binding.paintView.paintView

        sliderSize.addOnChangeListener { slider, value, fromUser ->
            PaintView.currentSize = value
        }

        paintBlk.setOnClickListener {
            selectColor(paintBlk, Color.BLACK)
        }
        paintRed.setOnClickListener {
            selectColor(paintRed, ContextCompat.getColor(this@MainActivity, R.color.paint_red))
        }
        paintOrg.setOnClickListener {
            selectColor(paintOrg, ContextCompat.getColor(this@MainActivity, R.color.paint_orange))
        }
        paintYel.setOnClickListener {
            selectColor(paintYel, ContextCompat.getColor(this@MainActivity, R.color.paint_yellow))
        }
        paintGrn.setOnClickListener {
            selectColor(paintGrn, ContextCompat.getColor(this@MainActivity, R.color.paint_green))
        }
        paintBlu.setOnClickListener {
            selectColor(paintBlu, ContextCompat.getColor(this@MainActivity, R.color.paint_blue))
        }
        paintInd.setOnClickListener {
            selectColor(paintInd, ContextCompat.getColor(this@MainActivity, R.color.paint_indigo))
        }
        paintVio.setOnClickListener {
            selectColor(paintVio, ContextCompat.getColor(this@MainActivity, R.color.paint_violet))
        }

        btnUndo.setOnClickListener{
            paintView.undo()
            paintView.postInvalidate()
        }
        btnSave.setOnClickListener {
            paintView.save()
        }
        btnClear.setOnClickListener {
            // confirmation dialog
            val dialog = DeleteConfirmationDialog()

            dialog.showDialog(
                this@MainActivity,
                paintView)
        }
    }

    private fun selectColor(button: ImageButton, colorResId: Int) {
        // Set the paint color
        CanvasFragment.paintBrush.color = colorResId
        currentColor(CanvasFragment.paintBrush.color)

        // Deselect all buttons
        paintBlk.isSelected = false
        paintRed.isSelected = false
        paintOrg.isSelected = false
        paintYel.isSelected = false
        paintGrn.isSelected = false
        paintBlu.isSelected = false
        paintInd.isSelected = false
        paintVio.isSelected = false

        // Select the clicked button
        button.isSelected = true
    }

    private fun currentColor(color: Int) {
        currentBrush = color
        CanvasFragment.path = android.graphics.Path()
    }
}