package json.gradesfromgeeks.ui.individualMeeting


import json.gradesfromgeeks.data.entity.Date
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import kotlinx.coroutines.delay

class IndividualMeetingViewModel(
    private val repository: GradesFromGeeksRepository
) : BaseViewModel<IndividualMeetingUIState, IndividualMeetingUIEffect>(IndividualMeetingUIState()) {

    init {
        getData()
    }

    private fun getData() {
        tryToExecute(
            {
                updateState { it.copy(isLoading = true, isError = false) }
                delay(1000)
                repository.getAvailableTimeForMentor("")
            },
            ::onSuccess, ::onError
        )
    }

    private fun onSuccess(dates: List<Date>) {
        updateState { it.copy(isLoading = false, availableDates = dates.toDateUiState()) }
    }

    private fun onError() {
        updateState {
            IndividualMeetingUIState(isError = true, isLoading = false)
        }
        sendNewEffect(IndividualMeetingUIEffect.IndividualMeetingError)
    }


    fun onTimeSelected(time: TimeUiState, day: String) {
        updateState {
            it.copy(
                selectedTime = time.copy(day = day),
                showBottomSheet = true,
                availableDates = it.availableDates.map {availableDate->
                    availableDate.setSelectTime(time.id) },
            )
        }
    }

    fun onDismissRequest() {
        updateState {
            it.copy(
                selectedTime = null,
                showBottomSheet = false,
                availableDates = it.availableDates.map { it.setSelectTime() })
        }
    }

    fun onValueChange(newNote: String) {
        updateState { it.copy(note = newNote) }
    }

    fun onBookClick() {
        updateState {
            it.copy(
                selectedTime = null,
                showBottomSheet = false,
                availableDates = it.availableDates.map { it.setSelectTime() }
            )
        }
    }

}
