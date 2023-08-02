package com.sananhaji.qrgenerator.utils

import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

object Utils {

    val placeHolder = "HHMS321342"
    val label = "Sertifikat ID"
    val baseUrl = "https://www.hhm.az/sertifikat-yoxla?certId="

    fun saveImage(bitmap: Bitmap, name: String) {
        val destination: String =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath +
                    "/QRCodeGenerator/"
//        val filePath = Environment.getExternalStorageDirectory().absolutePath +
//                "/QRCodeGenerator/"
        val dir = File(destination)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "$name.png")
        val fOut = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
        fOut.flush()
        fOut.close()
    }
}