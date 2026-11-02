package com.sananhaji.qrgenerator.ui.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

/**
 * MediaStore needs `WRITE_EXTERNAL_STORAGE` only before Android 10;
 * from Q on an app may add images to the shared collection without it.
 */
class StorageWriteAccess(
    private val context: Context,
    private val permissionLauncher: ActivityResultLauncher<String>,
) {

    /** @return `true` when saving can start now, otherwise asks for the permission. */
    fun ensureGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q || isGranted()) return true
        permissionLauncher.launch(PERMISSION)
        return false
    }

    private fun isGranted(): Boolean =
        ContextCompat.checkSelfPermission(context, PERMISSION) == PackageManager.PERMISSION_GRANTED

    private companion object {

        const val PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
}
