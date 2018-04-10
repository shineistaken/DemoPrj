package com.demo.my.weather.ui

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.demo.my.weather.App
import com.demo.my.weather.R
import com.demo.my.weather.extensions.ctx
import com.demo.my.weather.extensions.slideEnter
import com.demo.my.weather.extensions.slideExit
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by lenovo on 2018/3/13.
 */

interface ToolbarManager {
    // NOTE : 2018/3/16  by  lenovo  : 与java不同，kotlin中 接口可以包含代码，不一定需要类来实现
    val toolbar: Toolbar
    // NOTE : 2018/3/16  by  lenovo  : 重写变量的get/set方法
    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun attachToScroll(recycler: RecyclerView) {
        // NOTE : 2018/3/13  by  lenovo  : 在这里 object: 标识匿名内部类
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }

    fun initToolBar() {
        with(toolbar) {
            inflateMenu(R.menu.menu_main)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_settings -> toolbar.ctx.startActivity<SettingActivity>()
                    else -> App.instance.toast("Unknown option")
                }
                true
            }
        }
    }

    fun enableHomeAsUp(back: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
    }

    fun createUpDrawable() = DrawerArrowDrawable(toolbar.ctx).apply { progress = 1f }
}

