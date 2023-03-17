@file:OptIn(ExperimentalMaterial3Api::class)

package com.ucne.parcial2.ui.tickets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Column(modifier = Modifier
        .fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.CenterStart)
            ) {
                Text(
                    text = ticket.empresa.foldIndexed("") { index, acc, c ->
                        if (index % 20 == 0 && index > 0) "$acc\n$c" else "$acc$c"
                    },
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentSize(Alignment.Center)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .size(300.dp, 50.dp)
                        .border(2.dp, Color.Gray, shape = RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    value = ticket.fecha,
                    onValueChange = { ticket.fecha = it},
                    enabled = false
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.padding(10.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.CenterStart)
            ) {
                Text(
                    text = ticket.asunto.foldIndexed("") { index, acc, c ->
                        if (index % 30 == 0 && index > 0) "$acc\n$c" else "$acc$c"
                    },
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .size(33.dp)
                        .padding(4.dp)
                        .clickable { }
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckBox,
                    contentDescription = null,
                    modifier = Modifier
                        .size(33.dp)
                        .padding(4.dp)
                        .clickable { }
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Divider(Modifier.fillMaxWidth().size(15.dp))
    }
}
