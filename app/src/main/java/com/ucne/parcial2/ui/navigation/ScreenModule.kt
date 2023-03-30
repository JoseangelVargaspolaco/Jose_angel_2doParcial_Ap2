package com.ucne.parcial2.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenModule(val route: String, val title: String, val icon: ImageVector) {
    object Tickets : ScreenModule("tickets", "Registro de Tickets", Icons.TwoTone.LocalActivity)
    object TicketsList : ScreenModule("tickets_list","Lista de Tickets", Icons.TwoTone.ConfirmationNumber)
}