package com.victoris.canvas_drawing

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider
import com.victoris.canvas_drawing.databinding.FragmentCanvasBinding

class CanvasFragment : Fragment() {

    // binding
    private lateinit var binding: FragmentCanvasBinding

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
    private lateinit var sliderSize: Slider
    private lateinit var paintView: PaintView

    // data
    companion object {
        var path = android.graphics.Path()
        var paintBrush = Paint()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCanvasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        sliderSize = binding.sliderSize
        paintView = binding.paintView.paintView

        sliderSize.addOnChangeListener { slider, value, fromUser ->
            PaintView.currentSize = value
        }

        paintBlk.setOnClickListener {
            selectColor(paintBlk, Color.BLACK)
        }
        paintRed.setOnClickListener {
            selectColor(paintRed, ContextCompat.getColor(requireContext(), R.color.paint_red))
        }
        paintOrg.setOnClickListener {
            selectColor(paintOrg, ContextCompat.getColor(requireContext(), R.color.paint_orange))
        }
        paintYel.setOnClickListener {
            selectColor(paintYel, ContextCompat.getColor(requireContext(), R.color.paint_yellow))
        }
        paintGrn.setOnClickListener {
            selectColor(paintGrn, ContextCompat.getColor(requireContext(), R.color.paint_green))
        }
        paintBlu.setOnClickListener {
            selectColor(paintBlu, ContextCompat.getColor(requireContext(), R.color.paint_blue))
        }
        paintInd.setOnClickListener {
            selectColor(paintInd, ContextCompat.getColor(requireContext(), R.color.paint_indigo))
        }
        paintVio.setOnClickListener {
            selectColor(paintVio, ContextCompat.getColor(requireContext(), R.color.paint_violet))
        }

        btnUndo.setOnClickListener{
            paintView.undo()
            paintView.postInvalidate()
        }
        btnSave.setOnClickListener {
            paintView.save()
        }
    }

    private fun selectColor(button: ImageButton, colorResId: Int) {
        // Set the paint color
        paintBrush.color = colorResId
        currentColor(paintBrush.color)

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
        PaintView.currentBrush = color
        path = android.graphics.Path()
    }
}