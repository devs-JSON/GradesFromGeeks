package json.gradesfromgeeks.ui.seeAll

import json.gradesfromgeeks.ui.sharedState.MentorUiState
import json.gradesfromgeeks.ui.sharedState.SubjectsUiState
import json.gradesfromgeeks.ui.sharedState.UniversityUiState


data class SeeAllUIState(
    val type: SeeAllType = SeeAllType.Subjects,
    val universities: List<UniversityUiState> = emptyList(),
    val mentors: List<MentorUiState> = emptyList(),
    val subjects: List<SubjectsUiState> = emptyList(),

    val isLoading: Boolean = false,
    val isError: Boolean = false,
)


enum class SeeAllType(val value: String) {
    Mentors("Mentors"),
    Universities("Universities"),
    Subjects("Subjects"),

}

fun String.toSeeAllType(): SeeAllType {
    return when (this) {
        SeeAllType.Mentors.value -> SeeAllType.Mentors
        SeeAllType.Universities.value -> SeeAllType.Universities
        else -> SeeAllType.Subjects
    }
}
