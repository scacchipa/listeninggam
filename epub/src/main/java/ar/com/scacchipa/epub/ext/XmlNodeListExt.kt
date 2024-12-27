package ar.com.scacchipa.epub.ext

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.w3c.dom.Text

fun NodeList.firstText(): Text = this.item(0).firstChild as Text
fun NodeList.allText(): List<Text> = (0 until this.length).map { this.item(it).firstChild as Text }
fun NodeList.allElements(): List<Element> = (0 until this.length).map { this.item(it) as Element }