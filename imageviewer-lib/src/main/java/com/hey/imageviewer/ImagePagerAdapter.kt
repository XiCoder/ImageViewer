package com.hey.imageviewer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hey.base.imageviewer.core.ViewerAdapter
import com.hey.imageviewer.core.ViewerData
import com.hey.imageviewer.core.ViewerListener

class ImagePagerAdapter(data: MutableList<ViewerData>?, override var initPosition: Int, private var listener: ViewerListener) :
    ViewerAdapter<ViewerData, ImageViewHolder>(data) {

    fun setList(list: List<ViewerData>?, initPosition: Int) {
        this.initPosition = initPosition
        super.setData(list)
    }

    fun setImageViewerListener(listener: ViewerListener) {
        this.listener = listener
    }

    override fun bindView(holder: ImageViewHolder, position: Int, item: ViewerData) {
        val image = holder.getView<ImageView>(R.id.image_viewer)
        Glide.with(image)
            .load(item.getUlr())
            .into(image)
        if (holder.adapterPosition == initPosition) {
            listener.onInit(holder)
        }
    }

    override fun getViewerListener(): ViewerListener {
        return listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viewer, parent, false)
        return ImageViewHolder(view)
    }

}