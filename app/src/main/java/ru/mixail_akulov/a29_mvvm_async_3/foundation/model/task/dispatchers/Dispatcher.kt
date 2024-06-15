package ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.dispatchers

/**
 * Диспетчеры каким-то образом запускают указанный блок кода.
 */
interface Dispatcher {

    fun dispatch(block: () -> Unit)

}