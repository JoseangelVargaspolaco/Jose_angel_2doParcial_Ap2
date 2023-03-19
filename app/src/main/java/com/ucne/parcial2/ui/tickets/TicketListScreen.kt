package com.ucne.parcial2.ui.tickets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.ui.navigation.ScreenModule
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsListScreen(
    navController: NavController,
    viewModel: TicketApiViewModel = hiltViewModel(),
    onTicketClick: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(Modifier.fillMaxWidth()) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp, 100.dp)
                .padding(4.dp)
                .wrapContentSize(Alignment.TopStart)
                .clickable {
                    scope.launch {
                        navController.navigate(ScreenModule.Start.route)
                    }
                }
        )
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            topBar = {
                TopAppBar(
                    title = {
                        Row() {
                            Text(
                                text = "Lista de Tickets",
                                textAlign = TextAlign.Center,
                                fontSize = 40.sp,
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(ScreenModule.TicketsList.route) }
                ) {
                    Icon(imageVector = Icons.TwoTone.Autorenew, contentDescription = "Actualizar")
                }
            }
        ) {
            val uiState by viewModel.uiState.collectAsState()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
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
            items(ticketList) { ticket ->
                TicketRow(ticket) {
                    onTicketClick(it)
                }
            }
        }
    }
}

@Composable
fun TicketRow(ticket: TicketDto, onTicketClick: (Int) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = { onTicketClick(ticket.ticketId) })
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = ticket.empresa.foldIndexed("") { index, acc, c ->
                        if (index % 20 == 0 && index > 0) "$acc\n$c" else "$acc$c"
                    },
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(3f)
                )
                Text(
                    text = ticket.fecha.substring(0, 10), fontSize = 25.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(3f)
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = ticket.asunto.foldIndexed("") { index, acc, c ->
                        if (index % 20 == 0 && index > 0) "$acc\n$c" else "$acc$c"
                    },
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = ticket.estatus.foldIndexed("") { index, acc, c ->
                        if (index % 20 == 0 && index > 0) "$acc\n$c" else "$acc$c"
                    },
                    style = MaterialTheme.typography.titleLarge,
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
                    }, contentDescription = ticket.estatus,
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
                    }
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Divider(
            Modifier
                .fillMaxWidth()
                .size(15.dp)
        )
    }
}