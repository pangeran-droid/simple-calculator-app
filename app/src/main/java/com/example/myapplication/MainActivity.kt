package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen()
            }
        }
    }

    companion object {
        const val FULL_NAME_KEY = "FULL_NAME_KEY"
    }
}

@Composable
private fun MainScreen() {
    var fullName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.header_text),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(text = stringResource(R.string.full_name))
            Text(text = stringResource(R.string.full_nim))
            Text(text = stringResource(R.string.full_major))
            Text(
                text = stringResource(R.string.note_text),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 50.dp)
            )
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = {
                    Text(
                        text = stringResource(R.string.input_name_label),
                        fontSize = 16.sp
                    )
                },
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (fullName.isNotEmpty()) {
                        val welcomeIntent = Intent(context, CalculatorActivity::class.java)
                            .putExtra(MainActivity.FULL_NAME_KEY, fullName)
                        context.startActivity(welcomeIntent)
                    } else {
                        Toast.makeText(
                            context,
                            R.string.input_name_required,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.submit_button_text),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}
