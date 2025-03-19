package json.gradesfromgeeks.ui.search

import json.gradesfromgeeks.data.entity.SearchResult
import json.gradesfromgeeks.ui.home.SubjectDetailsUiState
import json.gradesfromgeeks.ui.home.toSubjectUiState
import json.gradesfromgeeks.ui.sharedState.MentorUiState
import json.gradesfromgeeks.ui.sharedState.UniversityUiState
import json.gradesfromgeeks.ui.sharedState.toUiState
import json.gradesfromgeeks.ui.sharedState.toUniversityUiState


data class SearchUIState(
    val keyword: String = "",
    val universities: List<UniversityUiState> = emptyList(),
    val mentors: List<MentorUiState> = emptyList(),
    val subjects: List<SubjectDetailsUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
fun SearchResult.toUIState(): SearchUIState {
    return SearchUIState(
        universities = universities.toUniversityUiState(),
        mentors = mentors.toUiState(),
        subjects = subject.toSubjectUiState(),
    )
}