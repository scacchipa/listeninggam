package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.datasource

import android.content.Context
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.util.zip.ZipInputStream
import javax.inject.Inject

class ZipUtils @Inject constructor(
    @ApplicationContext val context: Context
) {
    @Throws(IOException::class)
    fun unzip(@RawRes zipFileId: Int): Map<String,String> {

        val destMap = mutableMapOf<String, String>()

        ZipInputStream(context.resources.openRawResource(zipFileId))
            .use { stream ->

                var directory = ""
                var entry = stream.nextEntry
                while(entry != null) {
                    if (entry.isDirectory) {
                        directory = entry.name
                    } else {
                        val byteIn = ByteArray(entry.size.toInt())
                        stream.read(byteIn)
                        destMap[directory + "/" + entry.name] = String(byteIn)
                    }
                    entry = stream.nextEntry
                }
            }
        return destMap
    }
}