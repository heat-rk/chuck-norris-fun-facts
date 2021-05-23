package ru.heatalways.chucknorrisfunfacts.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import androidx.core.view.isVisible

fun View.setVisibleOrGone(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.setVisibleOrInvisible(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
}

fun View.showHideSmoothly(toPopup: Boolean) {
    if (toPopup) showSmoothly() else hideSmoothly()
}

fun View.showSmoothly() {
    if (!isVisible) {
        visibility = View.VISIBLE
        scaleX = 0f
        scaleY = 0f
        alpha = 0f

        animate()
            .setDuration(100)
            .scaleX(1f)
            .scaleY(1f)
            .alpha(1f)
            .setListener(object  : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    this@showSmoothly.visibility = View.VISIBLE
                }
            })
            .start()
    }
}

fun View.hideSmoothly() {
    if (isVisible) {
        scaleX = 1f
        scaleY = 1f
        alpha = 1f

        animate()
            .setDuration(100)
            .scaleX(0f)
            .scaleY(0f)
            .alpha(0f)
            .setListener(object  : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    this@hideSmoothly.visibility = View.GONE
                    scaleX = 1f
                    scaleY = 1f
                    alpha = 1f
                }
            })
            .start()
    }
}

fun View.expandCollapse(toExpand: Boolean) {
    if (toExpand) this.expand() else this.collapse()
}

fun View.expand() {
    val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight: Int = measuredHeight
    val initialHeight = 0

    this.layoutParams.height = initialHeight
    this.visibility = View.VISIBLE

    animateView(this, initialHeight, targetHeight)
}

fun View.collapse() {
    if (isVisible) {
        val initialHeight = this.measuredHeight
        val targetHeight = 0

        animateView(this, initialHeight, targetHeight)
    }
}

private fun animateView(v: View, initialHeight: Int, targetHeight: Int) {
    val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
    valueAnimator.addUpdateListener { animation ->
        v.layoutParams.height = animation.animatedValue as Int
        v.requestLayout()
    }
    valueAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator) {
            v.layoutParams.height = targetHeight
            if (targetHeight == 0) v.visibility = View.GONE
            else v.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
    valueAnimator.duration = 300
    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.start()
}