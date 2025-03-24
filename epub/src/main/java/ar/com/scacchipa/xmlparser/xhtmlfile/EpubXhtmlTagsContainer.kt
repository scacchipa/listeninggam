package ar.com.scacchipa.xmlparser.xhtmlfile

import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlCol
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlColgroup
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlHead
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlHtml
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlImg
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlLink
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlMeta
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlSvg
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTbody
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlThead
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTitle
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTr
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTspan
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.cell.EpubXhtmlTable
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
    val starterTagElement = mapOf<String, (Attributes) -> EpubXhtmlTag>(
        "html" to { attributes -> EpubXhtmlHtml(attributes) },
        "head" to { attributes -> EpubXhtmlHead(attributes) },
        "title" to { attributes -> EpubXhtmlTitle(attributes) },
        "meta" to { attributes -> EpubXhtmlMeta(attributes) },
        "link" to { attributes -> EpubXhtmlLink(attributes) },
        "body" to { attributes -> EpubXhtmlBody(attributes) },
        "section" to { attributes -> EpubXhtmlSection(attributes) },
        "h1" to { attributes -> EpubXhtmlH1(attributes) },
        "h2" to { attributes -> EpubXhtmlH2(attributes) },
        "h3" to { attributes -> EpubXhtmlH3(attributes) },
        "h4" to { attributes -> EpubXhtmlH4(attributes) },
        "h5" to { attributes -> EpubXhtmlH5(attributes) },
        "h6" to { attributes -> EpubXhtmlH6(attributes) },
        "p" to { attributes -> EpubXhtmlP(attributes) },
        "span" to { attributes -> EpubXhtmlSpan(attributes) },
        "div" to { attributes -> EpubXhtmlDiv(attributes) },
        "a" to { attributes -> EpubXhtmlA(attributes) },
        "strong" to { attributes -> EpubXhtmlStrong(attributes) },
        "i" to { attributes -> EpubXhtml(attributes) },
        "br" to { attributes -> EpubXhtmlBr(attributes) },
        "blockquote" to { attributes: Attributes -> EpubXhtmlBlockquote(attributes) },
        "img" to { attributes -> EpubXhtmlImg(attributes) },
        "ul" to { attributes -> EpubXhtmlUl(attributes) },
        "ol" to { attributes -> EpubXhtmlOl(attributes) },
        "li" to { attributes -> EpubXhtmlLi(attributes) },
        "table" to { attributes -> EpubXhtmlTable(attributes) },
        "caption" to { attributes -> EpubXhtmlCaption(attributes) },
        "colgroup" to { attributes -> EpubXhtmlColgroup(attributes) },
        "col" to { attributes -> EpubXhtmlCol(attributes) },
        "thead" to { attributes -> EpubXhtmlThead(attributes) },
        "tbody" to { attributes -> EpubXhtmlTbody(attributes) },
        "tfoot" to { attributes -> EpubXhtmlTfoot(attributes) },
        "tr" to { attributes -> EpubXhtmlTr(attributes) },
        "th" to { attributes -> EpubXhtmlTh(attributes) },
        "td" to { attributes -> EpubXhtmlTd(attributes) },
        "nav" to { attributes -> EpubXhtmlNav(attributes) },
        "svg" to { attributes -> EpubXhtmlSvg(attributes) },
        "rect" to { attributes -> EpubXhtmlRect(attributes) },
        "circle" to { attributes -> EpubXhtmlCircle(attributes) },
        "ellipse" to { attributes -> EpubXhtmlEllipse(attributes) },
        "line" to { attributes -> EpubXhtmlLine(attributes) },
        "polygon" to { attributes -> EpubXhtmlPolygon(attributes) },
        "polyline" to { attributes -> EpubXhtmlPolyline(attributes) },
        "path" to { attributes -> EpubXhtmlPath(attributes) },
        "text" to { attributes -> EpubXhtmlText(attributes) },
        "tspan" to { attributes -> EpubXhtmlTspan(attributes) },
        "image" to { attributes -> EpubXhtmlImage(attributes)  }
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
            pushUp<EpubXhtmlSection, EpubXhtmlContainerTag>(tagStack)
        },
        "h1" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlH1, EpubXhtmlContainerTag>(tagStack)
        },
        "h2" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlH2, EpubXhtmlContainerTag>(tagStack)
        },
        "h3" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlH3, EpubXhtmlContainerTag>(tagStack)
        },
        "h4" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlH4, EpubXhtmlContainerTag>(tagStack)
        },
        "h5" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlH5, EpubXhtmlContainerTag>(tagStack)
        },
        "h6" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlH6, EpubXhtmlContainerTag>(tagStack)
        },
        "p" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlP, EpubXhtmlContainerTag>(tagStack)
        },
        "span" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlSpan, EpubXhtmlContainerTag>(tagStack)
        },
        "div" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlDiv, EpubXhtmlContainerTag>(tagStack)
        },
        "a" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlA, EpubXhtmlContainerTag>(tagStack)
        },
        "strong" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlStrong, EpubXhtmlContainerTag>(tagStack)
        },
        "i" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtml, EpubXhtmlContainerTag>(tagStack)
        },
        "br" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlBr, EpubXhtmlContainerTag>(tagStack)
        },
        "blockquote" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlBlockquote, EpubXhtmlContainerTag>(tagStack)
        },
        "img" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlImg, EpubXhtmlContainerTag>(tagStack)
        },
        "ul" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlUl, EpubXhtmlContainerTag>(tagStack)
        },
        "ol" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlOl, EpubXhtmlContainerTag>(tagStack)
        },
        "li" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlLi, EpubXhtmlContainerTag>(tagStack)
        },
        "table" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlTable, EpubXhtmlContainerTag>(tagStack)
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
            when (val locationTag = tagStack.peek()) {
                is EpubXhtmlRowContainerTag -> locationTag.rows.add(lastTag)
                is EpubXhtmlTable -> locationTag.tbody = EpubXhtmlTbody().apply {
                    rows.add(lastTag)
                }
            }
        },

        "th" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTh
            when (val locationTag = tagStack.peek()) {
                is EpubXhtmlTr -> locationTag.cells.add(lastTag)
                is EpubXhtmlRowContainerTag -> locationTag.rows.add(EpubXhtmlTr().apply {
                    cells.add(lastTag)
                })

                is EpubXhtmlTable -> locationTag.tbody = EpubXhtmlTbody().apply {
                    rows.add(EpubXhtmlTr().apply {
                        cells.add(lastTag)
                    })
                }
            }
        },

        "td" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTd
            when (val locationTag = tagStack.peek()) {
                is EpubXhtmlTr -> locationTag.cells.add(lastTag)
                is EpubXhtmlRowContainerTag -> locationTag.rows.add(EpubXhtmlTr().apply {
                    cells.add(lastTag)
                })

                is EpubXhtmlTable -> locationTag.tbody = EpubXhtmlTbody().apply {
                    rows.add(EpubXhtmlTr().apply {
                        cells.add(lastTag)
                    })
                }
            }
        },

        "nav" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlNav, EpubXhtmlContainerTag>(tagStack)
        },
        "svg" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUp<EpubXhtmlSvg, EpubXhtmlContainerTag>(tagStack)
        },
        "rect" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUpShape<EpubXhtmlRect>(tagStack)
        },
        "circle" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUpShape<EpubXhtmlCircle>(tagStack)
        },
        "ellipse" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUpShape<EpubXhtmlEllipse>(tagStack)
        },
        "line" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUpShape<EpubXhtmlLine>(tagStack)
        },
        "polygon" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUpShape<EpubXhtmlPolygon>(tagStack)
        },
        "polyline" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUpShape<EpubXhtmlPolyline>(tagStack)
        },
        "path" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUpShape<EpubXhtmlPath>(tagStack)
        },
        "text" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUpShape<EpubXhtmlText>(tagStack)
        },
        "tspan" to { tagStack: Stack<EpubXhtmlTag> ->
            val lastTag = tagStack.pop() as EpubXhtmlTspan
            val locationTag = tagStack.peek() as EpubXhtmlText
            locationTag.text.add(lastTag)
        },
        "image" to { tagStack: Stack<EpubXhtmlTag> ->
            pushUpShape<EpubXhtmlImage>(tagStack)
        },
    )

    private inline fun <reified LastType : EpubXhtmlTag, reified LocationType : EpubXhtmlContainerTag> pushUp(
        tagStack: Stack<EpubXhtmlTag>
    ) {
        val lastTag = tagStack.pop() as LastType
        val locationTag = tagStack.peek() as LocationType
        locationTag.contents.add(lastTag)
    }

    private inline fun <reified LastType : EpubXhtmlShape> pushUpShape(tagStack: Stack<EpubXhtmlTag>) {
        val lastTag = tagStack.pop() as LastType
        val locationTag = tagStack.peek() as EpubXhtmlSvg
        locationTag.shapes.add(lastTag)
    }
}