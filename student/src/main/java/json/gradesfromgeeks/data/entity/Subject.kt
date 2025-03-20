package json.gradesfromgeeks.data.entity

data class Subject(
    val id: String,
    val name: String,
    val imageUrl: String,
    val mentorNumber: String,
    val summaryNumber: String,
    val videoNumber: String,
    val mentors: List<String>
)
