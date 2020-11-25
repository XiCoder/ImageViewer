package com.hey.imageviewer

import com.hey.imageviewer.core.ViewerData

class ImageData(var url:String): ViewerData {
    override fun getUlr(): String {
        return url
    }
}