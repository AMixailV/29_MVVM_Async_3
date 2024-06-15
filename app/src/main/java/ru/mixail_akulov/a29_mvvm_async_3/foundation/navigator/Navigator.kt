package ru.mixail_akulov.a29_mvvm_async_3.foundation.navigator

import ru.mixail_akulov.a29_mvvm_async_3.foundation.views.BaseScreen

/**Event
 * Navigation for your application
 */
interface Navigator {

    /**
     * Launch a new screen at the top of back stack.
     */
    fun launch(screen: BaseScreen)

    /**
     * Go back to the previous screen and optionally send some results.
     */
    fun goBack(result: Any? = null)

}