package com.demo.my.meterialanimationview

import android.animation.Animator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.ofFloat
import android.graphics.*
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ViewAnimator
import android.animation.AnimatorSet
import android.animation.AnimatorListenerAdapter


/**
 * Created by lenovo on 2018/3/9.
 */
class BombViewDataProvider(bombView: View) {
    private val view = bombView
    //画笔
    var mPaint = Paint()
    var mHeadLinePath: Path = Path()
    var mHeadPath: Path = Path()
    var mBodyLightPath: Path = Path()
    val bombColor = Color.parseColor("#88B0E8")
    val bombLineColor = Color.parseColor("#181D82")
    val bombShadowColor = Color.parseColor("#77609ee6")
    val bombCheekColor: Int = Color.parseColor("#5689cc")
    val bombMouthColor = Color.parseColor("#f34671")
    val bombFireColor = Color.parseColor("#fbb42d")
    val lightColor = Color.WHITE
    var bombLineWidth: Int = 0
    //炸弹身体半径 高光半径
    var bodyRadius = 0
    var highLightRadius: Int = 0
    //用于处理线条的分割点
    var groundDashPathEffect: DashPathEffect? = null
    var bodyDashPathEffect: DashPathEffect? = null
    var highLightPathEffect: DashPathEffect? = null
    var mHeadEffect: DashPathEffect? = null
    var mRectF: RectF? = null
    val mPathMeasure = PathMeasure()
    val mPathPosition = FloatArray(2)
    //动画控制相关
    var faceLROffset: Float = 0.toFloat()
    var faceMaxLROffset: Float = 0.toFloat()
    var faceTBOffset = 0f
    var faceMaxTBOffset: Float = 0.toFloat()
    var bombLRRotate = 15f
    val bombMaxLRRotate = 15f
    var bombTBRotate = 0f
    val bombMaxTBRotate = 5f
    var eyeRadius: Float = 0.toFloat()
    var eyeMaxRadius: Float = 0.toFloat()
    var eyeMinRadius: Float = 0.toFloat()
    var headLinePercent = 1f
    var headLineLightRate = 0f
    var maxBlastCircleRadius: Float = 0.toFloat()
    var currentBlastCircleRadius: Float = 0.toFloat()
    var blastCircleRadiusPercent: Float = 0.toFloat()
    //用于计算mouth第二阶段变化
    var mouthMaxWidthOffset: Float = 0.toFloat()
    var mouthMaxHeightOffset: Float = 0.toFloat()
    var mouthWidthOffset = 0f
    var mouthHeightOffset = 0f
    var mouthOffsetPercent = 0f
    //用于计算mouth第一阶段变化
    var mouthFSMaxWidthOffset: Float = 0.toFloat()
    var mouthFSMaxHeightOffset: Float = 0.toFloat()
    var mouthFSWidthOffset = 0f
    var mouthFSHeightOffset = 0f
    //炸弹的中心点
    var bombCenterX: Int = 0
    var bombCenterY: Int = 0

    var viewWith: Int = 0

    var viewHeight: Int = 0

    fun syncViewSize(width: Int, height: Int) {
        this.viewWith = width
        this.viewHeight = height
        syncData()
    }

    private fun syncData() {
        bombLineWidth = viewWith / 35
        bodyRadius = (viewHeight / 3.4f).toInt()
        highLightRadius = bodyRadius / 3 * 2
        mPaint.strokeWidth = bombLineWidth.toFloat()
        faceMaxLROffset = (bodyRadius / 3).toFloat();
        faceLROffset = -faceMaxLROffset;
        eyeMaxRadius = (bombLineWidth / 2).toFloat();
        eyeRadius = eyeMaxRadius
        eyeMinRadius = eyeMaxRadius / 6;
        faceMaxTBOffset = (bodyRadius / 3).toFloat();
        maxBlastCircleRadius = (viewWith * (2)).toFloat()
        mouthMaxWidthOffset = (bodyRadius / 5 - bodyRadius / 5 / 10).toFloat();
        mouthMaxHeightOffset = (bodyRadius / 5 / 2).toFloat();
        mouthFSMaxWidthOffset = mouthMaxWidthOffset / 3;
        mouthFSMaxHeightOffset = mouthMaxHeightOffset / 2;
        var padding: Float = (2 * bombLineWidth * Math.PI / 2 / 72).toFloat()
        bombCenterX = viewWith / 2
        bombCenterY = viewHeight - bombLineWidth - bodyRadius

        var list = listOf<Float>((bombLineWidth / 4).toFloat(),
                (bombLineWidth / 2 + bombLineWidth).toFloat(),
                (bombLineWidth * 2).toFloat(),
                (bombLineWidth / 3 * 2 + bombLineWidth).toFloat(),
                viewWith.toFloat(),
                0f)
        groundDashPathEffect = DashPathEffect(list.toFloatArray(), 0f)

        list = listOf<Float>(getRadianLength(56, bodyRadius)
                , getRadianLength(4, bodyRadius) + bombLineWidth
                , getRadianLength(2.5f, bodyRadius)
                , getRadianLength(4, bodyRadius) + bombLineWidth
                , getRadianLength(220, bodyRadius)
                , getRadianLength(12, bodyRadius) + bombLineWidth
                , getRadianLength(90, bodyRadius)
                , 0f)
        bodyDashPathEffect = DashPathEffect(list.toFloatArray(), 0f)

        list = listOf<Float>(0f, getRadianLength(95, highLightRadius)
                , getRadianLength(0.5f, highLightRadius)
                , getRadianLength(5, highLightRadius) + bombLineWidth
                , getRadianLength(12, highLightRadius)
                , getRadianLength(5, highLightRadius) + bombLineWidth
                , getRadianLength(24, highLightRadius)
                , getRadianLength(270, highLightRadius)
        )
        highLightPathEffect = DashPathEffect(list.toFloatArray(), 0f)
        list = listOf<Float>(padding * 2, padding)
        mHeadEffect = DashPathEffect(list.toFloatArray(), 0f);
        setHeadLinePath();
        setHeadPath();
        setBodyLightPath();
    }

