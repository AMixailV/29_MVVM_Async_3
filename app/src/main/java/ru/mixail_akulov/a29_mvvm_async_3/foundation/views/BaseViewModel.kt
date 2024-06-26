package ru.mixail_akulov.a29_mvvm_async_3.foundation.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.PendingResult
import ru.mixail_akulov.a29_mvvm_async_3.foundation.utils.Event
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.Result
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.Task
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.TaskListener
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.dispatchers.Dispatcher

// Альтернативные записи для сокращени кода
typealias LiveEvent<T> = LiveData<Event<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>
typealias LiveResult<T> = LiveData<Result<T>>

typealias MutableLiveResult<T> = MutableLiveData<Result<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<Result<T>>

/**
 * Base class for all view-models.
 */

open class BaseViewModel(
    private val dispatcher: Dispatcher
) : ViewModel() {

    private val tasks = mutableSetOf<Task<*>>()

    override fun onCleared() {
        super.onCleared()
        clearTasks()
    }

    /**
     * Override this method in child classes if you want to listen for results
     * from other screens
     */
    open fun onResult(result: Any) {

    }

    fun onBackPressed() {
        clearTasks()
    }

    /**
     * Launch task asynchronously, listen for its result and
     * automatically unsubscribe the listener in case of view-model destroying.
     */
    fun <T> Task<T>.safeEnqueue(listener: TaskListener<T>? = null) {
        tasks.add(this)
            this.enqueue(dispatcher) {
                tasks.remove(this)
                listener?.invoke(it)
            }
    }

    /**
     * Launch task asynchronously and map its result to the specified
     * [liveResult].
     * Task is cancelled automatically if view-model is going to be destroyed.
     */
    fun <T> Task<T>.into(liveResult: MutableLiveResult<T>) {
        liveResult.value = PendingResult()
        this.safeEnqueue {
            liveResult.value = it
        }
    }

    private fun clearTasks() {
        tasks.forEach { it.cancel() }
        tasks.clear()
    }

}

