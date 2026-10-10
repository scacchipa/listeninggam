package ar.com.scacchipa.xmlparser.domain

import androidx.annotation.RawRes
import ar.com.scacchipa.xmlparser.util.ZipUtils
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlHtml
import javax.inject.Inject

class OpenEpubUseCase @Inject constructor(
    private val zipUtils: ZipUtils
) {
    operator fun invoke(assetName: String): List<EpubXhtmlHtml> {
        return Epub(assetName, zipUtils).htmlFiles
    }

    operator fun invoke(@RawRes id: Int): List<EpubXhtmlHtml> {
        return Epub(id, zipUtils).htmlFiles
    }
}


