package com.ucne.parcial2.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenModule(val route: String) {
    object Tickets : ScreenModule("tickets")
    object TicketsList : ScreenModule("tickets_list")
}