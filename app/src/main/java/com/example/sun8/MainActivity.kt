package com.example.sun8


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * Fragment 页面中返回按钮 第二部分
         * fragment2 是 res\layout\activity_main.xml 中
         */
        NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.sunNavhostfragment))
    }
    //Fragment 页面中返回按钮 第一部分
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.sunNavhostfragment).navigateUp()
    }
}