package ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.factories

import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.Repository
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.Task

typealias TaskBody<T> = () -> T

/**
 * Фабрика для создания экземпляров асинхронных задач ([Task]) из синхронного кода, определенного [TaskBody]
 */
interface TasksFactory {

    /**
     * Create a new [Task] экземпляр из указанного тела.
     */
    fun <T> async(body: TaskBody<T>): Task<T>
}
