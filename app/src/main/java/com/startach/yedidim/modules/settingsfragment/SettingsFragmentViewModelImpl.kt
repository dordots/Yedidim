package com.startach.yedidim.modules.settingsfragment

import com.startach.yedidim.entities.authentication.AuthEntity

class SettingsFragmentViewModelImpl(private val authEntity: AuthEntity) : SettingsFragmentViewModel {
    override fun logout() {
        authEntity.logout()
    }
}