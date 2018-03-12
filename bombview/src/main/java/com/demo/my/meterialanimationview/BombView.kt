package com.demo.my.meterialanimationview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.animation.AnimatorSet


/**
 * Created by lenovo on 2018/3/9.
 */
class BombView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    init {
        initializeView()
    }

    val bombViewDataProvider: BombViewDataProvider = BombViewDataProvider(this)
    fun initializeView() {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        bombViewDataProvider.syncViewSize(width, height)
        //设置View的形状为正方形，边长至少100dp
        val minSize = Math.min(measuredHeight, measuredWidth)
        val dp_100 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, context.resources.displayMetrics).toInt()
        val minLength = Math.max(minSize, dp_100)
        setMeasuredDimension(minLength, minLength)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bombViewDataProvider.syncViewSize(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        bombViewDrawer.setCanvas(canvas)
//        bombViewDrawer.setProvider(bombViewDataProvider)
        val bombViewDrawer = BombViewDrawer(canvas, bombViewDataProvider)
        bombViewDrawer.drawHead()//头部小房子
        bombViewDrawer.drawGround()//地板
        bombViewDrawer.drawBody()//填充身体部分，包括高光效果
        bombViewDrawer.drawBodyBorder()//描出身体边框
        bombViewDrawer.drawFace()//脸
        bombViewDrawer.drawFaceShadow()//脸阴影
        bombViewDrawer.drawHeadLine()//头部的炸弹线
        bombViewDrawer.drawBlast()//爆炸
    }

    var animSet = AnimatorSet()
    var isPlayingAnim: Boolean = false

    /**
     * 播放动画
     */
    fun startAnim() {
        if (isPlayingAnim) stopAnim()
        isPlayingAnim = true
        //动画可分解成 左右摇头、点燃引线、头部向上动，眼睑和嘴部变化、爆炸

        animSet.playSequentially(
                bombViewDataProvider.getFaceLRAnim(),
                bombViewDataProvider.getFaceTBAnim(),
                bombViewDataProvider.getFaceChangeAnim(),
                bombViewDataProvider.getBlastAnim()
                )
        animSet.play(bombViewDataProvider.getFaceLRAnim())
                .with(bombViewDataProvider.getHeadLineAnim())
        animSet.start()
    }

    fun stopAnim() {
        isPlayingAnim = false
        animSet.cancel()
        //数据恢复到默认值
        bombViewDataProvider.initData()
        invalidate()
    }
}
