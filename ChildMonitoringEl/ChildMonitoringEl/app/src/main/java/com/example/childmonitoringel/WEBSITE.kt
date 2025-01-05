package com.example.childmonitoringel

import android.os.Build
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
            loadUrl("https://saumyasingh.netlify.app/")

        }

    }
}