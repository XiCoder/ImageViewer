package com.hey.imageviewer

import android.view.View
import com.hey.imageviewer.core.ViewHolder

class ImageViewHolder(view: View): ViewHolder(view) {

    override fun getViewerView(): View {
        return itemView.findViewById(R.id.image_viewer)
    }



}