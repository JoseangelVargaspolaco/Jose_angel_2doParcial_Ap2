package com.ucne.parcial2.ui.tickets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.ui.navigation.ScreenModule
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicktetListScreen(onNewTicket: () -> Unit, viewModel: TicketApiViewModel = hiltViewModel(), navController: NavController) {
    val scope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.height(50.dp))
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp, 30.dp)
                .padding(4.dp)
                .wrapContentSize(Alignment.TopStart)
                .clickable {
                    scope.launch {
                        navController.navigate(ScreenModule.Tickets.route)
                    }
                }
        )
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onNewTicket() }
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
            ) {
                TicketListBody(uiState.tickets)
            }
        }
    }
}

@Composable
fun TicketListBody(ticketList: List<TicketDto>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn {
            items(ticketList) {ticket ->
                TicketRow(ticket)
            }
        }
    }
}

@Composable
fun TicketRow(ticket: TicketDto) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        //todo : Implementar swipe to delete
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
        ){
            Text(
                text = ticket.fecha.format("dd/mm/yyyy"),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(3f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopStart)
        ) {
            Text(
                text = ticket.empresa,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(3f)
            )
            Text(
                text = ticket.especificaciones,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(3f)
            )
        }
        Divider(Modifier.fillMaxWidth())
    }
}