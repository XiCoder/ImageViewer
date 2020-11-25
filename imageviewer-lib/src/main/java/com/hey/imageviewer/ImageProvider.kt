package com.hey.imageviewer

import com.hey.imageviewer.core.ViewerDataProvider

class ImageProvider(var mData:List<ImageData>?) : ViewerDataProvider<ImageData> {

    private var _initPosition = 0

    override fun getData(): List<ImageData>? {
        return mData
    }

    override fun getInitPosition(): Int {
        return _initPosition
    }

    fun setInitPosition(initPosition: Int) {
        this._initPosition = initPosition
    }
}