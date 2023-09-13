//package com.jama.voicetotext
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.RectF
//import android.util.AttributeSet
//import android.view.View
//import androidx.compose.ui.graphics.Paint
//import androidx.core.content.ContextCompat
//import androidx.core.graphics.toColor
//
//class WaveformView(context: Context?, attrs: AttributeSet?): View(context, attrs) {
//    private var paint = Paint()
//    private var amplitudes = ArrayList<Float>()
//    private var spikes = ArrayList<RectF>()
//    private var radius = 6f
//    private var w = 9f
//
//    init {
//        paint.color = Color.rgb(244,81,30)
//    }
//    fun addAmplitude(amp:Float){
//        amplitudes.add(amp)
//        var left = 0f
//        var top = 0f
//        var right = left+w
//        var bottom = amp
//        spikes.add(RectF(left, top, right, bottom))
//        invalidate()
//    }
//    override fun draw(canvas: Canvas) {
//        super.draw(canvas)
//        spikes.forEach {
//            canvas?.drawRoundRect(it, radius, radius, paint)
//        }
//    }
//}
//
//
