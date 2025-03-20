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

    init {
        getUniversities()
    }

    fun onDocumentSelected(text: String) {
        updateState {
            it.copy(
                documentText = text,
                isDocumentMode = true,
                hasSelectedSource = true,
                showSourceSelector = false,
                isFirstEnter = false
            )
        }
    }

    private fun handleDocumentQuery(question: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateState { it.copy(isDocumentProcessing = true) }

            val response = mentorRepository.queryDocument(
                state.value.documentText,
                question
            )

            val messages = state.value.messages.toMutableList().apply {
                add(MessageUIState(isMe = false, message = response))
            }

            updateState {
                it.copy(
                    messages = messages,
                    isDocumentProcessing = false
                )
            }
        }
    }


    private fun getUniversities() {
        viewModelScope.launch {
            val universities = mentorRepository.getUniversitiesName()
            updateState { it.copy(universities = universities) }
        }
    }

    // Modify dismiss to handle both cases
    fun onDismissRequest() {
        if (!state.value.hasSelectedSource) {
            updateState { it.copy(isUniversitySheetOpen = !it.isUniversitySheetOpen) }
        }
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
        val message = state.value.message
        val messages = state.value.messages.toMutableList().apply {
            add(MessageUIState(isMe = true, message = message))
        }

        updateState {
            it.copy(
                messages = messages,
                message = "",
                isLoading = true
            )
        }

        if (state.value.isDocumentMode) {
            handleDocumentQuery(message)
        } else {
            getData(message)
        }
    }

    fun onSelectUniversity(index: Int) {
        updateState {
            it.copy(
                messages = emptyList(),
                universityName = state.value.universities[index],
                isUniversitySheetOpen = false,
                isDocumentMode = false,
                hasSelectedSource = true,
                showSourceSelector = false,
                isFirstEnter = false
            )
        }
        updateState { it.copy(selectedUniversity = index, isFirstEnter = false) }
    }

    fun onChanceMessage(newValue: String) {
        updateState { it.copy(message = newValue) }
    }

}
