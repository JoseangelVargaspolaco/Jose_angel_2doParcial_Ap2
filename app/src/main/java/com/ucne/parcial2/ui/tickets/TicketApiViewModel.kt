package com.ucne.parcial2.ui.tickets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.parcial2.data.local.dao.remote.dto.TicketDto
import com.ucne.parcial2.data.repository.TicketsApiRepositoryImp
import com.ucne.parcial2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TicketsListState(
    val isLoading: Boolean = false,
    val tickets: List<TicketDto> = emptyList(),
    val error: String = ""
)
data class TicketsState(
    val isLoading: Boolean = false,
    val ticket: TicketDto? = null,
    val error: String = ""
)
@HiltViewModel
class TicketApiViewModel @Inject constructor(
    private val ticketRepository: TicketsApiRepositoryImp,

) : ViewModel() {
    var asunto by mutableStateOf("")
    var asuntoError by mutableStateOf("")

    var empresa by mutableStateOf("")
    var empresaError by mutableStateOf("")

    var encargadoId by mutableStateOf(1)
    var orden by mutableStateOf(1)
    var ticketId by mutableStateOf(1)
    var fecha by mutableStateOf("2023-04-01T19:23:59.770Z")

    var especificaciones by mutableStateOf("")
    var especificacionesError by mutableStateOf("")

    var estatus by mutableStateOf("")
    var estatusError by mutableStateOf("")

    var Estatus = listOf("Solicitado", "En espera", "En proceso", "Finalizado")

    var uiState = MutableStateFlow(TicketsListState())
        private set
    var uiStateTicket = MutableStateFlow(TicketsState())
        private set

    init {
        ticketRepository.getTickets().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    uiState.update {
                        it.copy(tickets = result.data ?: emptyList())
                    }
                }
                is Resource.Error -> {
                    uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun TicketbyId(id: Int) {
        ticketId = id
        Limpiar()
        ticketRepository.getTicketsId(ticketId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiStateTicket.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    uiStateTicket.update {
                        it.copy(ticket = result.data)
                    }
                    empresa = uiStateTicket.value.ticket!!.empresa
                    asunto = uiStateTicket.value.ticket!!.asunto
                    estatus = uiStateTicket.value.ticket!!.estatus
                    fecha = uiStateTicket.value.ticket!!.fecha
                    especificaciones = uiStateTicket.value.ticket!!.especificaciones
                }
                is Resource.Error -> {
                    uiStateTicket.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun putTicket(id: Int) {
        viewModelScope.launch {
            ticketId = id!!
            try {
                if (ticketId != null) {
                    ticketRepository.putTickets(
                        ticketId, TicketDto(
                            asunto,
                            empresa,
                            uiStateTicket.value.ticket!!.encargadoId,
                            especificaciones,
                            estatus, fecha = uiStateTicket.value.ticket!!.fecha,
                            uiStateTicket.value.ticket!!.orden,
                            ticketId = ticketId
                        )
                    )
                } else {
                    throw NullPointerException("Value is null")
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }

    fun onAsuntoChanged(asunto: String) {
        this.asunto = asunto
        HayErrores()
    }

    fun onEmpresaChanged(empresa: String) {
        this.empresa = empresa
        HayErrores()
    }

    fun onEspecificacionesChanged(especificaciones: String) {
        this.especificaciones = especificaciones
        HayErrores()
    }

    fun onEstatusChanged(estatus: String) {
        this.estatus = estatus
        HayErrores()
    }

    fun HayErrores(): Boolean {
        var hayError = false
        asuntoError = ""
        if (asunto.isBlank()) {
            asuntoError = "Ingrese un asunto"
            hayError = true
        }

        empresaError = ""
        if (empresa.isBlank()) {
            empresaError = "Ingrese el nombre de la empresa"
            hayError = true
        }

        estatusError = ""
        if(estatus.isNullOrBlank()){
            estatusError = "Seleccione un estatus"
            hayError = true
        }

        especificacionesError = ""
        if (especificaciones.isBlank()) {
            especificacionesError = "Ingrese las especificaciones"
            hayError = true
        }
        return hayError
    }

    fun deleteTicket(id: Int) {
        viewModelScope.launch {
            ticketId = id!!
            try {
                if (ticketId != null) {
                    ticketRepository.deleteTickets(ticketId)
                } else {
                    throw NullPointerException("Value is null")
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }

    fun postTickets() {
        viewModelScope.launch {
            if (ticketId == null) {
                ticketId += 1
            }
            if (encargadoId == null) {
                encargadoId += 1
            }
            if (orden == null) {
                orden += 1
            }
            ticketRepository.postTickets(
                TicketDto(
                    asunto = asunto,
                    empresa = empresa,
                    encargadoId = encargadoId,
                    especificaciones = especificaciones,
                    estatus = estatus,
                    fecha = "2023-04-01T19:23:59.770Z",
                    orden = orden,
                    ticketId = ticketId
                )
            )
        }
    }


    private fun Limpiar() {
        asunto = ""
        empresa = ""
        especificaciones = ""
        estatus = ""
    }
}
