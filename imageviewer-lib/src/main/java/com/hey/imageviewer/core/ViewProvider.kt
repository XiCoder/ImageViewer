package com.hey.imageviewer.core

import android.util.SparseArray
import android.view.View

object ViewProvider {

    private val viewArray = SparseArray<View>()

    fun getView(position: Int): View? {
        return viewArray.get(position)
    }

    fun putView(position: Int,view:View){
        viewArray.put(position,view)
    }

    fun release(){
        viewArray.clear()
    }
}