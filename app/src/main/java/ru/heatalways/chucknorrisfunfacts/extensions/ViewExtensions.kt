package ru.heatalways.chucknorrisfunfacts.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.text.InputFilter
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.utils.LoadPhotoConfig

fun ImageView.loadImage(config: LoadPhotoConfig) =
    Glide
        .with(context)
        .load(config.url)
        .placeholder(config.placeholder ?: -1)
        .error(config.placeholder ?: -1)
        .into(this)

fun EditText.maxLength(max: Int){
    this.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max))
}

fun View.setVisibleOrGone(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.setVisibleOrInvisible(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
}

fun View.showHideSmoothly(toPopup: Boolean) {
    if (toPopup) showSmoothly() else hideSmoothly()
}

fun RecyclerView.postScrollToPosition(position: Int) =
    post { scrollToPosition(position) }

fun RecyclerView.onScrolledToLastItem(callback: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = (layoutManager as LinearLayoutManager).childCount
            val totalItemCount = (layoutManager as LinearLayoutManager).itemCount
            val firstVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0) {
                callback()
            }
        }
    })
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

fun Toolbar.initBackButton(activity: Activity) {
    setNavigationIcon(R.drawable.ic_back_arrow)
    setNavigationContentDescription(R.string.navigation_back)
    setNavigationOnClickListener { activity.onBackPressed() }
}