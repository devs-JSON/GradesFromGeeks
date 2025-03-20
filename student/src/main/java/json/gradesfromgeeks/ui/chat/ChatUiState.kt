package json.gradesfromgeeks.ui.chat


data class ChatUiState(
    val messages: List<MessageUIState> = emptyList(),
    val universities: List<String> = emptyList(),
    val message: String = "",
    val isLoading: Boolean = false,
    val canNotSendMessage: Boolean = false,
    val error: String? = "",
    val userRole: String = "",
    val modelRole: String = "",
    val isFirstEnter: Boolean = true,
    val selectedUniversity: Int = -1,
    val universityName: String = "",
    val isUniversitySheetOpen: Boolean = false,
    val isDocumentMode: Boolean = false,
    val documentText: String = "",
    val isDocumentAttached: Boolean = false,
    val isDocumentProcessing: Boolean = false,
    val hasSelectedSource: Boolean = false,
    val showSourceSelector: Boolean = true
) {

}

data class MessageUIState(
    val isMe: Boolean = false,
    val message: String = "",
)
