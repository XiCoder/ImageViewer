package com.hey.base.imageviewer.core

import androidx.recyclerview.widget.RecyclerView

abstract class ViewerAdapter<T, VH : ViewHolder>(private var data: List<T>?) : RecyclerView.Adapter<VH>() {

    abstract var initPosition: Int

    var recyclerView:RecyclerView?=null

    private val listener: ViewerListener by lazy {
        getViewerListener()
    }

    override fun getItemCount(): Int {
        data?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindView(holder, position, data!![position])
        if (holder.bindingAdapterPosition == initPosition) {
            listener.onInit(holder)
        }
    }

    fun setData(data: List<T>?){
        this.data=data
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView=recyclerView
    }

    abstract fun bindView(holder: VH, position: Int,item:T)

    abstract fun getViewerListener(): ViewerListener
}