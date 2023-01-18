package com.superking.parchisi

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.lang.Math.abs

open class OnSwipeListener(prodAct: Prodigy) : View.OnTouchListener {

    private var gestureDetector: GestureDetector

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    init {
        gestureDetector = GestureDetector(prodAct, OnSwipeListener())
    }

    private inner class OnSwipeListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent,
                             velocityX: Float, velocityY: Float): Boolean {
            var result = false
            val yDiff = e2.y - e1.y
            val xDiff = e2.x - e1.x

            if (abs(xDiff) > abs(yDiff)) {
                if (abs(xDiff) > Const.grthtsdf && abs(velocityX) > Const.fehfoief) {
                    if (xDiff > 0) {
                        OnSwipeRight()
                    } else {
                        OnSwipeLeft()
                    }
                    result = true
                }
            } else if (abs(yDiff) > Const.grthtsdf && abs(velocityY) > Const.fehfoief) {
                if (yDiff > 0) {
                    OnSwipeBottom()
                } else {
                    OnSwipeTop()
                }
                result = true
            }
            return result
        }


    }

    open fun OnSwipeLeft() {

    }
    open fun OnSwipeRight() {

    }
    open fun OnSwipeTop() {

    }
    open fun OnSwipeBottom() {

    }
}