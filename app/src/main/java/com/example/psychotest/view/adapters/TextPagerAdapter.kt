package com.example.psychotest.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.psychotest.R

class TextPagerAdapter(private var textList: MutableList<String>) : RecyclerView.Adapter<TextPagerAdapter.TextViewHolder>() {

    private val maxItemCount = Int.MAX_VALUE

    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val actualPosition = position % textList.size
        holder.textView.text = textList[actualPosition]
    }

    override fun getItemCount(): Int = maxItemCount

    fun removeItem(position: Int): Boolean {
        if (!isLast()) {
            textList.removeAt(position % textList.size)
            notifyDataSetChanged()
            return false
        }
        return true
    }

    fun isLast(): Boolean {
        return textList.size == 1
    }

}

