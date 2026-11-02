package com.sananhaji.qrgenerator.ui.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/** The user taps Save again after granting, so the permission result itself needs no handling. */
@Composable
fun rememberStorageWriteAccess(): StorageWriteAccess {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}
    return remember(context, launcher) {
        StorageWriteAccess(
            context = context,
            permissionLauncher = launcher,
        )
    }
}
