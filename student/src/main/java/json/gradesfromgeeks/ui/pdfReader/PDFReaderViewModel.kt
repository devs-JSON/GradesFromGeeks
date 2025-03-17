package json.gradesfromgeeks.ui.pdfReader

import androidx.lifecycle.ViewModel
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPdfReaderState
import org.koin.core.component.KoinComponent

class PDFReaderViewModel : ViewModel(), KoinComponent {

    val pdfVerticalReaderState = VerticalPdfReaderState(
        resource = ResourceType.Remote("https://drive.google.com/uc?export=download&id=10UuI-8P-aDFizgyb3YuTvKe4D_n_yq1f"),
        isZoomEnable = true
    )
}
