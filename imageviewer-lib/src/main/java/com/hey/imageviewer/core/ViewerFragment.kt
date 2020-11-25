package com.hey.imageviewer.core

import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.hey.imageviewer.ImagePagerAdapter
import com.hey.imageviewer.R
import kotlinx.android.synthetic.main.imageviewer_layout.*

/**
 * 大图显示Dialog
 *
 * @author Hey
 */
class ViewerFragment() : DialogFragment(), ViewerListener {
    private lateinit var viewProvider: ViewProvider
    private lateinit var viewerDataProvider: ViewerDataProvider<*>
    private var currentPosition = 0

    private val adapter by lazy {
        ImagePagerAdapter(null,0,this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_NoTitle).apply {
            this.window?.apply {
                //保持透明度不变，重要
                setWindowAnimations(R.style.Animation_Keep)
                decorView.setPadding(0, 0, 0, 0)
                //设置宽高为全屏
                val params = WindowManager.LayoutParams()
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = WindowManager.LayoutParams.MATCH_PARENT
                attributes = params
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.imageviewer_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //获得状态栏高度，修复getScreenLocation()的高度差问题
        ViewerConfig.statusBarHeight = getStatusBarHeight()
        dialog?.setOnKeyListener { _, keyCode, event ->
            var handleKey = false
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP) {
                dismissTransition()
                handleKey = true
            }
            return@setOnKeyListener handleKey
        }
        adapter.setImageViewerListener(this)
        viewpager.offscreenPageLimit = 1
        viewpager.adapter = adapter
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPosition = position
            }
        })
        viewpager.setCurrentItem(viewerDataProvider.getInitPosition(), false)
    }


    fun show(viewProvider: ViewProvider, viewerDataProvider: ViewerDataProvider<*>, parentFragmentManager: FragmentManager, s: String) {
        this.viewProvider = viewProvider
        this.viewerDataProvider = viewerDataProvider
        adapter.setList(viewerDataProvider.getData(), viewerDataProvider.getInitPosition())
        super.show(parentFragmentManager, s)
    }

    override fun onInit(viewHolder: ViewHolder) {
        showTransition(viewHolder)
    }

    private fun showTransition(viewHolder: ViewHolder) {
        val startView = ViewProvider.getView(currentPosition)
        startView?.let {
            TransactionHelper.start(startView, viewHolder)
        }
    }

    private fun dismissTransition() {
        ObjectAnimator.ofFloat(backgroundView, "alpha", 1f, 0f)
            .setDuration(100)
            .start()
        val startView = ViewProvider.getView(currentPosition)

        val holder = adapter.recyclerView?.findViewHolderForAdapterPosition(currentPosition) as ViewHolder
        startView?.let {
            TransactionEndHelper.start(startView, holder, this)
        }
    }

    private fun getStatusBarHeight(): Int {
        var statusBarHeight = -1;
        //获取status_bar_height资源的ID
        var resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight
    }

}