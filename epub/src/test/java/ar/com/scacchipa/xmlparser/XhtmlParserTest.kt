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
        var actualXhtml = xhtmlHandler.getXhtml()
        assertEquals(expectedFile9077970642051048906_74_h_0htmxhtmlString, actualXhtml.tagWrap())


        headerExample.byteInputStream().use { inputStream ->
            xhtmlSaxParser.parse(InputSource(inputStream), xhtmlHandler)
        }
        actualXhtml = xhtmlHandler.getXhtml()
        assertEquals(expectedHeader, actualXhtml.tagWrap())


        tableExample2.byteInputStream().use { inputStream ->
            xhtmlSaxParser.parse(InputSource(inputStream), xhtmlHandler)
        }
        actualXhtml = xhtmlHandler.getXhtml()
        assertEquals(expectedTable2, actualXhtml.tagWrap())

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



const val headerExample =
"""<html>
<body>
<h1 style="text-align:center">This is heading 1</h1>
<h2 style="text-align:left">This is heading 2</h2>
<h3 style="text-align:right">This is heading 3</h3>
<h4 style="text-align:justify">This is heading 4</h4>
<h5 style="text-align:right">This is heading 5</h5>
<h6 style="text-align:left">This is heading 6</h6>

</body></html>"""

const val expectedHeader =
"""<html><body><h1 style="text-align:center">This is heading 1</h1><h2 style="text-align:left">This is heading 2</h2><h3 style="text-align:right">This is heading 3</h3><h4 style="text-align:justify">This is heading 4</h4><h5 style="text-align:right">This is heading 5</h5><h6 style="text-align:left">This is heading 6</h6></body></html>"""

const val tableExample2 =
"""<html>
<head>
<style>
table, th, td {
  border: 1px solid black;
}
</style>
</head>
<body>

<h1>The thead, tbody, and tfoot elements</h1>

<table>
  <thead>
    <tr>
      <th>Month</th>
      <th>Savings</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>January</td>
      <td>$100</td>
    </tr>
    <tr>
      <td>February</td>
      <td>$80</td>
    </tr>
  </tbody>
  <tfoot>
    <tr>
      <td>Sum</td>
      <td>$180</td>
    </tr>
  </tfoot>
</table>

</body>
</html>"""

const val expectedTable2 = """<html><head><style>
table, th, td {
  border: 1px solid black;
}</style></head><body><h1>The thead, tbody, and tfoot elements</h1><table><thead><tr><th>Month</th><th>Savings</th></tr></thead><tbody><tr><td>January</td><td>$100</td></tr><tr><td>February</td><td>$80</td></tr></tbody><tfoot><tr><td>Sum</td><td>$180</td></tr></tfoot></table></body></html>"""