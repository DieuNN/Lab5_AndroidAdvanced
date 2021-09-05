package com.example.lab5_androidadvanced

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class NewsAdapter(
    private val mContext: Context,
    private val mResource: Int,
    private val newsList: ArrayList<News>
) : ArrayAdapter<News>(mContext, mResource, newsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(mContext).inflate(mResource, null)
        val title = view.findViewById<TextView>(R.id.txtTitle)
        val des = view.findViewById<TextView>(R.id.txtDes)

        title.text = newsList[position].title
        des.text = newsList[position].description

        return view
    }
}