package json.gradesfromgeeks.data.repositories


interface GradesFromGeeksRepository {
    suspend fun queryDocument(context: String, question: String): String
    suspend fun getUniversitiesName(): List<String>
    suspend fun getAnswerAboutUniversityTopic(question: String, university: String): String

}
