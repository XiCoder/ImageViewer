package com.hey.imageviewer.core

import android.transition.*
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.hey.base.imageviewer.core.ViewHolder
import com.hey.base.imageviewer.core.ViewerConfig

object TransactionHelper {

    fun start(startView: View, holder: ViewHolder) {
        beforeTransaction(startView, holder)
        val doTransaction = {
            TransitionManager.beginDelayedTransition(holder.itemView as ViewGroup, transitionSet())
            centerTransaction(holder)
        }
        holder.itemView.postDelayed(doTransaction, 50)

    }

    private fun beforeTransaction(startView: View, holder: ViewHolder) {
        val image = holder.getViewerView()
        image.layoutParams = image.layoutParams.apply {
            width = startView.width
            height = startView.height
            val location = IntArray(2)
            getLocationOnScreen(startView, location)
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = location[0]
                topMargin = location[1]- ViewerConfig.statusBarHeight-ViewerConfig.actionBarHeight
            }
        }

    }

    private fun centerTransaction(holder: ViewHolder) {
        val image = holder.getViewerView()
        image.layoutParams = image.layoutParams.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = 0
                topMargin = 0
            }
        }
    }

    private fun getLocationOnScreen(startView: View, location: IntArray) {
        startView.getLocationOnScreen(location)
        if (startView.layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL) {
            location[0] = startView.context.resources.displayMetrics.widthPixels - location[0] - startView.width
        }
    }

    private fun transitionSet(): Transition {
        return TransitionSet().apply {
            addTransition(ChangeBounds())
            addTransition(ChangeImageTransform())
            addTransition(ChangeTransform())
            duration = ViewerConfig.duration
        }
    }
}