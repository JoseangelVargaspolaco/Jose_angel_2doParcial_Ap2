package com.ucne.parcial2.ui.tickets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.parcial2.data.local.entity.TicketEntity
import com.ucne.parcial2.data.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TicketsUiState(
    val ticketsList: List<TicketEntity> = emptyList()
)

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    var descripcion by mutableStateOf("")
    var sueldo by mutableStateOf("")
    var isValid: Boolean by mutableStateOf(false)

    var uiState = MutableStateFlow(TicketsUiState())
        private set

    init {
        getLista()
    }

    fun getLista() {
        viewModelScope.launch(Dispatchers.IO) {
            refrescarTicket()
        }
    }

//    private  suspend fun getTicketsFromApi(){
//        val ticket = getTicketsFromApi().
//        uiState.update {
//            it.copy(ticketsList = ticket)
//        }
//    }

    private suspend fun refrescarTicket() {
        ticketRepository.getList().collect { lista ->
            uiState.update {
                it.copy(ticketsList = lista)
            }
        }
    }

    fun insertar() {
        try {
//            val ticket = TicketEntity(
////                descripcion = descripcion,
////                sueldo = sueldo.toDoubleOrNull() ?: 0.0
//            )

//            viewModelScope.launch(Dispatchers.IO) {
//                ticketRepository.insert(ticket)
//                Limpiar()
//            }
        } catch (ex: IllegalArgumentException) {
            isValid = true
        }
    }

    private fun Limpiar() {

    }
}