    private fun getRadianLength(angle: Float, radius: Int) = (angle.toDouble() * Math.PI * radius.toDouble() / 180f).toFloat()
    private fun getRadianLength(angle: Int, radius: Int) = (angle.toDouble() * Math.PI * radius.toDouble() / 180f).toFloat()

    private fun setBodyLightPath() {
        mBodyLightPath.reset()
        var point: Point = getPointInCircle(bombCenterX, bombCenterY, (bodyRadius - bombLineWidth).toFloat(), 160F);
        mBodyLightPath.moveTo(point.x.toFloat(), point.y.toFloat())
        mBodyLightPath.lineTo(point.x.toFloat(), point.y.toFloat())
        val pointControl = getPointInCircle(bombCenterX, bombCenterY, bodyRadius - bombLineWidth + bombLineWidth * 2.2f, 210f)
        point = getPointInCircle(bombCenterX, bombCenterY, (bodyRadius - bombLineWidth).toFloat(), 260f)
        mBodyLightPath.quadTo(pointControl.x.toFloat(), pointControl.y.toFloat(), point.x.toFloat(), point.y.toFloat());
        mBodyLightPath.lineTo((point.x - bodyRadius).toFloat(), point.y.toFloat());
        mBodyLightPath.close()
    }

    private fun getPointInCircle(circleX: Int, circleY: Int, radius: Float, angle: Float): Point {
        val point = Point()
        point.set((circleX + radius * Math.cos(Math.toRadians(angle.toDouble()))).toInt(),
                (circleY + radius * Math.sin(Math.toRadians(angle.toDouble()))).toInt()
        )
        return point
    }

    private fun setHeadPath() {
        mHeadPath.reset();
        mHeadPath.moveTo((bombCenterX - bodyRadius / 5).toFloat(), (viewHeight / 2).toFloat());
        mHeadPath.lineTo((bombCenterX + bodyRadius / 5).toFloat(), (viewHeight / 2).toFloat());
        mHeadPath.lineTo((bombCenterX + bodyRadius / 5).toFloat(), (bombCenterY - bodyRadius - bodyRadius / 4).toFloat());
        mHeadPath.lineTo(bombCenterX.toFloat(), (bombCenterY - bodyRadius - bodyRadius / 4 - bodyRadius / 4 / 4).toFloat());
        mHeadPath.lineTo((bombCenterX - bodyRadius / 5).toFloat(), (bombCenterY - bodyRadius - bodyRadius / 4).toFloat());
        mHeadPath.close();
    }

    private fun setHeadLinePath() {
        val beginY = (bombCenterY - bodyRadius - bodyRadius / 4 - bodyRadius / 4 / 4).toFloat()
        mHeadLinePath.reset()
        mHeadLinePath.moveTo(bombCenterX.toFloat(), beginY)
        val controlY = beginY - bodyRadius / 2.2f
        val bottomPointY = (bombCenterY - bodyRadius).toFloat()//转折点高度
        mHeadLinePath.quadTo((bombCenterX + bodyRadius / 2 / 5).toFloat(),
                controlY, (bombCenterX + bodyRadius / 2).toFloat(), controlY)
        mHeadLinePath.cubicTo((bombCenterX + bodyRadius / 2 + bodyRadius / 2 / 5 * 4).toFloat(),
                controlY, bombCenterX + bodyRadius / 2 * 2f - bodyRadius / 16,
                bottomPointY - bodyRadius / 6,
                bombCenterX + bodyRadius / 2 * 2f, bottomPointY)
        mHeadLinePath.quadTo(bombCenterX.toFloat() + bodyRadius / 2 * 2f + (bodyRadius / 16).toFloat(),
                bottomPointY + bodyRadius / 6,
                bombCenterX.toFloat() + bodyRadius / 2 * 2f + (bodyRadius / 7).toFloat(),
                bottomPointY + bodyRadius / 4)
    }


    val blastAnimTime = 700L

