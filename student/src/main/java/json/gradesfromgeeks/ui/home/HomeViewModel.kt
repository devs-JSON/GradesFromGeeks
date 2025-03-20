package json.gradesfromgeeks.ui.home


import json.gradesfromgeeks.data.entity.Meeting
import json.gradesfromgeeks.data.entity.Mentor
import json.gradesfromgeeks.data.entity.Subject
import json.gradesfromgeeks.data.entity.University
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import json.gradesfromgeeks.ui.sharedState.toUiState
import json.gradesfromgeeks.ui.sharedState.toUniversityUiState
import json.gradesfromgeeks.utils.isLessThanXMinutes

class HomeViewModel(
    private val repository: GradesFromGeeksRepository
) : BaseViewModel<HomeUIState, HomeUIEffect>(HomeUIState()) {

    init {
        getData()
    }

    private fun getData() {
        updateState { it.copy(isLoading = true) }
        getMentors()
        getSubjects()
        getUniversities()
        getUpComingMeetings()
    }

    private fun getMentors() {
        tryToExecute(
            repository::getMentors,
            ::onSuccessMentors,
            ::onError
        )
    }

    private fun onSuccessMentors(mentor: List<Mentor>) {
        updateState { it.copy(mentors = mentor.toUiState(), isLoading = false) }
    }

    private fun getSubjects() {
        tryToExecute(
            repository::getSubject,
            ::onSuccessSubject,
            ::onError
        )
    }

    private fun onSuccessSubject(subjects: List<Subject>) {
        updateState { it.copy(subjects = subjects.take(6).toSubjectUiState(), isLoading = false) }
    }

    private fun getUniversities() {
        tryToExecute(
            repository::getUniversities,
            ::onSuccessUniversity,
            ::onError
        )
    }

    private fun onSuccessUniversity(universities: List<University>) {
        updateState {
            it.copy(
                university = universities.take(6).toUniversityUiState(), isLoading = false
            )
        }
    }

    private fun getUpComingMeetings() {
        tryToExecute(
            repository::getUpComingMeetings,
            ::onSuccessMeetings,
            ::onError
        )
    }

    private fun onSuccessMeetings(meetings: List<Meeting>) {
        updateState {
            it.copy(
                upComingMeetings = meetings.take(2).toUpCompingMeetingUiState(), isLoading = false
            )
        }
    }

    private fun onError() {
        updateState { HomeUIState(isError = true, isLoading = false) }
        sendNewEffect(HomeUIEffect.HomeError)
    }

    fun updateMeeting() {
        updateState {
            it.copy(
                upComingMeetings = it.upComingMeetings.mapNotNull { meeting ->
                    val reminder = getReminderTime(meeting.time)
                    if (reminder < -10) {
                        null
                    } else {
                        meeting.copy(
                            reminder = reminder,
                            enableJoin = meeting.time.isLessThanXMinutes()
                        )
                    }
                }
            )
        }
    }
}
