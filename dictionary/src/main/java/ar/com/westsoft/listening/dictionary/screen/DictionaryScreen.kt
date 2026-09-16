package ar.com.westsoft.listening.dictionary.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DictionaryScreen(
    definition: String?,
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

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
            text = "Dictionary Definition",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (definition != null) {
            val annotatedDefinition = remember(definition) {
                parseHtmlToAnnotatedString(definition)
            }
            Text(
                text = annotatedDefinition,
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            Text(
                text = "No definition loaded",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

private fun parseHtmlToAnnotatedString(htmlText: String): AnnotatedString {
    if (htmlText.startsWith("Error")) {
        return buildAnnotatedString {
            append(htmlText)
            addStyle(SpanStyle(color = Color.Red), 0, htmlText.length)
        }
    }

    // A quick simple stateful tag parser for <b>, <i>, etc.
    return buildAnnotatedString {
        var i = 0
        val boldStack = mutableListOf<Int>()
        val italicStack = mutableListOf<Int>()

        // Pre-parse using android.text.Html to normalize entities (like &quot;, &lt;, etc.)
        // But since we want to handle internal sub-tags customized, we can also parse line-by-line.
        // Let's iterate through characters to build spans for simple <b> and <i> tags.
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
                else -> {
                    cleanText.append(htmlText[i])
                    i++
                }
            }
        }
        
        append(cleanText.toString())

        // Apply a distinct style for structural headings (like "noun:", "verb:")
        val textStr = cleanText.toString()
        textStr.split("\n").forEach { line ->
            if (line.endsWith(":")) {
                val start = textStr.indexOf(line)
                if (start != -1) {
                    addStyle(
                        SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF2196F3)),
                        start,
                        start + line.length
                    )
                }
            }
        }
    }
}
