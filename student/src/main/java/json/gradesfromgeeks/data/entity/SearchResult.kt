package json.gradesfromgeeks.data.entity


data class SearchResult(
    val universities: List<University> = emptyList(),
    val mentors: List<Mentor> = emptyList(),
    val subject: List<Subject> = emptyList(),
)