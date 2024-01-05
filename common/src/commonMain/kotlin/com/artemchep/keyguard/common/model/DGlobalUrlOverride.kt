package com.artemchep.keyguard.common.model

import com.artemchep.keyguard.ui.icons.generateAccentColors
import kotlinx.datetime.Instant

data class DGlobalUrlOverride(
    val id: String? = null,
    val name: String,
    val regex: Regex,
    val command: String,
    val createdDate: Instant,
) : Comparable<DGlobalUrlOverride> {
    val accentColor = run {
        val colors = generateAccentColors(name)
        colors
    }

    override fun compareTo(other: DGlobalUrlOverride): Int {
        return name.compareTo(other.name, ignoreCase = true)
    }
}
