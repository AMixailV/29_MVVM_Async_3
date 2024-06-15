package ru.mixail_akulov.a29_mvvm_async_3.simplemvvm.views.currentcolor

import ru.mixail_akulov.a29_mvvm_async_3.R
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.PendingResult
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.SuccessResult
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.takeSuccess
import ru.mixail_akulov.a29_mvvm_async_3.foundation.model.task.dispatchers.Dispatcher
import ru.mixail_akulov.a29_mvvm_async_3.foundation.navigator.Navigator
import ru.mixail_akulov.a29_mvvm_async_3.foundation.uiactions.UiActions
import ru.mixail_akulov.a29_mvvm_async_3.foundation.views.BaseViewModel
import ru.mixail_akulov.a29_mvvm_async_3.foundation.views.LiveResult
import ru.mixail_akulov.a29_mvvm_async_3.foundation.views.MutableLiveResult
import ru.mixail_akulov.a29_mvvm_async_3.simplemvvm.model.colors.ColorListener
import ru.mixail_akulov.a29_mvvm_async_3.simplemvvm.model.colors.ColorsRepository
import ru.mixail_akulov.a29_mvvm_async_3.simplemvvm.model.colors.NamedColor
import ru.mixail_akulov.a29_mvvm_async_3.simplemvvm.views.changecolor.ChangeColorFragment

class CurrentColorViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val colorsRepository: ColorsRepository,
    dispatcher: Dispatcher
) : BaseViewModel(dispatcher) {

    private val _currentColor = MutableLiveResult<NamedColor>(PendingResult())
    val currentColor: LiveResult<NamedColor> = _currentColor

    private val colorListener: ColorListener = {
        _currentColor.postValue(SuccessResult(it))
    }

    // --- пример результатов прослушивания через модельный слой

    init {
        colorsRepository.addListener(colorListener)
        load()
    }

    override fun onCleared() {
        super.onCleared()
        colorsRepository.removeListener(colorListener)
    }

    // --- пример прослушивания результатов прямо с экрана

    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is NamedColor) {
            val message = uiActions.getString(R.string.changed_color, result.name)
            uiActions.toast(message)
        }
    }

    // ---

    fun changeColor() {
        val currentColor = currentColor.value.takeSuccess() ?: return
        val screen = ChangeColorFragment.Screen(currentColor.id)
        navigator.launch(screen)
    }

    fun tryAgain() {
        load()
    }

    private fun load() {
        colorsRepository.getCurrentColor().into(_currentColor)
    }
}