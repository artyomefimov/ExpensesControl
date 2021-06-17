package com.artyomefimov.expensescontrol.presentation.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artyomefimov.expensescontrol.presentation.model.Event

inline fun <T> LiveData<T>.safeObserve(
    lifecycleOwner: LifecycleOwner,
    crossinline onHandleContent: (T) -> Unit
) {
    observe(lifecycleOwner, { it?.let(onHandleContent) })
}

inline fun <T> LiveData<Event<T>>.observeEvent(
    lifecycleOwner: LifecycleOwner,
    crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(lifecycleOwner, { it?.getContent()?.let(onEventUnhandledContent) })
}

fun MutableLiveData<Event<Unit>>.toggle() {
    value = Event(Unit)
}
