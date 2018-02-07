package com.demo.my.kotlindemo.extension

import android.content.Context
import android.widget.Toast

/**
 * Created by lenovo on 2018/2/8.
 */
fun Context.tip(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}