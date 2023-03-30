package com.ucne.parcial2.data.local.dao.remote

import com.ucne.parcial2.data.local.dao.remote.dto.TicketDto
import retrofit2.Response
import retrofit2.http.*

interface TicketsApi{
    @GET("/api/tickets")
    suspend fun getTickets(): List<TicketDto>

    @GET("/api/tickets/{id}")
    suspend fun getTicketsId(@Path("id") id: Int): TicketDto

    @POST("/api/tickets")
    suspend fun postTickets(@Body ticketDto: TicketDto): TicketDto

    @PUT("/api/tickets/{id}")
    suspend fun putTickets(@Path("id") id: Int, @Body ticketDto: TicketDto): Response<Unit>

    @DELETE("/api/tickets/{id}")
    suspend fun deleteTickets(@Path("id") id: Int): TicketDto
}