package com.artemchep.keyguard.feature.home.settings.component

import kotlinx.coroutines.flow.flowOf
import org.kodein.di.DirectDI

actual fun settingCredentialProviderProvider(
    directDI: DirectDI,
): SettingComponent = flowOf(null)