package ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.factories

import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.AbstractTask
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.SynchronizedTask
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.Task
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.TaskListener

/**
 * Фабрика, создающая задачи, которые запускаются в отдельном потоке:
 * один поток на каждую задачу. Темы создаются с помощью [Thread] class.
 */
class ThreadTasksFactory : TasksFactory {

    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SynchronizedTask(ThreadTask(body))
    }

    private class ThreadTask<T>(
        private val body: TaskBody<T>
    ) : AbstractTask<T>() {

        private var thread: Thread? = null

        override fun doEnqueue(listener: TaskListener<T>) {
            thread = Thread {
                executeBody(body, listener)
            }
            thread?.start()
        }

        override fun doCancel() {
            thread?.interrupt()
        }
    }
}