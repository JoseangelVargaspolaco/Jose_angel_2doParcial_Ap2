package com.ucne.parcial2.ui.tickets

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.twotone.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ucne.parcial2.ui.navigation.ScreenModule
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TicketScreen(
    ticketId: Int,
    navController: NavController,
    viewModel: TicketApiViewModel = hiltViewModel(),
    onSaveClick: () -> Unit
) {
    remember {
        viewModel.TicketbyId(ticketId)
        0
    }
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.CenterEnd)
    ) {

        Spacer(Modifier.height(20.dp))
        Icon(
            imageVector = Icons.TwoTone.ArrowCircleLeft,
            contentDescription = null,
            tint = Color(0xCD8595FF),
            modifier = Modifier
                .align(Alignment.Start)
                .size(50.dp, 50.dp)
                .clickable {
                    scope.launch {
                        navController.navigate(ScreenModule.TicketsList.route)
                    }
                }
        )

        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Registro de Tickets", fontSize = 27.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            value = viewModel.empresa,
            label = { Text(text = "Empresa") },
            onValueChange = viewModel::onEmpresaChanged,
            isError = viewModel.empresaError.isNotBlank(),
            trailingIcon = {
                if (viewModel.empresaError.isNotBlank()) {
                    Icon(imageVector = Icons.TwoTone.Error, contentDescription = "error")
                } else {
                    Icon(imageVector = Icons.TwoTone.Check, contentDescription = "success")
                }
            }
        )
        if (viewModel.empresaError.isNotBlank()) {
            Text(
                text = viewModel.empresaError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
                    Text(
                        text = viewModel.empresaError,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.wrapContentSize(Alignment.Center)
                    )
                }


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .wrapContentSize(Alignment.Center),
            value = viewModel.asunto,
            label = { Text(text = "Asunto") },
            onValueChange = viewModel::onAsuntoChanged,
            isError = viewModel.asuntoError.isNotBlank(),
            trailingIcon = {
                if (viewModel.asuntoError.isNotBlank()) {
                    Icon(imageVector = Icons.TwoTone.Error, contentDescription = "error")
                } else {
                    Icon(imageVector = Icons.TwoTone.Check, contentDescription = "success")
                }
            }
        )
        if (viewModel.asuntoError.isNotBlank()) {
            Text(
                text = viewModel.asuntoError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = viewModel.asuntoError,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }

        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .wrapContentSize(Alignment.Center),
            value = viewModel.fecha,
            onValueChange = viewModel::onFechaChanged,
            enabled = false,
            isError = viewModel.fechaError.isNotBlank(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            mDatePickerDialog.show()
                        })
            },
            label = { Text(text = "Fecha") },
            trailingIcon = {
                if (viewModel.fechaError.isNotBlank()) {
                    Icon(imageVector = Icons.TwoTone.Error, contentDescription = "error")
                } else {
                    Icon(imageVector = Icons.TwoTone.Check, contentDescription = "success")
                }
            }
        )

        if (viewModel.fechaError.isNotBlank()) {
            Text(
                text = viewModel.fechaError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = viewModel.fechaError,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(4.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            readOnly = true, enabled = false,
            value = viewModel.estatus,
            isError = viewModel.estatusError.isNotBlank(),
            label = { Text(text = "Estatus") },
            onValueChange = viewModel::onEstatusChanged,
            trailingIcon = {
                Icon(
                    imageVector = Icons.TwoTone.ArrowDropDown,
                    contentDescription = null
                )
                if (viewModel.estatusError.isNotBlank()) {
                    Icon(
                        imageVector = Icons.TwoTone.Error,
                        contentDescription = "error"
                    )
                }
            }
        )

        if (viewModel.estatusError.isNotBlank()) {
            Text(
                text = viewModel.estatusError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = viewModel.estatusError,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .padding(5.dp)
                    .size(123.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                viewModel.Estatus.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item, textAlign = TextAlign.Center)
                        },
                        onClick = {
                            expanded = false
                            viewModel.estatus = item
                        }
                    )
                }
            }
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .wrapContentSize(Alignment.Center),
            value = viewModel.especificaciones,
            label = { Text(text = "Especificaciones") },
            onValueChange = viewModel::onEspecificacionesChanged,
            isError = viewModel.especificacionesError.isNotBlank(),
            trailingIcon = {
                if (viewModel.especificacionesError.isNotBlank()) {
                    Icon(imageVector = Icons.TwoTone.Error, contentDescription = "error")
                } else {
                    Icon(imageVector = Icons.TwoTone.Check, contentDescription = "success")
                }
            }
        )
        if (viewModel.especificacionesError.isNotBlank()) {
            Text(
                text = viewModel.especificacionesError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = viewModel.especificacionesError,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .wrapContentSize(Alignment.Center),
                text = { Text("Guardar", color = Color.White) },
                containerColor = Color.Blue,
                contentColor = Color.Blue,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = "Save",
                        tint = Color.White
                    )
                },
                onClick = {
                    viewModel.putTicket(ticketId)
                    onSaveClick()
                }
            )
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .wrapContentSize(Alignment.Center),
                text = { Text("Eliminar", color = Color.White) },
                containerColor = Color.Red,
                contentColor = Color.Red,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete",
                        tint = Color.White
                    )
                },
                onClick = {
                    viewModel.deleteTicket(ticketId)
                    onSaveClick()
                }
            )
        }
    }
}

