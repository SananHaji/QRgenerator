package com.sananhaji.qrgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.sananhaji.qrgenerator.ui.navigation.AppNavigation
import com.sananhaji.qrgenerator.ui.theme.QRgeneratorTheme
import com.sananhaji.qrgenerator.utils.LogCollector
import io.gleap.Gleap

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Android 15+ draws every target-35 app edge to edge; doing it on all versions keeps the look the same.
        enableEdgeToEdge()
        Gleap.initialize(GLEAP_API_KEY, application)

        setContent {
            QRgeneratorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AppNavigation(onSendLogs = { LogCollector.dumpLogFile(this) })
                }
            }
        }
    }

    private companion object {

        const val GLEAP_API_KEY = "7dCm2vCO2gaJsB6P3IqGOx8xhuFlYbQJ"
    }
}
