package json.gradesfromgeeks.ui.subject

import android.util.Log
import json.gradesfromgeeks.data.entity.Subject
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import json.gradesfromgeeks.ui.home.toSubjectUiState
import json.gradesfromgeeks.ui.sharedState.toUiState
import kotlinx.coroutines.delay

class SubjectViewModel(
    val id: String,
    private val ggRepository: GradesFromGeeksRepository
) : BaseViewModel<SubjectUIState, SubjectUIEffect>(SubjectUIState()) {

    init {
        Log.i("lllllllllll", id)
        onMakeRequest()
        getMentors()
    }

    private fun onMakeRequest() {
        updateState { it.copy(isLoading = true) }

        tryToExecute(
            {
                delay(1000)
                ggRepository.getSubjectById(id)
            },
            ::onSuccess,
            ::onError
        )
    }

    private fun getMentors() {
        tryToExecute(
            {
                ggRepository.getMentors()
            },
            { mentors ->
                updateState { it.copy(subjectMentors = mentors.toUiState()) }
            },
            ::onError
        )
    }

    private fun onSuccess(subject: Subject) {
        updateState {
            it.copy(
                isSuccess = true,
                isError = false,
                isLoading = false,
                subjectDetails = subject.toSubjectUiState(),
            )
        }
    }

    private fun onError() {
        updateState {
            SubjectUIState(
                isError = true,
                isLoading = false,
                isSuccess = false
            )
        }
        sendNewEffect(SubjectUIEffect.SubjectError)
    }


}
