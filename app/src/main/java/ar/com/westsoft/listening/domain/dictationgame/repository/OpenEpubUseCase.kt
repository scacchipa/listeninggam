package ar.com.westsoft.listening.domain.dictationgame.repository

import androidx.annotation.RawRes
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.datasource.ZipUtils
import javax.inject.Inject

class OpenEpubUseCase @Inject constructor(
    private val zipUtils: ZipUtils
) {

    operator fun invoke(@RawRes id: Int) {
        zipUtils.unzip(id).forEach{
            println(it.key)
        }
    }
}
