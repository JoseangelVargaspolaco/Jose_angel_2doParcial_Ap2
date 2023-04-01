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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ucne.parcial2.data.local.dao.remote.dto.TicketDto
import com.ucne.parcial2.ui.navigation.ScreenModule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsListScreen(
    navController: NavController,
    viewModel: TicketApiViewModel = hiltViewModel(),
    onTicketClick: (Int) -> Unit
) {
    Column( modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(20.dp))
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Lista de Tickets", fontSize = 30.sp,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(ScreenModule.NewTickets.route) }
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Save")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            val uiState by viewModel.uiState.collectAsState()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
            {
                TicketListBody(uiState.tickets) {
                    onTicketClick(it)
                }
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

    val viewModel: TicketApiViewModel = hiltViewModel()
    val navController = rememberNavController()

    Spacer(modifier = Modifier.padding(5.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .wrapContentSize(Alignment.Center)
                .border(
                    2.dp, Color(0xA88E24AA),
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            Card(
                shape = RoundedCornerShape(5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable(onClick = { onTicketClick(ticket.ticketId) })
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.CenterEnd)
                    ) {
                        Text(
                            text = ticket.empresa, fontSize = 25.sp,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF000000),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(7f)
                        )
                        Text(
                            String.format(ticket.fecha.substring(0, 10)), fontSize = 17.sp,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color(0xD0000000),
                            modifier = Modifier
                                .weight(1.5f)
                                .border(
                                    2.dp, Color(0x56808080),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.TopStart),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = ticket.asunto, fontSize = 20.sp,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color(0xC3303030),
                            modifier = Modifier.weight(8f)
                        )
                        Text(
                            text = ticket.estatus + " ", fontSize = 20.sp,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color(0xC3303030),
                            modifier = Modifier
                                .weight(7f)
                                .wrapContentSize(Alignment.CenterEnd)
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
                            modifier = Modifier
                                .size(20.dp)
                                .wrapContentSize(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopCenter)
        ) {
            Box() {
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .size(60.dp, 50.dp),
                    containerColor = Color.Red,
                    text = { Text("") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    },
                    onClick = {
                        viewModel.deleteTicket(ticket.ticketId)
                        navController.navigate(ScreenModule.TicketsList.route)
                    }
                )
            }
        }
    }
}