package com.example.childmonitoringel

/*import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi

class WEBSITE : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_website)
        val webView=findViewById<WebView>(R.id.webview1)
        webViewSetUp(webView)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetUp(webView: WebView){
        webView.webViewClient= WebViewClient()
        webView.apply {
            settings.javaScriptEnabled=true
            settings.safeBrowsingEnabled=true
            loadUrl("https://crescere.streamlit.app/")

        }

    }
}
*/

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WEBSITE : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cam)
        val bt=findViewById<Button>(R.id.Btn2)
        bt.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://crescere.streamlit.app"))
            startActivity(intent)
        }


    }
}