package ar.com.scacchipa.xmlparser

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXHtmlHandler
import org.junit.Assert.assertEquals
import org.junit.Test
import org.xml.sax.InputSource
import javax.xml.parsers.SAXParserFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class XhtmlParserTest {
    @Test
    fun parserTomSawyerHtmlFile() {

        val factory = SAXParserFactory.newInstance()

        val xhtmlSaxParser = factory.newSAXParser()
        val xhtmlHandler = EpubXHtmlHandler()

        file9077970642051048906_74_h_0htmxhtmlString.byteInputStream().use { inputStream ->
            xhtmlSaxParser.parse(InputSource(inputStream), xhtmlHandler)
        }

        val actualxhtml = xhtmlHandler.getXhtml()

        assertEquals(expectedFile9077970642051048906_74_h_0htmxhtmlString, actualxhtml.tagWrap())


    }
}

const val file9077970642051048906_74_h_0htmxhtmlString =
"""<?xml version='1.0' encoding='utf-8'?>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta charset="utf-8"/><title>The Adventures of Tom Sawyer | Project Gutenberg
</title>
<link href="4869221894640329421_cover.jpg" rel="icon" type="image/x-cover" id="id-4638908298089879300"/>

<link href="0.css" rel="stylesheet" type="text/css"/>
<link href="1.css" rel="stylesheet" type="text/css"/>
<link href="pgepub.css" rel="stylesheet" type="text/css"/>
<meta name="generator" content="Ebookmaker 0.12.47 by Project Gutenberg"/>
</head>
<body class="x-ebookmaker x-ebookmaker-3"><section class="pg-boilerplate pgheader" id="pg-header" lang="en"><h2 id="pg-header-heading" title="">The Project Gutenberg eBook of <span lang="en" id="pg-title-no-subtitle">The Adventures of Tom Sawyer, Complete</span></h2>

<div>This ebook is for the use of anyone anywhere in the United States and
most other parts of the world at no cost and with almost no restrictions
whatsoever. You may copy it, give it away or re-use it under the terms
of the Project Gutenberg License included with this ebook or online
at <a class="reference external" href="https://www.gutenberg.org">www.gutenberg.org</a>. If you are not located in the United States,
you will have to check the laws of the country where you are located
before using this eBook.</div>

<div class="container" id="pg-machine-header"><p><strong>Title</strong>: The Adventures of Tom Sawyer, Complete</p>
<div id="pg-header-authlist">
<p><strong>Author</strong>: Mark Twain</p>
</div>
<p><strong>Release date</strong>: July 1, 2004 [eBook #74]<br/>
Most recently updated: August 9, 2023</p>

<p><strong>Language</strong>: English</p>

<p><strong>Credits</strong>: David Widger</p>

</div><div id="pg-start-separator">
<span>*** START OF THE PROJECT GUTENBERG EBOOK THE ADVENTURES OF TOM SAWYER, COMPLETE ***</span>
</div></section><div style="margin-top:2em; margin-bottom:4em"/>
<div class="fig" style="width:60%">
<img alt="bookcover.jpg (156K)" src="4869221894640329421_bookcover.jpg" style="width:100%;" id="img_images_bookcover.jpg"/>
</div>
<div class="fig" style="width:30%;">
<img alt="spine.jpg (33K)" src="4869221894640329421_spine.jpg" style="width:100%;" id="img_images_spine.jpg"/>
</div>
<h1 id="pgepubid00000">THE ADVENTURES OF TOM SAWYER</h1>
<div class="ph2">
BY MARK TWAIN
</div>
<div class="ph3">
(Samuel Langhorne Clemens)
</div>
<div class="fig" style="width:60%">
<a id="frontispiece"/>
<img alt="frontispiece.jpg (259K)" src="4869221894640329421_frontispiece.jpg" style="width:100%;" id="img_images_frontispiece.jpg"/>
</div>
<div class="fig" style="width:60%">
<img alt="titlepage.jpg (72K)" src="4869221894640329421_titlepage.jpg" style="width:100%;" id="img_images_titlepage.jpg"/>
</div>
<div class="fig" style="width:60%">
<img alt="dedication.jpg (10K)" src="4869221894640329421_dedication.jpg" style="width:100%;" id="img_images_dedication.jpg"/>
</div>
</body></html>
"""

const val expectedFile9077970642051048906_74_h_0htmxhtmlString =
"""<html xmlns="http://www.w3.org/1999/xhtml" lang="en"><head><title>The Adventures of Tom Sawyer | Project Gutenberg
</title><meta charset="utf-8"/><meta name="generator" content="Ebookmaker 0.12.47 by Project Gutenberg"/><link href="4869221894640329421_cover.jpg" rel="icon" type="image/x-cover" id="id-4638908298089879300"/><link href="0.css" rel="stylesheet" type="text/css"/><link href="1.css" rel="stylesheet" type="text/css"/><link href="pgepub.css" rel="stylesheet" type="text/css"/></head><body class="x-ebookmaker x-ebookmaker-3"><section class="pg-boilerplate pgheader" id="pg-header" lang="en"><h2 id="pg-header-heading" title="">The Project Gutenberg eBook of <span lang="en" id="pg-title-no-subtitle">The Adventures of Tom Sawyer, Complete</span></h2><div>This ebook is for the use of anyone anywhere in the United States and
most other parts of the world at no cost and with almost no restrictions
whatsoever. You may copy it, give it away or re-use it under the terms
of the Project Gutenberg License included with this ebook or online
at <a class="reference external" href="https://www.gutenberg.org">www.gutenberg.org</a>. If you are not located in the United States,
you will have to check the laws of the country where you are located
before using this eBook.</div><div class="container" id="pg-machine-header"><p><strong>Title</strong>: The Adventures of Tom Sawyer, Complete</p><div id="pg-header-authlist"><p><strong>Author</strong>: Mark Twain</p></div><p><strong>Release date</strong>: July 1, 2004 [eBook #74]<br></br>
Most recently updated: August 9, 2023</p><p><strong>Language</strong>: English</p><p><strong>Credits</strong>: David Widger</p></div><div id="pg-start-separator"><span>*** START OF THE PROJECT GUTENBERG EBOOK THE ADVENTURES OF TOM SAWYER, COMPLETE ***</span></div></section><div style="margin-top:2em; margin-bottom:4em"></div><div class="fig" style="width:60%"><img alt="bookcover.jpg (156K)" src="4869221894640329421_bookcover.jpg" style="width:100%;" id="img_images_bookcover.jpg"/></div><div class="fig" style="width:30%;"><img alt="spine.jpg (33K)" src="4869221894640329421_spine.jpg" style="width:100%;" id="img_images_spine.jpg"/></div><h1 id="pgepubid00000">THE ADVENTURES OF TOM SAWYER</h1><div class="ph2">
BY MARK TWAIN
</div><div class="ph3">
(Samuel Langhorne Clemens)
</div><div class="fig" style="width:60%"><a id="frontispiece"></a><img alt="frontispiece.jpg (259K)" src="4869221894640329421_frontispiece.jpg" style="width:100%;" id="img_images_frontispiece.jpg"/></div><div class="fig" style="width:60%"><img alt="titlepage.jpg (72K)" src="4869221894640329421_titlepage.jpg" style="width:100%;" id="img_images_titlepage.jpg"/></div><div class="fig" style="width:60%"><img alt="dedication.jpg (10K)" src="4869221894640329421_dedication.jpg" style="width:100%;" id="img_images_dedication.jpg"/></div></body></html>"""