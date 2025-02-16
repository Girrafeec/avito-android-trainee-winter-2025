package com.girrafeec.avito_deezer.component

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AvitoDeezerPlayer @Inject constructor(
    @ApplicationContext
    context: Context
) {

}
