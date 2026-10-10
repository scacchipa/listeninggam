package ar.com.scacchipa.xmlparser.util

import android.content.Context
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import java.util.zip.ZipInputStream
import javax.inject.Inject

class ZipUtils @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun unzip(@RawRes zipFileId: Int): Map<String,String> {
        return unzip(context.resources.openRawResource(zipFileId))
    }

    fun unzip(assetName: String): Map<String,String> {
        return unzip(context.assets.open(assetName))
    }

    fun unzip(inputStream: InputStream): Map<String,String> {
        val destMap = mutableMapOf<String, String>()
        ZipInputStream(inputStream).use { stream ->
            var entry = stream.nextEntry
            while (entry != null) {
                if (!entry.isDirectory) {
                    val content = String(stream.readBytes())
                    val name = entry.name
                    destMap[name] = content
                    destMap["/$name"] = content
                    if (name.startsWith("/")) {
                        destMap[name.removePrefix("/")] = content
                    }
                }
                entry = stream.nextEntry
            }
        }
        return destMap
    }
}