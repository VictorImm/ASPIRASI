package com.victoris.canvas_drawing

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.victoris.canvas_drawing.PaintView.Companion.currentBrush
import com.victoris.canvas_drawing.databinding.ActivityMainBinding
import kotlin.io.path.Path

class MainActivity : AppCompatActivity() {

    // binding
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    // data
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

        // get nav host fragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        //instantiate the navController using navHostFragment
        navController = navHostFragment.navController

        // make sure actions in the ActionBar get propagated to the NavController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}