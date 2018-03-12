package com.demo.my.meterialanimationview

import android.graphics.*

/**
 * Created by lenovo on 2018/3/9.
 */
class BombViewDrawer {
    private lateinit var mCanvas: Canvas

    private lateinit var provider: BombViewDataProvider

    private var mPaint: Paint = Paint()
    var mPath: Path = Path()

    constructor(canvas: Canvas?, bombViewDataProvider: BombViewDataProvider) {
        if (canvas != null) mCanvas = canvas
        provider = bombViewDataProvider
        with(mPaint) {
            isAntiAlias = true
            isDither = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            strokeWidth = provider.bombLineWidth.toFloat()
        }
    }

    //用于控制旋转
    private var mCamera = Camera()
    private var mMatrix = Matrix()


    fun drawHead() {
        mCanvas.save()
        mCamera.save()
        mCamera.rotate(provider.bombTBRotate, 0f, -provider.bombLRRotate)
        mMatrix.reset()
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        mMatrix.preTranslate((-provider.bombCenterX).toFloat(), (-(provider.bombCenterY)).toFloat());
        mMatrix.postTranslate(provider.bombCenterX.toFloat(), (provider.bombCenterY.toFloat()));
        mCanvas.concat(mMatrix);

        mPaint.setStrokeWidth(provider.bombLineWidth * 0.8f);
        //内部
        mPaint.setColor(provider.bombColor);
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawPath(provider.mHeadPath, mPaint);
        //边框
        mPaint.setColor(provider.bombLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mCanvas.drawPath(provider.mHeadPath, mPaint);
        mPaint.setStrokeWidth(provider.bombLineWidth.toFloat());
        mCanvas.restore()
    }

    fun drawGround() {
        mPaint.style = Paint.Style.STROKE
        mPaint.color = provider.bombLineColor
        mPaint.pathEffect = provider.groundDashPathEffect
        mPath.reset()
        mPath.moveTo((provider.bombLineWidth / 2).toFloat(), (provider.viewHeight - provider.bombLineWidth / 2).toFloat())
        mPath.lineTo((provider.viewWith - provider.bombLineWidth / 2).toFloat(),
                (provider.viewHeight - provider.bombLineWidth / 2).toFloat())
        mCanvas.drawPath(mPath, mPaint)
    }

    private val mRectF = RectF()

    fun drawBody() {
        mPaint.style = Paint.Style.FILL
        mPaint.color = provider.bombColor
        mCanvas.drawCircle(provider.bombCenterX.toFloat(), provider.bombCenterY.toFloat(),
                (provider.bodyRadius - provider.bombLineWidth / 2).toFloat(), mPaint)
        //左上角光点
        mPaint.pathEffect = provider.highLightPathEffect
        mPaint.color = provider.lightColor
        mPaint.style = Paint.Style.STROKE
        mPath.reset()
        mPath.addCircle(provider.bombCenterX.toFloat(), provider.bombCenterY.toFloat(),
                provider.highLightRadius.toFloat(), Path.Direction.CCW)
        mCanvas.drawPath(mPath, mPaint)
        //左上角的光边
        mPaint.pathEffect = null
        mRectF.set((provider.bombCenterX - provider.bodyRadius + provider.bombLineWidth / 2).toFloat(),
                (provider.bombCenterY - provider.bodyRadius + provider.bombLineWidth / 2).toFloat(),
                (provider.bombCenterX + provider.bodyRadius - provider.bombLineWidth / 2).toFloat(),
                (provider.viewHeight - provider.bombLineWidth - provider.bombLineWidth / 2).toFloat())
        mCanvas.drawArc(mRectF, 160f, 100f, false, mPaint)
        //拼接光边
        mPath.reset()
        mPath.addCircle(provider.bombCenterX.toFloat(), provider.bombCenterY.toFloat(),
                (provider.bodyRadius - provider.bombLineWidth / 2).toFloat(), Path.Direction.CCW)
        mCanvas.save()
        mCanvas.clipPath(mPath)//裁剪圆内

        mPaint.style = Paint.Style.FILL
        mPaint.color = provider.lightColor
        mCanvas.drawPath(provider.mBodyLightPath, mPaint)
        mCanvas.restore()
    }

    fun drawBodyBorder() {
        mCanvas.save()
        mCanvas.rotate(provider.bombLRRotate, provider.bombCenterX.toFloat(), provider.bombCenterY.toFloat())
        mPaint.pathEffect = provider.bodyDashPathEffect
        mPaint.color = provider.bombLineColor
        mPaint.style = Paint.Style.STROKE
        mPath.reset()
        mPath.addCircle(provider.bombCenterX.toFloat(),
                provider.bombCenterY.toFloat(),
                provider.bodyRadius.toFloat(), Path.Direction.CW)
        mCanvas.drawPath(mPath, mPaint)
        mCanvas.restore()
    }

    fun drawFace() {
        mCanvas.save()
        mCamera.save()
        mCamera.rotate(provider.bombTBRotate, 0f, -provider.bombLRRotate / 3)
        mMatrix.reset()
        mCamera.getMatrix(mMatrix)
        mCamera.restore()
        mMatrix.preTranslate((-provider.bombCenterX).toFloat(), (-provider.bombCenterY).toFloat())
        mMatrix.postTranslate(provider.bombCenterX.toFloat(), provider.bombCenterY.toFloat())
        mMatrix.postTranslate(provider.faceLROffset, provider.faceTBOffset)
        mCanvas.concat(mMatrix)
        //眼睛 椭圆控制
        mPaint.style = Paint.Style.FILL
        mPaint.color = provider.bombLineColor
        val eyeY = (provider.bombCenterY + provider.bodyRadius / 5).toFloat()
        val eyeWidth = Math.max(provider.eyeMaxRadius, provider.eyeRadius)
        mRectF.set(provider.bombCenterX.toFloat() - provider.bodyRadius / 3.5f - eyeWidth,
                eyeY - provider.eyeRadius,
                provider.bombCenterX - provider.bodyRadius / 3.5f + eyeWidth,
                eyeY + provider.eyeRadius)
        mCanvas.drawOval(mRectF, mPaint)
        mRectF.set(provider.bombCenterX + provider.bodyRadius / 3.5f - eyeWidth,
                eyeY - provider.eyeRadius,
                provider.bombCenterX.toFloat() + provider.bodyRadius / 3.5f + eyeWidth,
                eyeY + provider.eyeRadius)
        mCanvas.drawOval(mRectF, mPaint)
        //画嘴巴 路径
        val mouthY = eyeY + provider.bombLineWidth - provider.mouthHeightOffset//嘴巴起始高度
        val mouthMaxY = mouthY + (provider.bodyRadius / 7).toFloat() + provider.mouthHeightOffset - provider.mouthFSHeightOffset//嘴巴最底部
        val mouthHalfDistance = provider.bodyRadius / 5 - provider.mouthWidthOffset * 0.5f + provider.mouthFSWidthOffset//嘴巴顶部的拐角的一半宽度
        var mouthTopHalfDistance = mouthHalfDistance - provider.bodyRadius / 5 / 10 - provider.mouthWidthOffset //嘴巴顶部的一半宽度
        val mouthHorDistanceHalf = (mouthMaxY - mouthY) / (6 - 4 * provider.mouthOffsetPercent)//嘴角控制点的距离嘴角点的竖直距离
        if (mouthTopHalfDistance < provider.bodyRadius / 5 / 10) {//让过渡更加自然
            mouthTopHalfDistance = 0f
        }
        mPath.reset()
        mPath.moveTo(provider.bombCenterX - mouthTopHalfDistance, mouthY)
        mPath.lineTo(provider.bombCenterX + mouthTopHalfDistance, mouthY)

        mPath.quadTo(provider.bombCenterX + mouthHalfDistance, mouthY,
                provider.bombCenterX + mouthHalfDistance, mouthY + mouthHorDistanceHalf)
        mPath.cubicTo(provider.bombCenterX + mouthHalfDistance, mouthY + mouthHorDistanceHalf * 2,
                provider.bombCenterX + (mouthHalfDistance - provider.bodyRadius / 5 / 4) * (1 - provider.mouthOffsetPercent), mouthMaxY,
                provider.bombCenterX.toFloat(), mouthMaxY)

        mPath.cubicTo(provider.bombCenterX - (mouthHalfDistance - provider.bodyRadius / 5 / 4) * (1 - provider.mouthOffsetPercent), mouthMaxY,
                provider.bombCenterX - mouthHalfDistance, mouthY + mouthHorDistanceHalf * 2,
                provider.bombCenterX - mouthHalfDistance, mouthY + mouthHorDistanceHalf)
        mPath.quadTo(provider.bombCenterX - mouthHalfDistance, mouthY, provider.bombCenterX - mouthTopHalfDistance, mouthY)
        mPath.close()
        mCanvas.drawPath(mPath, mPaint)
        //画舌头 圆和嘴巴的缩放相交
        val save = mCanvas.saveLayer(0f, 0f, provider.viewWith.toFloat(), provider.viewHeight.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        mCanvas.drawPath(mPath, mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        mPaint.color = provider.bombMouthColor
        mCanvas.drawCircle(provider.bombCenterX.toFloat(),
                mouthY + (mouthMaxY - mouthY) / 8 + provider.bodyRadius / (5 - 1.4f * provider.mouthOffsetPercent),
                (provider.bodyRadius / 5).toFloat(), mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        mCanvas.scale(0.8f, 0.8f, provider.bombCenterX.toFloat(), (mouthMaxY + mouthY) / 2)
        mCanvas.drawPath(mPath, mPaint)
        mCanvas.restoreToCount(save)
        mPaint.xfermode = null
        //酒窝
        mPaint.color = provider.bombCheekColor
        mCanvas.drawCircle(provider.bombCenterX.toFloat() - provider.bodyRadius / 3.5f - provider.bombLineWidth.toFloat(),
                (mouthMaxY + mouthY) / 2, (provider.bombLineWidth / 3).toFloat(), mPaint)
        mCanvas.drawCircle(provider.bombCenterX.toFloat() + provider.bodyRadius / 3.5f + provider.bombLineWidth.toFloat(),
                (mouthMaxY + mouthY) / 2, (provider.bombLineWidth / 3).toFloat(), mPaint)
        mPaint.pathEffect = null
        mCanvas.restore()
    }

    fun drawFaceShadow() {
        //两个圆相交产生阴影
        val save = mCanvas.saveLayer(0f, 0f, provider.viewWith.toFloat(), provider.viewHeight.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        mPaint.style = Paint.Style.FILL
        mPaint.color = provider.bombShadowColor
        mCanvas.drawCircle(provider.bombCenterX.toFloat(), provider.bombCenterY.toFloat(), (provider.bodyRadius - provider.bombLineWidth / 2).toFloat(), mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        mCanvas.translate((-provider.bodyRadius / 5).toFloat(), (-provider.bodyRadius / 5).toFloat())
        mPaint.color = provider.bombColor
        mCanvas.drawCircle(provider.bombCenterX.toFloat(), provider.bombCenterY.toFloat(), (provider.bodyRadius - provider.bombLineWidth / 2).toFloat(), mPaint)
        mCanvas.restoreToCount(save)
        mPaint.xfermode = null
    }

    fun drawHeadLine() {
        mCanvas.save()
        mCamera.save()
        mCamera.rotate(provider.bombTBRotate, 0f, -provider.bombLRRotate)
        mMatrix.reset()
        mCamera.getMatrix(mMatrix)
        mCamera.restore()
        mMatrix.preTranslate((-provider.bombCenterX).toFloat(), (-provider.bombCenterY).toFloat())
        mMatrix.postTranslate(provider.bombCenterX.toFloat(), provider.bombCenterY.toFloat())
        mCanvas.concat(mMatrix)
        mPaint.color = provider.bombLineColor
        mPaint.style = Paint.Style.STROKE

        // fixme pathMeasure的计算转移到provider中
        provider.mPathMeasure.setPath(provider.mHeadLinePath, false)
        mPath.reset()
        provider.mPathMeasure.getSegment(0f, provider.mPathMeasure.getLength() * provider.headLinePercent, mPath, true)
        mCanvas.drawPath(mPath, mPaint)
        //火光
        provider.mPathMeasure.setPath(mPath, false)
        val length = provider.mPathMeasure.getLength()
        provider.mPathMeasure.getPosTan(length, provider.mPathPosition, null)
        mPaint.color = provider.bombFireColor
        mPaint.style = Paint.Style.FILL
        mCanvas.drawCircle(provider.mPathPosition[0],
                provider.mPathPosition[1],
                Math.max(provider.bombLineWidth / 1.3f * provider.headLineLightRate, provider.bombLineWidth / 2 * 1.2f),
                mPaint)
        mPaint.color = provider.bombMouthColor
        mPath.reset()
        mPath.addCircle(provider.mPathPosition[0],
                provider.mPathPosition[1],
                provider.bombLineWidth / 2.5f * provider.headLineLightRate,
                Path.Direction.CCW)
        mPaint.pathEffect = provider.mHeadEffect
        mCanvas.drawPath(mPath, mPaint)
        mPaint.pathEffect = null
        mPaint.color = Color.WHITE
        mCanvas.drawCircle(provider.mPathPosition[0],
                provider.mPathPosition[1],
                provider.bombLineWidth / 6.5f * provider.headLineLightRate,
                mPaint)
        mCanvas.restore()
    }

    fun drawBlast() {
        if (provider.blastCircleRadiusPercent == 0f) {
            return
        }
        val circleY = (provider.bombCenterY - provider.bodyRadius - provider.bodyRadius / 4 - provider.bodyRadius / 4 / 4).toFloat()
        val save = mCanvas.saveLayer(0f, 0f, provider.viewWith.toFloat(), provider.viewHeight.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        val distance = provider.maxBlastCircleRadius / 12
        //画圆
        mPaint.color = provider.lightColor
        mCanvas.drawCircle(provider.bombCenterX.toFloat(), circleY, provider.currentBlastCircleRadius, mPaint)
        mPaint.color = provider.bombColor
        mCanvas.drawCircle(provider.bombCenterX.toFloat(), circleY, provider.currentBlastCircleRadius - distance, mPaint)
        mPaint.color = provider.bombLineColor
        mCanvas.drawCircle(provider.bombCenterX.toFloat(), circleY, provider.currentBlastCircleRadius - distance * 2, mPaint)
        mPaint.color = provider.lightColor
        mCanvas.drawCircle(provider.bombCenterX.toFloat(), circleY, provider.currentBlastCircleRadius - distance * 3, mPaint)
        //掏空
        if (provider.blastCircleRadiusPercent > 0.65) {
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            mCanvas.drawCircle(provider.bombCenterX.toFloat(), circleY, provider.currentBlastCircleRadius - provider.maxBlastCircleRadius * 0.65f, mPaint)
            mPaint.xfermode = null
        }
        mCanvas.restoreToCount(save)
    }
}