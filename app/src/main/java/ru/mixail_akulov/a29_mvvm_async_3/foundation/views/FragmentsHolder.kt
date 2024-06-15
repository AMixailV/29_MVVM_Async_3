package ru.mixail_akulov.a29_mvvm_async_3.foundation.views

import ru.mixail_akulov.a29_mvvm_async_3.foundation.ActivityScopeViewModel

/**
 * Реализуйте этот интерфейс в activity.
 */
interface FragmentsHolder {

    /**
     * Вызывается, когда представления действий должны быть перерисованы.
     */
    fun notifyScreenUpdates()

    /**
     * Получите текущие реализации зависимостей из области активности виртуальной машины.
     */
    fun getActivityScopeViewModel(): ActivityScopeViewModel

}