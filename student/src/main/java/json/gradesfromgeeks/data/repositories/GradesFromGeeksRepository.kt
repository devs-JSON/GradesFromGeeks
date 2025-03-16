package json.gradesfromgeeks.data.repositories


interface GradesFromGeeksRepository {
    suspend fun getUniversitiesName(): List<String>
    suspend fun getAnswerAboutUniversityTopic(question: String, university: String): String

}
