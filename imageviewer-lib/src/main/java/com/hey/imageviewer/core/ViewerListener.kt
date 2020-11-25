package com.hey.imageviewer.core

import com.hey.base.imageviewer.core.ViewHolder

interface ViewerListener {
    /**
     * ViewerView初始化监听
     */
    fun onInit(viewHolder: ViewHolder)

}