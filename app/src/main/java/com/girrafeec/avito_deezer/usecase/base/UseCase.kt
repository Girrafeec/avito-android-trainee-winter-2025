package com.girrafeec.avito_deezer.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds

abstract class UseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    private val className = if (Timber.treeCount != 0) this.javaClass.simpleName else TAG

    @Suppress("TooGenericExceptionCaught")
    public suspend operator fun invoke(params: P): Result<R> {
        return try {
            val result: Result<R>
            val executionDuration = measureTimeMillis {
                withContext(dispatcher) {
                    execute(params).let {
                        result = Result.success(it)
                    }
                }
            }.milliseconds
            Timber.tag(className).v("Execution of $className took $executionDuration")
            result
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            Timber.tag(className).e(e, "Exception occurred while executing $className with parameters $params")
            Result.failure(e)
        }
    }

    protected abstract suspend fun execute(params: P): R

    companion object {
        private const val TAG = "UseCase"
    }
}
