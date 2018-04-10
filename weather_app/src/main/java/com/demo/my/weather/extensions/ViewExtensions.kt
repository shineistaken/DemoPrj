package com.demo.my.weather.extensions

import android.content.Context
import android.view.View
import android.widget.TextView

/**
 * Created by lenovo on 2018/3/13.
 */

val View.ctx: Context
    get() = context

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}

fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

  var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)