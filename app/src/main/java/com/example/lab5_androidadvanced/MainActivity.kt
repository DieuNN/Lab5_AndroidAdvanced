package com.example.lab5_androidadvanced

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5_androidadvanced.databinding.ActivityMain2Binding
import org.w3c.dom.Element
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URL
import java.util.*

var newsList = ArrayList<News>()
private var adapter: NewsAdapter? = null

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMain2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnDownload.setOnClickListener {
            if (binding.edtLink.text.isBlank()) {
                Toast.makeText(this, "Cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val content = RSSFeed().execute(binding.edtLink.text.toString())

        }
        adapter = NewsAdapter(this, R.layout.news_items, newsList)

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { adapterView, view, index, l ->
            val intent: Intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("link", newsList[index].link)
            startActivity(intent)
        }


    }


    companion object {
        private const val TAG = "ERR"
    }


}

class RSSFeed() : AsyncTask<String?, Void?, String>() {
    override fun onPostExecute(s: String) {
        super.onPostExecute(s)
        val xmlParse = XMLParse()
        val document = xmlParse.getDocument(s) ?: return

        val nodeList = document!!.getElementsByTagName("item")
        var title = ""
        var link = ""
        var description = ""
        for (i in 0 until nodeList.length) {
            val element = nodeList.item(i) as Element
            title = """
                        ${xmlParse.getTitleValue(element, "title")}
                        
                        """.trimIndent()
            link = """
                        ${xmlParse.getTitleValue(element, "link")}
                        
                        """.trimIndent()
            description = "${xmlParse.getDescriptionValue(element, "description")}"
            newsList.add(News(title, description, link))
            adapter?.notifyDataSetChanged()
        }
    }

    override fun doInBackground(vararg strings: String?): String {
        val content = StringBuilder()
        val url: URL
        try {
            url = URL(strings[0])
            val reader = InputStreamReader(url.openConnection().getInputStream())
            val bufferedReader = BufferedReader(reader)
            var line: String? = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                content.append(line)
            }
            bufferedReader.close()
        } catch (e: Exception) {

        }


        return content.toString()
    }


}

