package com.ssu.better.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream
import java.net.URLEncoder

fun Uri.getMultipartBodyFromUri(context: Context): MultipartBody.Part {
    var inputStream: InputStream = context.contentResolver.openInputStream(this)!!
    return MultipartBody.Part.createFormData(
        "image",
        URLEncoder.encode(getFileName(context)),
        RequestBody.create("images/*".toMediaTypeOrNull(), inputStream.readBytes()),
    )
}

@SuppressLint("Recycle", "Range")
fun Uri.getFileName(context: Context): String {
    var result: String? = null
    if (this.scheme == "content") {
        val cursor = context.contentResolver.query(this, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor!!.close()
        }
    }
    if (result == null) {
        result = this.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}
