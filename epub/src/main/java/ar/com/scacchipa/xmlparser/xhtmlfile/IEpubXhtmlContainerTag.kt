package ar.com.scacchipa.xmlparser.xhtmlfile

interface IEpubXhtmlContainerTag : IEpubXhtmlTag {
    val content: MutableList<IEpubXhtmlTag>
}