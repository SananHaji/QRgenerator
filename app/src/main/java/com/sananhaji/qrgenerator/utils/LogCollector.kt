package com.sananhaji.qrgenerator.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * The Сlass responsible for dump and sending logs from the Logcat
 */
object LogCollector {

    fun dumpLogFile(context: Activity) {
        val file = File(context.cacheDir, FILE_NAME)

        if (file.exists()) {
            file.delete()
        }
        try {
            val command = String.format("logcat -f " + file.path)
            Runtime.getRuntime().exec(command)
            sendMail(context, file)
        } catch (e: Exception) {
            Log.d("Error send Log", e.localizedMessage)
        }
    }

    private fun sendMail(activity: Activity, file: File) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "vnd.android.cursor.dir/email"
            putExtra(Intent.EXTRA_SUBJECT, "Local Log : " + getTime())
            putExtra(
                Intent.EXTRA_STREAM, FileProvider.getUriForFile(
                    activity.applicationContext,
                    "com.sananhaji.qrgenerator.provider",
                    file
                )
            )
        }
        activity.startActivity(Intent.createChooser(emailIntent, "Send file"))
    }

    private fun getTime(): String? {
        val sdf = SimpleDateFormat("yyyy.MM.dd - HH:mm:ss z", Locale.getDefault())
        return sdf.format(Date())
    }

    private const val FILE_NAME = "appLog.log"
}

