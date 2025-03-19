package json.gradesfromgeeks.ui.sharedState

import json.gradesfromgeeks.data.entity.Subject

data class SubjectsUiState(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val imageUrl: String = ""
)

fun Subject.toSubjectUiState() = SubjectsUiState(
    id = id,
    imageUrl = imageUrl,
    name = name,
    address = ""
)

fun List<Subject>.toSubjectsUiState() = map { it.toSubjectUiState() }
