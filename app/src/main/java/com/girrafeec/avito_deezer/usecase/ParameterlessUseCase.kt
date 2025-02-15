package com.girrafeec.avito_deezer.usecase

import kotlinx.coroutines.CoroutineDispatcher

abstract class ParameterlessUseCase<out R>(dispatcher: CoroutineDispatcher) : UseCase<Unit, R>(dispatcher) {
    suspend operator fun invoke(): Result<R> = invoke(Unit)
}
