package ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task

/**
 * Общие методы работы с потоками.
 */
interface ThreadUtils {

    /**
     * Приостановить текущий поток на указанное время.
     */
    fun sleep(millis: Long)

    class Default : ThreadUtils {
        override fun sleep(millis: Long) {
            Thread.sleep(millis)
        }
    }

}