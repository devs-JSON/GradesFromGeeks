package json.gradesfromgeeks.ui.utils

import android.content.Context
import android.net.Uri
import android.content.res.Configuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.IOException
import java.util.Locale

object Utils {
    @OptIn(ExperimentalTextApi::class)
    fun textWithLink(fullText: String, linkText: String, url: String, color:Color): AnnotatedString {
        val annotatedLinkString: AnnotatedString = buildAnnotatedString {
            val startIndex = fullText.indexOf(linkText)
            val endIndex = startIndex + linkText.length
            append(fullText)
            addStyle(
                style = SpanStyle(
                    color = color,
                    textDecoration = TextDecoration.None
                ), start = startIndex, end = endIndex
            )

            addUrlAnnotation(
                UrlAnnotation(url),
                start = startIndex,
                end = endIndex
            )
        }
        return annotatedLinkString
    }
    fun readTextFromUri(uri: Uri, context: Context): String {
        var textResult = ""
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            try {
                PDDocument.load(inputStream).use { pdfDocument ->
                    if (!pdfDocument.isEncrypted) {
                        textResult = PDFTextStripper().getText(pdfDocument)
                    }
                }
            } catch (e: IOException) {
                textResult = "Error reading PDF: ${e.localizedMessage}"
            }
        }
        return textResult
    }
}


fun updateLanguage(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    context.createConfigurationContext(config)


}
