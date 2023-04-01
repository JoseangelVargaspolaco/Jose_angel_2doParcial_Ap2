package com.ucne.parcial2.data.local.dao.remote.dto

data class TicketDto(
    val asunto: String,
    val empresa: String,
    var encargadoId: Int,
    val especificaciones: String,
    val estatus: String,
    var fecha: String,
    val orden: Int,
    val ticketId: Int
)