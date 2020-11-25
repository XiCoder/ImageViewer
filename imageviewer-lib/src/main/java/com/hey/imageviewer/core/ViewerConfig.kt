package com.hey.imageviewer.core

/**
 * Viewer配置项
 */
object ViewerConfig {
    //状态栏高度，用于修复getScreenLocation问题
    var statusBarHeight = 0

    //如果有ActionBar,需要设置ActionBar高度
    var actionBarHeight = 0

    //动画持续时间
    var duration = 300L
}