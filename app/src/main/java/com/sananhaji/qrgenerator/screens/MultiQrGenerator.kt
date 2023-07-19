package com.sananhaji.qrgenerator.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sananhaji.qrgenerator.utils.Utils.baseUrl
import com.sananhaji.qrgenerator.utils.Utils.placeHolder
import com.sananhaji.qrgenerator.views.BarcodeView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiQrGenerator() {
    val text = remember { mutableStateOf("") }
    val idList = remember { mutableStateListOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text(text = "Multi Certificate IDs") },
            placeholder = { Text(text = "$placeHolder, $placeHolder, $placeHolder") }
        )

        Text(text = "Separate IDs with comma")

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(idList) { id ->
                    if (id.isNotEmpty()) {
                        BarcodeView(url = "$baseUrl$id")
                        Text(text = id)
                        Divider(color = Color.Blue, thickness = 1.dp)
                    }
                }
            }

            ExtendedFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {
                    val ids = text.value.split(",")
                    idList.clear()
                    idList.addAll(ids)
                },
                containerColor = Color.Green,
                shape = RoundedCornerShape(8.dp),
                text = { Text(text = "Create QRs") },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Create,
                        contentDescription = "Create QRs",
                        tint = Color.White,
                    )
                }
            )
        }

    }
}