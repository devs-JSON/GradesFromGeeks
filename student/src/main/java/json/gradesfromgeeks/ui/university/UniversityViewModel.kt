package json.gradesfromgeeks.ui.university

import android.util.Log
import json.gradesfromgeeks.data.entity.University
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import kotlinx.coroutines.delay

class UniversityViewModel(
    private val id: String,
    private val ggRepository: GradesFromGeeksRepository
) : BaseViewModel<UniversityUIState, UniversityUIEffect>(UniversityUIState()) {

    init {
        Log.i("UNIVERSITY-LOG", id)
        onMakeRequest()
    }

    private fun onMakeRequest() {
        updateState { it.copy(isLoading = true) }

        tryToExecute(
            {
                delay(1000)
                ggRepository.getUniversityById(id)
            },
            ::onSuccess,
            ::onError
        )
    }


    private fun onSuccess(university: University) {
        updateState {
            it.copy(
                isSuccess = true,
                isError = false,
                isLoading = false,
                universityDetails = university.toUIState()
            )
        }
    }

    private fun onError() {
        updateState {
            UniversityUIState(
                isError = true,
                isLoading = false,
                isSuccess = false
            )
        }
        sendNewEffect(UniversityUIEffect.UniversityError)
    }


}
