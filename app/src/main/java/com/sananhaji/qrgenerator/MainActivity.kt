package com.sananhaji.qrgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sananhaji.qrgenerator.screens.Navigation
import com.sananhaji.qrgenerator.screens.SingleQrGenerator
import com.sananhaji.qrgenerator.ui.theme.QRgeneratorTheme
import io.gleap.Gleap

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Gleap.initialize("7dCm2vCO2gaJsB6P3IqGOx8xhuFlYbQJ", application) // For Kotlin replace this with application

        setContent {
            val navController = rememberNavController()

            QRgeneratorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(activity = this, navController = navController)
                }
            }
        }
    }

}

