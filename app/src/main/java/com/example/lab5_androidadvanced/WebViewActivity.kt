package com.example.lab5_androidadvanced

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.lab5_androidadvanced.R

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        findViewById<WebView>(R.id.webView).apply {
            loadUrl(intent.getStringExtra("link")!!)
            webViewClient = WebViewClient()
        }
    }
}