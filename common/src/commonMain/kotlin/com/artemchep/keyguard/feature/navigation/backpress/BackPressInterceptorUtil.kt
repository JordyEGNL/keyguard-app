package com.artemchep.keyguard.feature.navigation.backpress

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

fun BackPressInterceptorHost.interceptBackPress(
    scope: CoroutineScope,
    interceptorFlow: Flow<(() -> Unit)?>,
): () -> Unit {
    lateinit var job: Job

    var callback: (() -> Unit)? = null
    // A registration to remove the back press interceptor
    // from the navigation entry. When it's not null it means
    // that the interceptor is active.
    var unregister: (() -> Unit)? = null

    fun setCallback(
        cb: (() -> Unit)?,
    ) {
        callback = cb
        if (callback != null) {
            if (unregister != null) {
                // Do nothing
            } else {
                // Register a new back press handler,
                // use the callback holder, so we do
                // not have to re-register in the future.
                unregister = interceptBackPress {
                    val cb = callback
                        ?: return@interceptBackPress
                    cb.invoke()
                }
            }
        } else {
            unregister?.invoke()
            unregister = null
        }
    }

    job = interceptorFlow
        .map { c ->
            val newCallback = c.takeIf { !job.isCancelled }
            setCallback(newCallback)
        }
        .launchIn(scope)
    return {
        job.cancel()
        // Unregister the existing interceptor,
        // if there's any.
        unregister?.invoke()
        unregister = null
    }
}
