package json.gradesfromgeeks.ui.seeAll

import json.gradesfromgeeks.data.entity.Mentor
import json.gradesfromgeeks.data.entity.Subject
import json.gradesfromgeeks.data.entity.University
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import json.gradesfromgeeks.ui.sharedState.toSubjectsUiState
import json.gradesfromgeeks.ui.sharedState.toUiState
import json.gradesfromgeeks.ui.sharedState.toUniversityUiState

class SeeAllViewModel(
    private val type: SeeAllType,
    private val repository: GradesFromGeeksRepository
) : BaseViewModel<SeeAllUIState, SeeAllUIEffect>(SeeAllUIState()) {

    init {
        updateState { it.copy(type = type) }
        getData()
    }

    private fun getData() {
        updateState { it.copy(isLoading = true) }
        when (state.value.type) {
            SeeAllType.Mentors -> getMentors()
            SeeAllType.Universities -> getUniversities()
            SeeAllType.Subjects -> getSubjects()
        }
    }

    private fun getMentors() {
        tryToExecute(repository::getMentors, ::onSuccessMentors, ::onError)
    }

    private fun onSuccessMentors(mentor: List<Mentor>) {
        updateState { it.copy(mentors = mentor.toUiState(), isLoading = false) }
    }


    private fun getUniversities() {
        tryToExecute(repository::getUniversities, ::onSuccessUniversity, ::onError)
    }

    private fun onSuccessUniversity(universities: List<University>) {
        updateState {
            it.copy(universities = universities.toUniversityUiState(), isLoading = false)
        }
    }


    private fun getSubjects() {
        tryToExecute(repository::getSubject, ::onSuccessSubjects, ::onError)
    }

    private fun onSuccessSubjects(subjects: List<Subject>) {
        updateState {
            it.copy(subjects = subjects.toSubjectsUiState(), isLoading = false)
        }
    }

    private fun onError() {
        updateState { SeeAllUIState(isError = true, isLoading = false) }
        sendNewEffect(SeeAllUIEffect.SeeAllError)
    }

}
