package json.gradesfromgeeks.ui.chat

sealed interface ChatUIEffect {
    data object Error : ChatUIEffect
}
