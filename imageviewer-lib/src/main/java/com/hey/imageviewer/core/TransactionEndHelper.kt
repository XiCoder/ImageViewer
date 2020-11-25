package com.hey.imageviewer.core

import android.transition.*
import android.view.View
import android.view.ViewGroup
import androidx.core.transition.doOnEnd
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import com.hey.base.imageviewer.core.ViewHolder
import com.hey.base.imageviewer.core.ViewerConfig

object TransactionEndHelper {

    private const val TAG = "TransactionEndHelper"

    fun start(startView: View, holder: ViewHolder, dialogFragment: DialogFragment) {
        val doTransaction = {
            TransitionManager.beginDelayedTransition(holder.itemView as ViewGroup, transitionSet()
                .apply {
                    addListener(doOnEnd {
                        dialogFragment.dismiss()
                    })
                })
            beforeTransaction(startView, holder)
        }
        holder.itemView.postDelayed(doTransaction, 50)
    }

    private fun beforeTransaction(startView: View, holder: ViewHolder) {
        val image = holder.getViewerView()
        image.translationX = 0f
        image.translationY = 0f
        image.layoutParams = image.layoutParams.apply {
            width = startView.width
            height = startView.height
            if(this is ViewGroup.MarginLayoutParams){
                val location = IntArray(2)
                getLocationOnScreen(startView, location)
                leftMargin=location[0]
                topMargin=location[1]- ViewerConfig.statusBarHeight - ViewerConfig.actionBarHeight
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