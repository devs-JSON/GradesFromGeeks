package json.gradesfromgeeks.data.repositories

import com.google.ai.client.generativeai.type.GenerateContentResponse
import json.gradesfromgeeks.data.entity.Date
import json.gradesfromgeeks.data.entity.Download
import json.gradesfromgeeks.data.entity.Language
import json.gradesfromgeeks.data.entity.Meeting
import json.gradesfromgeeks.data.entity.Mentor
import json.gradesfromgeeks.data.entity.Notification
import json.gradesfromgeeks.data.entity.SearchResult
import json.gradesfromgeeks.data.entity.Subject
import json.gradesfromgeeks.data.entity.Summaries
import json.gradesfromgeeks.data.entity.University
import json.gradesfromgeeks.data.entity.User
import kotlinx.coroutines.flow.Flow


interface GradesFromGeeksRepository {
    suspend fun queryDocument(context: String, question: String): String
    suspend fun getVideoUrl(): String
    suspend fun getUniversitiesName(): List<String>
    suspend fun getIsFirstTimeUseApp(): Boolean
    suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean)
    suspend fun getSummarizeTextFromVideo(): String
    suspend fun getSearch(keyword: String, limit: Int): SearchResult

    //region Download
    suspend fun getDownloadDetails(id: String): Download

    //endregion

    //region Mentor
    suspend fun getMentors(): List<Mentor>
    suspend fun getMentorDetails(id: String): Mentor
    suspend fun getSummaries(): List<Summaries>
    suspend fun getVideos() : List<Summaries>
    suspend fun getUserData(): User
    suspend fun getMeeting(): List<Meeting>

    //endregion


    //region Subject
    suspend fun getSubject(): List<Subject>
    suspend fun getSubjectById(id: String) : Subject

    //endregion

    //region Notifications
    suspend fun getNotifications(): List<Notification>
    //endregion


    //region Universities
    suspend fun getUniversities(): List<University>
    fun getUniversitiesNames(): List<String>

    suspend fun getUniversityById(id: String): University


    //endregion



    suspend fun getUpComingMeetings(): List<Meeting>

    //endregion

    //region gimmien ai
    suspend fun getAnswerAboutUniversityTopic(question: String, university: String): String


    fun getAvailableTimeForMentor(mentorId: String): List<Date>

    // region fields
    suspend fun getFields(): List<String>
    //endregion

    //region Language and Theme
    suspend fun saveLanguage(language: Language)
    fun getLanguage(): Flow<Language>
    suspend fun setTheme(isDark: Boolean)
    fun getTheme(): Flow<Boolean?>
    //endregion

    //region Levels
    suspend fun getLevels(): List<Int>
    //endregion
}
