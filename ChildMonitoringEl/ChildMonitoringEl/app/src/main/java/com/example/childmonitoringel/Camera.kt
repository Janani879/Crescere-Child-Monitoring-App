package com.example.childmonitoringel

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Camera : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val tutin=findViewById<Button>(R.id.tutbtn)
        tutin.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=PAvGwHqgr8k"))
            startActivity(intent)
        }
        val btn=findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            val intent=Intent(this,cam::class.java)
            startActivity(intent)
        }

    }
}