package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fullName = intent.getStringExtra(MainActivity.FULL_NAME_KEY).orEmpty()

        setContent {
            MyApplicationTheme {
                CalculatorScreen(fullName)
            }
        }
    }
}

@Composable
private fun CalculatorScreen(fullName: String) {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.calculator_title),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = stringResource(R.string.calculator_welcome_text, fullName),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = firstNumber,
                onValueChange = { firstNumber = it },
                label = { Text(stringResource(R.string.calculator_input1_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = secondNumber,
                onValueChange = { secondNumber = it },
                label = { Text(stringResource(R.string.calculator_input2_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (result.isNotEmpty()) {
                        stringResource(R.string.calculator_result_text, result)
                    } else {
                        stringResource(R.string.calculator_result_empty)
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(
                    onClick = {
                        firstNumber = ""
                        secondNumber = ""
                        result = ""
                    }
                ) {
                    Text(stringResource(R.string.calculator_clear_button))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        result = calculate(firstNumber, secondNumber) { first, second ->
                            first + second
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.calculator_add_button))
                }
                Button(
                    onClick = {
                        result = calculate(firstNumber, secondNumber) { first, second ->
                            first * second
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.calculator_multiply_button))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        result = calculate(firstNumber, secondNumber) { first, second ->
                            if (second != 0.0) first / second else Double.NaN
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.calculator_divide_button))
                }
                Button(
                    onClick = {
                        result = calculate(firstNumber, secondNumber) { first, second ->
                            first - second
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.calculator_subtract_button))
                }
            }
        }
    }
}

private fun calculate(
    firstNumber: String,
    secondNumber: String,
    operation: (Double, Double) -> Double
): String {
    val first = firstNumber.toDoubleOrNull() ?: return ""
    val second = secondNumber.toDoubleOrNull() ?: return ""
    val res = operation(first, second)
    
    if (res.isNaN()) return "Error"

    return if (res % 1.0 == 0.0) {
        res.toInt().toString()
    } else {
        res.toString()
    }
}