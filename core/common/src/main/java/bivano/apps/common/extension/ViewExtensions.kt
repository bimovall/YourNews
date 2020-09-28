package bivano.apps.common.extension

import android.animation.ObjectAnimator
import android.text.TextWatcher
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.roundToInt


@ExperimentalCoroutinesApi
fun AppCompatEditText.debouncingText() = callbackFlow {
    val watcher: TextWatcher = doOnTextChanged { text, start, before, count ->
        offer(text.toString())
    }
    awaitClose { this@debouncingText.removeTextChangedListener(watcher) }
}

fun ProgressBar.animateProgress(start: Int, end: Int) {
    val animator = ObjectAnimator.ofInt(this, "progress", start, end)
    animator.interpolator = AccelerateInterpolator()
    animator.duration = 300
    animator.start()
}

/**
 * @see https://github.com/jeppeman/android-jetpack-playground/blob/master/libraries/common-presentation/src/main/java/com/jeppeman/jetpackplayground/common/presentation/extensions/Views.kt
 */
fun TextView.animateProgress(from: Int, to: Int, duration: Long) {
    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            val total = to - from
            val current = from + total * interpolatedTime
            text = "${current.roundToInt()}%"
        }
    }

    this.clearAnimation()
    this.animation = animation
    animation.duration = duration
    animation.start()
}