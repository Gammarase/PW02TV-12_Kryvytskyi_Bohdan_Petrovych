package com.example.pw02tv12_kryvytskyi_bohdan_petrovych.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel


class EmissionsViewModel : ViewModel() {
    //Задаємо початковий стан з значеннями 7го варіанту
    var coalAmount by mutableStateOf("466020.76")
    var mazutAmount by mutableStateOf("156836.79")
    var gasAmount by mutableStateOf("175979.49")

    var coalEmission by mutableDoubleStateOf(0.0)
    var mazutEmission by mutableDoubleStateOf(0.0)
    var gasEmission by mutableDoubleStateOf(0.0)

    fun calculateEmissions() {
        coalEmission = calculateCoalEmission(coalAmount.toDoubleOrNull() ?: 0.0)
        mazutEmission = calculateMazutEmission(mazutAmount.toDoubleOrNull() ?: 0.0)
        gasEmission = calculateGasEmission(gasAmount.toDoubleOrNull() ?: 0.0)
    }

    private fun calculateCoalEmission(amount: Double): Double {
        val kCoal = 150.0
        val qCoal = 20.47
        return kCoal * amount * qCoal / 1e6
    }

    private fun calculateMazutEmission(amount: Double): Double {
        val kMazut = 0.57
        val qMazut = 40.40 * 0.98
        return kMazut * amount * qMazut / 1e6
    }

    private fun calculateGasEmission(amount: Double): Double {
        return 0.0 // При спалюванні природного газу тверді частинки відсутні
    }
}

@Composable
fun EmissionsCalculator() {
    val viewModel = remember { EmissionsViewModel() }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = viewModel.coalAmount,
            onValueChange = { viewModel.coalAmount = it },
            label = { Text("Кількість вугілля (тонн)") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.mazutAmount,
            onValueChange = { viewModel.mazutAmount = it },
            label = { Text("Кількість мазуту (тонн)") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.gasAmount,
            onValueChange = { viewModel.gasAmount = it },
            label = { Text("Кількість природного газу (1000 м³)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.calculateEmissions() }) {
            Text("Розрахувати викиди")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Викид вугілля: ${viewModel.coalEmission.format(2)} тонн")
        Text("Викид мазуту: ${viewModel.mazutEmission.format(2)} тонн")
        Text("Викид природного газу: ${viewModel.gasEmission.format(2)} тонн")
    }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)