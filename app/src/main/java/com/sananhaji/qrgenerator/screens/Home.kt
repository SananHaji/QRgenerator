package com.sananhaji.qrgenerator.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Home(navHostController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Button(
//            modifier = Modifier.fillMaxWidth().padding(16.dp),
//            onClick = { navHostController.navigate(NavVariables.single) }) { Text(text = "Single QR") }
//        Button(
//            modifier = Modifier.fillMaxWidth().padding(16.dp),
//            onClick = { navHostController.navigate(NavVariables.multi) }) { Text(text = "Multi QR") }
        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = { navHostController.navigate(NavVariables.withLogo) }) { Text(text = "With Logo") }
        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = { navHostController.navigate(NavVariables.withLogoMulti) }) { Text(text = "With Multi Logo") }
    }
}