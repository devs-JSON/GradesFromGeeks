package json.gradesfromgeeks.ui.chat

import androidx.lifecycle.viewModelScope
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatBotViewModel(
    private val mentorRepository: GradesFromGeeksRepository
) : BaseViewModel<ChatUiState, ChatUIEffect>(ChatUiState()) {
    init {
     getUniversities()
    }

    private fun getUniversities() {
        viewModelScope.launch {
            val universities = mentorRepository.getUniversitiesName()
            updateState { it.copy(universities = universities) }
        }
    }

    fun onDismissRequest() {
        updateState { it.copy(isUniversitySheetOpen = !state.value.isUniversitySheetOpen) }
    }

    private fun getData(msg: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mentorRepository.getAnswerAboutUniversityTopic(
                question = msg, university = state.value.universityName
            )
            val messages = state.value.messages.toMutableList()
            messages.add(MessageUIState(isMe = true, message = ""))
            val old = messages.last().message + " " + response
            messages.removeLast()
            messages.add(MessageUIState(isMe = false, message = old))
            updateState { it.copy(messages = messages) }
        }
    }


    fun onSendClicked() {
        val oldMsg = state.value.message
        val messages = state.value.messages.toMutableList()
        messages.add(MessageUIState(isMe = true, message = oldMsg))
        updateState { it.copy(messages = messages, message = "") }
        getData(oldMsg)
    }

    fun onSelectUniversity(index: Int) {
        updateState { it.copy(messages = emptyList(), universityName =state.value.universities[index] , isUniversitySheetOpen = false) }
        updateState { it.copy(selectedUniversity = index, isFirstEnter = false) }
    }

    fun onChanceMessage(newValue: String) {
        updateState { it.copy(message = newValue) }
    }

}
