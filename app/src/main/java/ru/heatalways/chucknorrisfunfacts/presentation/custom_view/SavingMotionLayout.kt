package ru.heatalways.chucknorrisfunfacts.presentation.custom_view

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout

class SavingMotionLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    override fun onSaveInstanceState(): Parcelable {
        return State(super.onSaveInstanceState(), startState, endState, targetPosition)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        (state as? State)?.let {
            super.onRestoreInstanceState(it.superParcel)
            setTransition(it.startState, it.endState)
            progress = it.progress
        }
    }

    @kotlinx.parcelize.Parcelize
    private class State(
            val superParcel: Parcelable?,
            val startState: Int,
            val endState: Int,
            val progress: Float
    ) : Parcelable
}