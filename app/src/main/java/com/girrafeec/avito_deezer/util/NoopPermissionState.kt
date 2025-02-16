package com.girrafeec.avito_deezer.util

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus

@OptIn(ExperimentalPermissionsApi::class)
class NoopPermissionState : PermissionState {
    override val permission: String = ""

    override val status: PermissionStatus = PermissionStatus.Granted

    override fun launchPermissionRequest() = Unit
}
