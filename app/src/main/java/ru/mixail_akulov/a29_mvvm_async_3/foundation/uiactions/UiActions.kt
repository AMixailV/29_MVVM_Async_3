package ru.mixail_akulov.a29_mvvm_async_3.foundation.uiactions

/**
 * Общие действия, которые можно выполнять в модели представления
 */
interface UiActions {

    /**
     * Display a simple toast message.
     */
    fun toast(message: String)

    /**
     * Get string resource content by its identifier.
     */
    fun getString(messageRes: Int, vararg args: Any): String

}