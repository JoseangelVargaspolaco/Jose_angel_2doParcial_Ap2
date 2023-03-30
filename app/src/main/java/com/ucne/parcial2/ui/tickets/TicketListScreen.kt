package com.ucne.parcial2.ui.tickets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ucne.parcial2.data.local.dao.remote.dto.TicketDto
import kotlinx.coroutines.launch

@Composable
fun TicketsListScreen(
    navController: NavController,
    viewModel: TicketApiViewModel = hiltViewModel(),
    onTicketClick: (Int) -> Unit
) {
    Column(Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.padding(40.dp))

        Text(
            text = "Lista de Tickets", fontSize = 27.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )


        val uiState by viewModel.uiState.collectAsState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {
            TicketListBody(uiState.tickets){
                onTicketClick(it)
            }
        }
    }
}


@Composable
fun TicketListBody(ticketList: List<TicketDto>, onTicketClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn {
            items(ticketList) {ticket ->
                TicketRow(ticket)
                {
                    onTicketClick(it)
                }
            }
        }
    }
}

@Composable
fun TicketRow(ticket: TicketDto, onTicketClick: (Int) -> Unit) {
    Spacer(modifier = Modifier.padding(10.dp))

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onTicketClick(ticket.ticketId) })
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.CenterEnd)
            ) {
                Text(
                    text = ticket.empresa,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(7f)
                )
                Text(
                    String.format(ticket.fecha.substring(0, 10)),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xD0808080),
                    modifier = Modifier.weight(2f).border(0.5.dp, Color(0x56808080),
                    shape = RoundedCornerShape(14.dp)),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = ticket.asunto,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xC3303030),
                    modifier = Modifier.weight(8f)
                )
                Text(
                    text = ticket.estatus,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xC3303030),
                    modifier = Modifier.weight(7f).wrapContentSize(Alignment.CenterEnd)
                )
                Icon(
                    imageVector = when (ticket.estatus) {
                        "Solicitado" -> {
                            Icons.TwoTone.DownloadDone
                        }
                        "En espera" -> {
                            Icons.TwoTone.BackHand
                        }
                        "En Proceso" -> {
                            Icons.TwoTone.ChangeCircle
                        }
                        "Finalizado" -> {
                            Icons.TwoTone.CheckBox
                        }
                        else -> {
                            Icons.TwoTone.AddCircle
                        }
                    },
                    contentDescription = ticket.estatus,
                    tint = when (ticket.estatus) {
                        "Solicitado" -> {
                            Color.Blue
                        }
                        "En espera" -> {
                            Color.Cyan
                        }
                        "En proceso" -> {
                            Color.Cyan
                        }
                        "Finalizado" -> {
                            Color.Green
                        }
                        else -> {
                            Color.Gray
                        }
                    },
                    modifier = Modifier.size(20.dp).wrapContentSize(Alignment.CenterEnd)
                )
            }
        }
        Divider(Modifier.fillMaxWidth())
    }
}