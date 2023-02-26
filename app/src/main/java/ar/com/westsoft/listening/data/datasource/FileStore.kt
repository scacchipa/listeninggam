package ar.com.westsoft.listening.data.datasource

import android.app.Application
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipInputStream
import javax.inject.Inject

class FileStore @Inject constructor(
    private val context: Application
) {
    fun unzip(inputStream: InputStream) {
        ZipInputStream(inputStream).use { zipInputStream ->
            var zipEntry = zipInputStream.nextEntry
            while (zipEntry != null) {
                val filePath: String = context.filesDir.path + "/" + zipEntry.name
                println("*** path new file: $filePath")
                if (zipEntry.isDirectory) {
                    val unzipFile = File(filePath)
                    if (!unzipFile.isDirectory) {
                        unzipFile.mkdirs()
                    }
                } else {
                    saveFile(zipEntry.name, zipInputStream)
                    zipInputStream.closeEntry()
                }
                zipEntry = zipInputStream.nextEntry
            }
        }
    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun saveFile(name: String, inputStream: InputStream): SaveFileResult {
        try {
            val filePath: String = context.filesDir.path + "/" + name
            val fileOutput = FileOutputStream(filePath, false)

            val byteArray = ByteArray(8192)
            var c: Int
            do {
                c = inputStream.read(byteArray)

                if (c != -1) {
                    fileOutput.write(byteArray, 0, c)
                }
            } while (c != -1)

            fileOutput.flush()
            fileOutput.close()
            return SaveFileResult.Success
        } catch (exception: FileNotFoundException) {
            return SaveFileResult.FileNotFound
        } catch (exception: IOException) {
            return SaveFileResult.IOError
        }
    }

    fun createGameFolder(gameName: String): Result {
        return try {
            Files.createDirectory(Paths.get(context.filesDir.path + "/" + gameName))
            Result.Success
        } catch (exception: IOException) {
            Result.Failure
        }
    }
}

sealed class SaveFileResult {
    object Success: SaveFileResult()
    object FileNotFound: SaveFileResult()
    object IOError: SaveFileResult()
}

sealed class Result {
    object Success : Result()
    object Failure : Result()
}