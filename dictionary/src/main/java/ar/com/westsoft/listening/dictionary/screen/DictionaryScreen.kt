package ar.com.westsoft.listening.dictionary.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ar.com.westsoft.listening.dictionary.source.WiktionaryItem

data class DictionaryScreenState(
    val word: String,
    val items: List<WiktionaryItem>,
    val error: String?
)

@Composable
fun DictionaryScreen(
    dictionaryStack: List<DictionaryScreenState>,
    onWordClick: (String) -> Unit,
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

    val currentScreen = dictionaryStack.lastOrNull()
    val items = currentScreen?.items ?: emptyList()
    val error = currentScreen?.error
    val currentWord = currentScreen?.word ?: ""

    var selectedWord by remember(currentWord) { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Button(onClick = onBack) {
            Text("Back")
        }
        
        Text(
            text = if (currentWord.isNotBlank()) "Definition: $currentWord" else "Dictionary Definition",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodyLarge,
                color = if (error.startsWith("Searching")) Color.Unspecified else Color.Red,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        if (items.isNotEmpty()) {
            items.forEach { item ->
                Text(
                    text = item.partOfSpeech.replaceFirstChar { it.uppercase() } + ":",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2196F3)
                    ),
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )

                item.definitions.forEachIndexed { index, def ->
                    val fullAnnotatedString = remember(def.definition, selectedWord) {
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("  ${index + 1}. ")
                            }
                            append(parseHtmlAndAnnotateWords(def.definition, selectedWord))
                        }
                    }
                    ClickableText(
                        text = fullAnnotatedString,
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                        onClick = { offset ->
                            fullAnnotatedString.getStringAnnotations(tag = "WORD", start = offset, end = offset)
                                .firstOrNull()?.let { annotation ->
                                    val tappedWord = annotation.item
                                    selectedWord = tappedWord
                                    onWordClick(tappedWord)
                                }
                        },
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        } else if (error == null) {
            Text(
                text = "No definitions loaded",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

private fun parseHtmlAndAnnotateWords(
    htmlText: String,
    selectedWord: String?
): AnnotatedString {
    val htmlParsed = parseHtmlToAnnotatedString(htmlText)
    return buildAnnotatedString {
        append(htmlParsed)
        
        val plainText = htmlParsed.text
        val wordRegex = Regex("\\b[a-zA-Z]+\\b")
        wordRegex.findAll(plainText).forEach { match ->
            val word = match.value
            val start = match.range.first
            val end = match.range.last + 1
            
            addStringAnnotation(tag = "WORD", annotation = word, start = start, end = end)
            
            if (selectedWord != null && word.equals(selectedWord, ignoreCase = true)) {
                addStyle(
                    SpanStyle(
                        background = Color.Yellow,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    start,
                    end
                )
            }
        }
    }
}

private fun parseHtmlToAnnotatedString(htmlText: String): AnnotatedString {
    return buildAnnotatedString {
        var i = 0
        val boldStack = mutableListOf<Int>()
        val italicStack = mutableListOf<Int>()
        val cleanText = StringBuilder()

        while (i < htmlText.length) {
            when {
                htmlText.startsWith("<b>", i) -> {
                    boldStack.add(cleanText.length)
                    i += 3
                }
                htmlText.startsWith("</b>", i) -> {
                    if (boldStack.isNotEmpty()) {
                        val start = boldStack.removeAt(boldStack.lastIndex)
                        addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, cleanText.length)
                    }
                    i += 4
                }
                htmlText.startsWith("<i>", i) -> {
                    italicStack.add(cleanText.length)
                    i += 3
                }
                htmlText.startsWith("</i>", i) -> {
                    if (italicStack.isNotEmpty()) {
                        val start = italicStack.removeAt(italicStack.lastIndex)
                        addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, cleanText.length)
                    }
                    i += 4
                }
                htmlText[i] == '<' -> {
                    val closingIndex = htmlText.indexOf('>', i)
                    if (closingIndex != -1) {
                        val tag = htmlText.substring(i, closingIndex + 1)
                        i = closingIndex + 1
                    } else {
                        cleanText.append(htmlText[i])
                        i++
                    }
                }
                else -> {
                    cleanText.append(htmlText[i])
                    i++
                }
            }
        }
        
        append(cleanText.toString())
    }
}
