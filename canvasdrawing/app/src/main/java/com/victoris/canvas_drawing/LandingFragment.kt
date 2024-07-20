package com.victoris.canvas_drawing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.victoris.canvas_drawing.databinding.FragmentLandingBinding


class LandingFragment : Fragment() {

    // binding
    private lateinit var binding: FragmentLandingBinding

    // widgets
    private lateinit var btn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // widget init
        btn = binding.button

        btn.setOnClickListener {
            val action = LandingFragmentDirections.actionLargeFragmentToSmallFragment()
            this.findNavController().navigate(action)
        }
    }
}