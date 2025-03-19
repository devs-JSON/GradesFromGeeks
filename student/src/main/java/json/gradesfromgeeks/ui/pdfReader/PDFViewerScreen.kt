package json.gradesfromgeeks.ui.pdfReader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rizzi.bouquet.VerticalPDFReader
import json.gradesfromgeeks.ui.pdfReader.PDFReaderViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun PDFViewerScreen(
    viewModel: PDFReaderViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    VerticalPDFReader(
        state = viewModel.pdfVerticalReaderState,
        modifier = Modifier.fillMaxSize()
    )
}
