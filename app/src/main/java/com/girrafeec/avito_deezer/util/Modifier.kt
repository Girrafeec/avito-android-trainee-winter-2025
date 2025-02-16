package com.girrafeec.avito_deezer.util

import androidx.compose.ui.Modifier

fun Modifier.optional(
    predicate: Boolean,
    action: Modifier.() -> Modifier,
): Modifier {
    return if (predicate) this.action() else this
}
