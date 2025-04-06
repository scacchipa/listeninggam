package ar.com.scacchipa.xmlparser.xhtmlfile

import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlBase
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlCol
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlColgroup
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlHead
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlHtml
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlImg
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlLink
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlMeta
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlNoscript
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlScript
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlStyle
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlSvg
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTable
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTbody
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlThead
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTitle
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTr
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTspan
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.cell.EpubXhtmlTd
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.cell.EpubXhtmlTh
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtml
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlA
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlBlockquote
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlBody
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlBr
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlCaption
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlDiv
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH1
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH2
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH3
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH4
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH5
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH6
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlLi
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlNav
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlOl
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlP
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlSection
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlSpan
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlStrong
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlTfoot
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlUl
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlCircle
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlEllipse
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlImage
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlLine
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlPath
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlPolygon
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlPolyline
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlRect
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlText
import org.xml.sax.Attributes
import java.util.Stack

object EpubXhtmlTagsContainer {
    val starterTagElement = mapOf<String, (Attributes, Stack<EpubXhtmlTag>) -> EpubXhtmlTag>(
        "html" to { attributes, _ -> EpubXhtmlHtml(attributes) },
        "head" to { attributes, _ -> EpubXhtmlHead(attributes) },
        "title" to { attributes, _ -> EpubXhtmlTitle(attributes) },
        "meta" to { attributes, _ -> EpubXhtmlMeta(attributes) },
        "link" to { attributes, _ -> EpubXhtmlLink(attributes) },
        "body" to { attributes, _ -> EpubXhtmlBody(attributes) },
        "section" to { attributes, _ -> EpubXhtmlSection(attributes) },
        "h1" to { attributes, _ -> EpubXhtmlH1(attributes) },
        "h2" to { attributes, _ -> EpubXhtmlH2(attributes) },
        "h3" to { attributes, _ -> EpubXhtmlH3(attributes) },
        "h4" to { attributes, _ -> EpubXhtmlH4(attributes) },
        "h5" to { attributes, _ -> EpubXhtmlH5(attributes) },
        "h6" to { attributes, _ -> EpubXhtmlH6(attributes) },
        "p" to { attributes, _ -> EpubXhtmlP(attributes) },
        "span" to { attributes, _ -> EpubXhtmlSpan(attributes) },
        "div" to { attributes, _ -> EpubXhtmlDiv(attributes) },
        "a" to { attributes, _ -> EpubXhtmlA(attributes) },
        "strong" to { attributes, _ -> EpubXhtmlStrong(attributes) },
        "i" to { attributes, _ -> EpubXhtml(attributes) },
        "br" to { attributes, _ -> EpubXhtmlBr(attributes) },
        "blockquote" to { attributes, _ -> EpubXhtmlBlockquote(attributes) },
        "img" to { attributes, _ -> EpubXhtmlImg(attributes) },
        "ul" to { attributes, _ -> EpubXhtmlUl(attributes) },
        "ol" to { attributes, _ -> EpubXhtmlOl(attributes) },
        "li" to { attributes, _ -> EpubXhtmlLi(attributes) },
        "table" to { attributes, _ -> EpubXhtmlTable(attributes) },
        "caption" to { attributes, _ -> EpubXhtmlCaption(attributes) },
        "colgroup" to { attributes, _ -> EpubXhtmlColgroup(attributes) },
        "col" to { attributes, tagStack ->
            while (
                tagStack.peek() !is EpubXhtmlColgroup &&
                tagStack.peek() !is EpubXhtmlTable
            ) {
                genericExtractUp(tagStack)
            }
            if (tagStack.peek() is EpubXhtmlTable) {
                tagStack.push(EpubXhtmlColgroup())
            }
            EpubXhtmlCol(attributes)
        },
        "thead" to { attributes, _ -> EpubXhtmlThead(attributes) },
        "tbody" to { attributes, _ -> EpubXhtmlTbody(attributes) },
        "tfoot" to { attributes, _ -> EpubXhtmlTfoot(attributes) },
        "tr" to { attributes, tagStack ->
            while (
                tagStack.peek() !is EpubXhtmlRowContainerTag &&
                tagStack.peek() !is EpubXhtmlTable
            ) {
                genericExtractUp(tagStack)
            }

            if (tagStack.peek() is EpubXhtmlTable) {
                tagStack.push(EpubXhtmlTbody())
            }

            EpubXhtmlTr(attributes)
        },
        "th" to { attributes, _ -> EpubXhtmlTh(attributes) },
        "td" to { attributes, tagStack ->
            while (
                tagStack.peek() !is EpubXhtmlCellContainerTag &&
                tagStack.peek() !is EpubXhtmlRowContainerTag &&
                tagStack.peek() !is EpubXhtmlTable
            ) {
                genericExtractUp(tagStack)
            }

            if (tagStack.peek() is EpubXhtmlTable) {
                tagStack.push(EpubXhtmlTbody())
            }
            if (tagStack.peek() is EpubXhtmlRowContainerTag) {
                tagStack.push(EpubXhtmlTr())
            }

            EpubXhtmlTd(attributes)
        },
        "nav" to { attributes, _ -> EpubXhtmlNav(attributes) },
        "svg" to { attributes, _ -> EpubXhtmlSvg(attributes) },
        "rect" to { attributes, _ -> EpubXhtmlRect(attributes) },
        "circle" to { attributes, _ -> EpubXhtmlCircle(attributes) },
        "ellipse" to { attributes, _ -> EpubXhtmlEllipse(attributes) },
        "line" to { attributes, _ -> EpubXhtmlLine(attributes) },
        "polygon" to { attributes, _ -> EpubXhtmlPolygon(attributes) },
        "polyline" to { attributes, _ -> EpubXhtmlPolyline(attributes) },
        "path" to { attributes, _ -> EpubXhtmlPath(attributes) },
        "text" to { attributes, _ -> EpubXhtmlText(attributes) },
        "tspan" to { attributes, _ -> EpubXhtmlTspan(attributes) },
        "image" to { attributes, _ -> EpubXhtmlImage(attributes) },
        "style" to { attributes, _ -> EpubXhtmlStyle(attributes) },
        "base" to { attributes, _ -> EpubXhtmlBase(attributes) },
        "script" to { attributes, _ -> EpubXhtmlScript(attributes) },
        "noscript" to { attributes, _ -> EpubXhtmlNoscript(attributes) },
    )

