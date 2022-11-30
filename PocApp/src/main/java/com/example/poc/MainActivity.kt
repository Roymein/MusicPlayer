package com.example.poc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
        val anyResult = list.any { it.length <= 5 }
        val allResult = list.all { it.length <= 5 }
        println("anyResult is $anyResult, allResult is $allResult")

    }
}