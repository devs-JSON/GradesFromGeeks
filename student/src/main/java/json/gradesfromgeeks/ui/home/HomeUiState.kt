package json.gradesfromgeeks.ui.home

import json.gradesfromgeeks.data.entity.Subject

data class SubjectDetailsUiState(
    val id: String = "",
    val name: String = "",
    val mentorNumber: String = "",
    val summaryNumber: String = "",
    val videoNumber: String = "",
    val mentors: List<String> = emptyList()
)
fun Subject.toSubjectUiState() = SubjectDetailsUiState(
    id = id,
    name = name,
    mentorNumber = mentorNumber,
    summaryNumber = summaryNumber,
    videoNumber = videoNumber,
    mentors = mentors
)

fun List<Subject>.toSubjectUiState() = map { it.toSubjectUiState() }