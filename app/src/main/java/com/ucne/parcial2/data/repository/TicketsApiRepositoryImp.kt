package com.ucne.parcial2.data.repository

import com.ucne.parcial2.data.remote.TicketsApi
import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TicketsApiRepositoryImp @Inject constructor(
    private val ticketsApi: TicketsApi
): TicketApiRepository {

    override fun getTickets(): Flow<Resource<List<TicketDto>>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val ticket = ticketsApi.getTickets() //descarga los tickets de internet, se supone quedemora algo

            emit(Resource.Success(ticket)) //indicar que se cargo correctamente
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    override  fun getTicketsId(id: Int): Flow<Resource<TicketDto>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val tickets =
                ticketsApi.getTicketsId(id) //descagar la lista de tickets por el id

            emit(Resource.Success(tickets)) //indicar que se cargo correctamente
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    override suspend fun putTickets(id: Int, ticketDto: TicketDto) {
        ticketsApi.putTickets(id, ticketDto)
    }
    override suspend fun deleteTickets(id: Int) = ticketsApi.deleteTickets(id)
    override suspend fun postTickets(ticketDto: TicketDto) {
       ticketsApi.postTickets(ticketDto)
    }
}