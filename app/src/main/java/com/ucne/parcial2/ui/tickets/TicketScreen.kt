package com.ucne.parcial2.ui.tickets

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ucne.parcial2.ui.navigation.ScreenModule
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun TicketScreen(
    navController: NavController,
    ticketId: Int,
    viewModel: TicketApiViewModel = hiltViewModel(),
    onSaveClick: () -> Unit
) {
    remember {
        viewModel.setTicket(ticketId)
        0
    }
    Column(Modifier.fillMaxWidth()) {
        TicketBody(viewModel = viewModel, navController = navController) {
            onSaveClick()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketBody(
    navController: NavController,
    viewModel: TicketApiViewModel,
    onSaveClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val anio: Int
    val mes: Int
    val dia: Int

    val mCalendar = Calendar.getInstance()
    anio = mCalendar.get(Calendar.YEAR)
    mes = mCalendar.get(Calendar.MONTH)
    dia = mCalendar.get(Calendar.DAY_OF_MONTH)

    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current, { _: DatePicker, anio: Int, mes: Int, dia: Int ->
            viewModel.fecha = "$dia/${mes + 1}/$anio"
        }, anio, mes, dia
    )
    /*----------------------------------------Code Start------------------------------------------------------*/

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
            modifier = Modifier.fillMaxWidth(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Registro de Tickets", textAlign = TextAlign.Center,
                            fontSize = 27.sp, style = MaterialTheme.typography.headlineLarge
                        )
                    }
                )
            }
        ) {
            Spacer(modifier = Modifier.padding(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .wrapContentSize(Alignment.Center)
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = viewModel.empresa,
                    label = { Text(text = "Empresa") },
                    onValueChange = { viewModel.empresa = it })

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = viewModel.asunto,
                    label = { Text(text = "Asunto") },
                    onValueChange = { viewModel.asunto = it })


                OutlinedTextField(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                    value = viewModel.fecha,
                    onValueChange = { viewModel.fecha = it },
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = null,
                            modifier = Modifier
                                .size(33.dp)
                                .padding(4.dp)
                                .clickable {
                                    mDatePickerDialog.show()
                                })
                    },
                    label = { Text(text = "Fecha") })


                OutlinedTextField(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                        .padding(8.dp),
                    value = viewModel.estatus,
                    label = { Text(text = "Estatus") },
                    onValueChange = { viewModel.estatus = it })

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = viewModel.especificaciones,
                    label = { Text(text = "Especificaciones") },
                    onValueChange = { viewModel.especificaciones = it })

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.BottomCenter)
                ) {
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .size(120.dp, 120.dp)
                            .align(Alignment.CenterHorizontally)
                            .wrapContentSize(Alignment.Center),
                        text = { Text("Guardar") },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Save,
                                contentDescription = "Save"
                            )
                        },
                        onClick = {
                            viewModel.putTicket()
                            onSaveClick()
                        }
                    )
                }

            }
        }
    }
}