    /**
     * 圆环扩散的爆炸效果动画
     */
    fun getBlastAnim(): Animator {
        val valueAnimator = ofFloat(0f, maxBlastCircleRadius)
                .setDuration(blastAnimTime) as ValueAnimator
        valueAnimator.addUpdateListener { animation ->
            if (animation.animatedFraction > 0.7f) {
                initData()
            }
            currentBlastCircleRadius = animation.animatedValue as Float
            blastCircleRadiusPercent = animation.animatedFraction
            view.invalidate()
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                initData()
                view.invalidate()
            }
        })
        return valueAnimator
    }

    val faceLeftRightAnimTime = 1400L

    /**
     * 头部从左转向右边，同时眨一次眼，身体边框从逆时针方向旋转一定角度复原
     */
    fun getFaceLRAnim(): ValueAnimator {
        val valueAnimator = ofFloat(-1f, 0f, 1f, 0f)
                .setDuration(faceLeftRightAnimTime) as ValueAnimator
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            run {
                val animatedValue = animation.getAnimatedValue() as Float
                faceLROffset = animatedValue * faceMaxLROffset
                bombLRRotate = -bombMaxLRRotate * animatedValue
                eyeRadius = if (Math.abs(animatedValue) < 0.3 && animation.getAnimatedFraction() < 0.6) {
                    Math.max(eyeMaxRadius * Math.abs(animatedValue), eyeMinRadius);
                } else {
                    eyeMaxRadius;
                }
                view.invalidate()
            }
        }
        return valueAnimator
    }

    val faceTBAnimTime: Long = 300L
    val faceTBAnimDelayTime: Long = 200L
    val faceChangeAnimTime: Long = 450L
    val headLineAnimTime: Long =
//            20000L
            faceLeftRightAnimTime + faceTBAnimTime + faceTBAnimDelayTime + faceChangeAnimTime + 500L

    /**
     * 引线点燃动画
     */
    fun getHeadLineAnim(): Animator {
        //引线长度变化的动画
        val valueAnimator = ofFloat(1f, 0f)
                .setDuration(headLineAnimTime)
        valueAnimator.addUpdateListener { animation ->
            headLinePercent = animation.animatedValue as Float
            view.invalidate()
        }
        //引线末端小红点闪烁的动画
        val valueAnimatorLight = ValueAnimator.ofFloat(0f, 1.3f, 0f)
                .setDuration(headLineAnimTime / 5)
        //小红点缩放执行5次
        valueAnimatorLight.repeatCount = 5
        valueAnimatorLight.addUpdateListener { animation -> headLineLightRate = animation.animatedValue as Float }
        val animatorSet = AnimatorSet()
        animatorSet.play(valueAnimator).with(valueAnimatorLight)
        return animatorSet
    }

//    val faceTBAnimTime: Long = 300L
//
//    val faceTBAnimDelayTime: Long = 200L

    /**
     * 头部转向上方的动画
     */
    fun getFaceTBAnim(): Animator {
        val valueAnimator = ofFloat(0f, 1f)
                .setDuration(faceTBAnimTime) as ValueAnimator
        valueAnimator.startDelay = faceTBAnimDelayTime
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            faceTBOffset = -faceMaxTBOffset * value
            bombTBRotate = bombMaxTBRotate * value
            view.invalidate()
        }
        return valueAnimator
    }

//    val faceChangeAnimTime: Long = 450L

    fun getFaceChangeAnim(): Animator {
        val animatorSet = AnimatorSet()
        //眼睛眨一次，然后睁大
        val valueAnimator = ValueAnimator.ofFloat(1f, 0f, 1.4f)
                .setDuration(faceChangeAnimTime)
        valueAnimator.addUpdateListener { animation ->
            eyeRadius = eyeMaxRadius * animation.animatedValue as Float
            if (eyeRadius < eyeMinRadius) {
                eyeRadius = eyeMinRadius
            }
        }
        //嘴部动画
        val mouthAnimator = ValueAnimator.ofFloat(0f, 1f, 0f, 1f)
                .setDuration(faceChangeAnimTime)
        mouthAnimator.interpolator = DecelerateInterpolator()
        mouthAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            if (animation.animatedFraction > 0.75) {
                //动画进入第二阶段
                mouthWidthOffset = mouthMaxWidthOffset * value
                mouthHeightOffset = mouthMaxHeightOffset * value
                mouthOffsetPercent = animation.animatedFraction
            } else {
                mouthFSWidthOffset = mouthFSMaxWidthOffset * value
                mouthFSHeightOffset = mouthFSMaxHeightOffset * value
            }
            view.invalidate()
        }
        animatorSet.play(valueAnimator).with(mouthAnimator)
        return animatorSet
    }

    fun initData() {
        faceTBOffset = 0F;
        bombTBRotate = 0F;
        bombLRRotate = 15F;
        faceLROffset = -faceMaxLROffset;
        currentBlastCircleRadius = 0F;
        blastCircleRadiusPercent = 0F;
        eyeRadius = eyeMaxRadius;
        headLinePercent = 1F;
        mouthWidthOffset = 0F;
        mouthHeightOffset = 0F
        mouthOffsetPercent = 0F;
        mouthFSWidthOffset = 0F;
        mouthFSHeightOffset = 0F;
        headLineLightRate = 0F;
    }


}

