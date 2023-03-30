package com.ucne.parcial2.data.repository

import com.ucne.parcial2.data.local.dao.remote.dto.TicketDto
import com.ucne.parcial2.util.Resource
import kotlinx.coroutines.flow.Flow


interface TicketApiRepository
{
    fun getTickets(): Flow<Resource<List<TicketDto>>>
    fun getTicketsId(id: Int): Flow<Resource<TicketDto>>
    suspend fun putTickets(id: Int, ticketDto: TicketDto)
    suspend fun deleteTickets(id: Int)
    suspend fun postTickets(ticketDto: TicketDto)
}