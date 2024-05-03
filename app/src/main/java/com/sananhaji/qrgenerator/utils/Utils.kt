package com.sananhaji.qrgenerator.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object Utils {

    val placeHolder = "HHMS321342"
    val label = "Sertifikat ID"
    val baseUrl = "https://www.socialup.az/"

    fun saveImage(bitmap: Bitmap, name: String) {
        try {
            val destination: String =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath +
                        "/QRCodeGenerator/"
//        val filePath = Environment.getExternalStorageDirectory().absolutePath +
//                "/QRCodeGenerator/"
            val dir = File(destination)
            if (!dir.exists()) dir.mkdirs()
            var file = File(dir, "$name.png")
            if (file.exists()) {
                val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                file = File(dir, "${name}_$timestamp.png")
            }
            val fOut = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            Log.e("TAGTAGTAG", "saveImage: ", e)
        }
    }

    fun saveBitmapAsImage(context: Context, bitmap: Bitmap, filename: String): File? {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val directory = File(root, "YourDirectoryName") // Change "YourDirectoryName" to your desired directory name

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "$filename.png") // Change ".png" to the desired image format

        return try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()

            // If you want to make the image visible in the gallery immediately
            context.sendBroadcast(android.content.Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))

            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun saveBitmapAsPng(context: Context, bitmap: Bitmap, filename: String) {
        // Assume block needs to be inside a Try/Catch block.
        // Assume block needs to be inside a Try/Catch block.
        val path = Environment.getExternalStorageDirectory().toString()
        val fOut: OutputStream?
        val file = File(
            path,
            "$filename.jpg"
        ) // the File to save , append increasing numeric counter to prevent files from getting overwritten.

        fOut = FileOutputStream(file)

        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            fOut
        ) // saving the Bitmap to a file compressed as a JPEG with 85% compression rate

        fOut.flush() // Not really required

        fOut.close() // do not forget to close the stream


        MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, file.name, file.name)
    }
}