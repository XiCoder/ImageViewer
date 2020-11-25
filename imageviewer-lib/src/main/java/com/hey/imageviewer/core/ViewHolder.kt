package com.hey.imageviewer.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    /**
     * 获取需要展示的view
     */
    abstract fun getViewerView(): View

    fun <T:View> getView(id:Int):T{
        return itemView.findViewById(id)
    }
}