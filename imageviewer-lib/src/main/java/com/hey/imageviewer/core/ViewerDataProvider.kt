package com.hey.imageviewer.core

interface ViewerDataProvider<T:ViewerData> {

    /**
     * 获取数据
     */
    fun getData(): List<T>?


    /**
     * 获取点击的position
     */
    fun getInitPosition(): Int

}