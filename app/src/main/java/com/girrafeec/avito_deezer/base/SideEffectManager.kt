package com.girrafeec.avito_deezer.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

interface SideEffectManager<SideEffect> {
    val sideEffects: Flow<SideEffect>
    fun emitSideEffect(sideEffect: SideEffect)
}

class SideEffectManagerImpl<SideEffect> : SideEffectManager<SideEffect> {
    private val _sideEffects = Channel<SideEffect>(Channel.UNLIMITED)
    override val sideEffects = _sideEffects.receiveAsFlow()

    override fun emitSideEffect(sideEffect: SideEffect) {
        Timber.tag(TAG).v("SideEffect: $sideEffect")
        _sideEffects.trySend(sideEffect)
    }

    companion object {
        private const val TAG = "SideEffectManager"
    }
}