    val enderTagElement = mapOf<String, (Stack<EpubXhtmlTag>) -> Unit>(
        "html" to { _ -> },

        "head" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlHead
            val locationTag = tagStack.peek() as EpubXhtmlHtml
            locationTag.head = lastTag
        },

        "title" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTitle
            val locationTag = tagStack.peek() as EpubXhtmlHead
            locationTag.title = lastTag
        },

        "meta" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlMeta
            val locationTag = tagStack.peek() as EpubXhtmlHead
            locationTag.metas.add(lastTag)
        },

        "link" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlLink
            val locationTag = tagStack.peek() as EpubXhtmlHead
            locationTag.links.add(lastTag)
        },

        "body" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlBody
            val locationTag = tagStack.peek() as EpubXhtmlHtml
            locationTag.body = lastTag
        },

        "section" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlSection, EpubXhtmlContainerTag>(tagStack)
        },
        "h1" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlH1, EpubXhtmlContainerTag>(tagStack)
        },
        "h2" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlH2, EpubXhtmlContainerTag>(tagStack)
        },
        "h3" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlH3, EpubXhtmlContainerTag>(tagStack)
        },
        "h4" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlH4, EpubXhtmlContainerTag>(tagStack)
        },
        "h5" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlH5, EpubXhtmlContainerTag>(tagStack)
        },
        "h6" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlH6, EpubXhtmlContainerTag>(tagStack)
        },
        "p" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlP, EpubXhtmlContainerTag>(tagStack)
        },
        "span" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlSpan, EpubXhtmlContainerTag>(tagStack)
        },
        "div" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlDiv, EpubXhtmlContainerTag>(tagStack)
        },
        "a" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlA, EpubXhtmlContainerTag>(tagStack)
        },
        "strong" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlStrong, EpubXhtmlContainerTag>(tagStack)
        },
        "i" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtml, EpubXhtmlContainerTag>(tagStack)
        },
        "br" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlBr, EpubXhtmlContainerTag>(tagStack)
        },
        "blockquote" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlBlockquote, EpubXhtmlContainerTag>(tagStack)
        },
        "img" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlImg, EpubXhtmlContainerTag>(tagStack)
        },
        "ul" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlUl, EpubXhtmlContainerTag>(tagStack)
        },
        "ol" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlOl, EpubXhtmlContainerTag>(tagStack)
        },
        "li" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlLi, EpubXhtmlContainerTag>(tagStack)
        },
        "table" to { tagStack: Stack<EpubXhtmlTag> ->
            while (tagStack.peek() !is EpubXhtmlTable) {
                genericExtractUp(tagStack)
            }
            extractUp<EpubXhtmlTable, EpubXhtmlContainerTag>(tagStack)
        },
        "caption" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlCaption
            val locationTag = tagStack.peek() as EpubXhtmlTable
            locationTag.caption = lastTag
        },

        "colgroup" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlColgroup
            val locationTag = tagStack.peek() as EpubXhtmlTable
            locationTag.colgroup = lastTag
        },

        "col" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlCol
            val locationTag = tagStack.peek() as EpubXhtmlColgroup
            locationTag.cols.add(lastTag)
        },

        "thead" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlThead
            val locationTag = tagStack.peek() as EpubXhtmlTable
            locationTag.thead = lastTag
        },

        "tbody" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTbody
            val locationTag = tagStack.peek() as EpubXhtmlTable
            locationTag.tbody = lastTag
        },

        "tfoot" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTfoot
            val locationTag = tagStack.peek() as EpubXhtmlTable
            locationTag.tfoot = lastTag
        },
        "tr" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTr
            val locationTag = tagStack.peek() as EpubXhtmlRowContainerTag

            locationTag.rows.add(lastTag)
        },
        "th" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTh
            val locationTag = tagStack.peek() as EpubXhtmlCellContainerTag

            locationTag.cells.add(lastTag)
        },
        "td" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTd
            val locationTag = tagStack.peek() as EpubXhtmlCellContainerTag

            locationTag.cells.add(lastTag)
        },

        "nav" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlNav, EpubXhtmlContainerTag>(tagStack)
        },
        "svg" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUp<EpubXhtmlSvg, EpubXhtmlContainerTag>(tagStack)
        },
        "rect" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUpShape<EpubXhtmlRect>(tagStack)
        },
        "circle" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUpShape<EpubXhtmlCircle>(tagStack)
        },
        "ellipse" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUpShape<EpubXhtmlEllipse>(tagStack)
        },
        "line" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUpShape<EpubXhtmlLine>(tagStack)
        },
        "polygon" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUpShape<EpubXhtmlPolygon>(tagStack)
        },
        "polyline" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUpShape<EpubXhtmlPolyline>(tagStack)
        },
        "path" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUpShape<EpubXhtmlPath>(tagStack)
        },
        "text" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUpShape<EpubXhtmlText>(tagStack)
        },
        "tspan" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTspan
            val locationTag = tagStack.peek() as EpubXhtmlText
            locationTag.text.add(lastTag)
        },
        "image" to { tagStack: Stack<EpubXhtmlTag> ->
            extractUpShape<EpubXhtmlImage>(tagStack)
        },
        "style" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlStyle
            val locationTag = tagStack.peek() as EpubXhtmlHead
            locationTag.style.add(lastTag)
        },
        "base" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlBase
            val locationTag = tagStack.peek() as EpubXhtmlHead
            locationTag.base.add(lastTag)
        },
        "script" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlScript
            val locationTag = tagStack.peek() as EpubXhtmlHead
            locationTag.script.add(lastTag)
        },
        "noscript" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlNoscript
            val locationTag = tagStack.peek() as EpubXhtmlHead
            locationTag.noscript.add(lastTag)
        },
    )

    private inline fun <reified LastType : EpubXhtmlTag, reified LocationType : EpubXhtmlContainerTag> extractUp(
        tagStack: Stack<EpubXhtmlTag>
    ) {
        val lastTag = tagStack.pop() as LastType
        val locationTag = tagStack.peek() as LocationType
        locationTag.contents.add(lastTag)
    }

    private inline fun <reified LastType : EpubXhtmlShape> extractUpShape(tagStack: Stack<EpubXhtmlTag>) {
        val lastTag = tagStack.pop() as LastType
        val locationTag = tagStack.peek() as EpubXhtmlSvg
        locationTag.shapes.add(lastTag)
    }

    private fun genericExtractUp(tagStack: Stack<EpubXhtmlTag>) {
        val lastTag = tagStack.pop()
        val locationTag = tagStack.peek()

        when (lastTag) {
            is EpubXhtmlTd -> (locationTag as EpubXhtmlTr).cells.add(lastTag)
            is EpubXhtmlTh -> (locationTag as EpubXhtmlTr).cells.add(lastTag)
            is EpubXhtmlTr -> (locationTag as EpubXhtmlRowContainerTag).rows.add(lastTag)
            is EpubXhtmlTbody -> (locationTag as EpubXhtmlTable).tbody = lastTag
            is EpubXhtmlThead -> (locationTag as EpubXhtmlTable).thead = lastTag
            is EpubXhtmlTfoot -> (locationTag as EpubXhtmlTable).tfoot = lastTag
            is EpubXhtmlColgroup -> (locationTag as EpubXhtmlTable).colgroup = lastTag
            is EpubXhtmlCaption -> (locationTag as EpubXhtmlTable).caption = lastTag
            is EpubXhtmlTable -> (locationTag as EpubXhtmlContainerTag).contents.add(lastTag)

            is EpubXhtmlCol -> (locationTag as EpubXhtmlColgroup).cols.add(lastTag)
        }
    }
}
