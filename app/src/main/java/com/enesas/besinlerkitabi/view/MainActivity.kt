package com.enesas.besinlerkitabi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enesas.besinlerkitabi.R
import com.enesas.besinlerkitabi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
    }
}