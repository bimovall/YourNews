package bivano.apps.common.extension

import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


@ExperimentalCoroutinesApi
fun AppCompatEditText.debouncingText() = callbackFlow {
    val watcher: TextWatcher = doOnTextChanged { text, start, before, count ->
        offer(text.toString())
    }
    awaitClose { this@debouncingText.removeTextChangedListener(watcher) }
}