package json.gradesfromgeeks.data.repositories

import com.google.ai.client.generativeai.type.asTextOrNull
import json.gradesfromgeeks.data.entity.HuggingFaceRequest
import json.gradesfromgeeks.data.entity.InputData
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
import json.gradesfromgeeks.data.source.BaseRepository
import json.gradesfromgeeks.data.source.local.UserPreferences
import json.gradesfromgeeks.data.source.remote.service.GeminiApi
import json.gradesfromgeeks.data.source.remote.service.HuggingFaceApiService
import json.gradesfromgeeks.ui.notification.NotificationType
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GradesFromGeeksRepositoryImp(
    private val geminiApi: GeminiApi,
    private val huggingFaceApi: HuggingFaceApiService
    private val authorizationPreferences: UserPreferences
) : BaseRepository(), GradesFromGeeksRepository {



    override suspend fun saveLanguage(language: Language) {
        authorizationPreferences.saveLanguage(language)
    }

    override fun getLanguage(): Flow<Language> {
        return authorizationPreferences.getLanguage()

    }
    override suspend fun setTheme(isDark: Boolean) {
        authorizationPreferences.saveTheme(isDark)
    }

    override fun getTheme(): Flow<Boolean?> {
        return authorizationPreferences.isDarkTheme()
    }

    override suspend fun getNotifications(): List<Notification> {
        return listOf(
            Notification(
                id = 1,
                ownerId = 3,
                mentorName = "Nada Feteiha",
                timeCreated = "12 sec",
                type = NotificationType.MEETING_ACCEPTED,
                viewed = true,
                subjectId = 1,
            ),
            Notification(
                id = 2,
                ownerId = 3,
                mentorName = "Nada Feteiha",
                timeCreated = "1 hr ago",
                type = NotificationType.UPCOMING_MEETING,
                viewed = false,
                subjectId = 2
            ),
            Notification(
                id = 3,
                ownerId = 3,
                mentorName = "Nada Feteiha",
                timeCreated = "1 hr ago",
                type = NotificationType.NEW_SCHEDULE_MEETING_GROUP,
                viewed = false,
                subjectId = 3
            ),
            Notification(
                id = 4,
                ownerId = 5,
                mentorName = "Amnah.a",
                timeCreated = "56 min ago",
                type = NotificationType.NEW_SUMMARY,
                viewed = false,
                subjectId = 4
            ),
            Notification(
                id = 5,
                ownerId = 5,
                mentorName = "Amnah.a",
                timeCreated = "56 sec",
                type = NotificationType.NEW_VIDEO,
                viewed = true,
                subjectId = 5
            ),
            Notification(
                id = 6,
                ownerId = 5,
                mentorName = "Amnah.a",
                timeCreated = "56 sec",
                type = NotificationType.NEW_VIDEO,
                viewed = true,
                subjectId = 5
            ),
            Notification(
                id = 7, ownerId = 5, mentorName = "Amnah.a", timeCreated = "56 sec",
                type = NotificationType.NEW_VIDEO,
                viewed = true,
                subjectId = 5
            ),
            Notification(
                id = 8,
                ownerId = 3,
                mentorName = "Nada Feteiha",
                timeCreated = "1 hr ago",
                type = NotificationType.UPCOMING_MEETING,
                viewed = false,
                subjectId = 2
            ),
            Notification(
                id = 9,
                ownerId = 3,
                mentorName = "Nada Feteiha",
                timeCreated = "1 hr ago",
                type = NotificationType.NEW_SCHEDULE_MEETING_GROUP,
                viewed = false,
                subjectId = 3
            ),
        )
    }


    override suspend fun getUniversitiesName(): List<String> {
        return listOf(
            "جامعة واشنطن",
            "جامعة كاليفورنيا، لوس أنجلوس",
            "جامعة بغداد",
            "جامعة كاليفورنيا، بيركلي",
            "جامعة هارفارد",
            "جامعة ستانفورد",
            "معهد ماساتشوستس للتكنولوجيا (MIT)",
            "جامعة أكسفورد",
            "جامعة كامبريدج",
            "معهد كاليفورنيا للتكنولوجيا (كالتيك)",
            "المعهد الفيدرالي السويسري للتكنولوجيا (ETH Zurich)",
            "جامعة كلية لندن (UCL)",
            "جامعة شيكاغو",
            "كلية إمبريال لندن"
        )
    }

    override suspend fun getAnswerAboutUniversityTopic(question: String, university: String): String {
        return geminiApi.generateUniversityContent(
            question = question,
            university = university
        ).candidates.last().content.parts.first().asTextOrNull()?:" "
    }
    override suspend fun getVideoUrl(): String {
        return "https://www.kaltura.com/p/1838561/sp/183856100/playManifest/entryId/1_3ysdu8d4/protocol/https/format/applehttp/flavorIds/1_v94arpvc/ks/djJ8MTgzODU2MXzE7qmX-CtFR9N3ibayqga2fIS4VZIRFynkSt_AQAqRAQdaLCATg6HXM1DF2ddgK4Rxmq-wG_hlO9eC67ad_hjOSd8b-HOgUmATEU7nTnr9n0E1I85UQT1uLfRuEqPzJjcTHPDXowRTSTN-qkvJf4NX05o0yzcTMdJSt_PnXsIeuUwsu5toZMwuNJLN5Goo0Smzb1D2dUxjZxe6MW_wC50u0ETYsfMuNBQBOT3anEEjPsj7KVVgeBtiyo_Agr0NwnuX85xVJ6YzvNAhaCWkDO5bwelmHVPzAjU4vOsCvyyFu7GmissopTPnYnd3rtA537K3JyYj-Kbpd-rxxUCk1lRfhK0fEFpNdswWofDQ4joznWeBOCfJaYRoR9CsbLUzIdfVugPZW9U598oSp-CVxbLT_tStWoowGjfHYfbVNa9zv8AIcJR4QYlId69D05yrukwiN2jy7F87lWyD47Pl8-yqtzsZs77DjdOphaGEY_DfXakOngNdQVB1VjSgkb1Ll3A=/a.m3u8?uiConfId=55310943&playSessionId=c6737052-ee6e-e726-9965-10a5949cf2d9:67b5aea0-57f5-0f48-3ed7-ae376c638a9a&referrer=aHR0cHM6Ly9sZWFybi5zdW55ZW1waXJlLmVkdS9tZWRpYS9TYW1wbGUlMkJQZXJzdWFzaXZlJTJCU3BlZWNoLm1wNC8xXzN5c2R1OGQ0LzMwNzM2MDg3Mj91dG1fc291cmNlPWNoYXRncHQuY29t&clientTag=html5:v7.166"
    }

    override suspend fun getSummarizeTextFromVideo(): String {
        val videoTranscript = "On a chilly November\n" +
                "night two years ago, a Ford explorer\n" +
                "was charging down a California highway. The 16 year old\n" +
                "driver and three of his friends were\n" +
                "returning from a concert in Los Angeles. These young people were good students,\n" +
                "gifted athletes, talented artists\n" +
                "and musicians, and none were drunk\n" +
                "or impaired by drugs. They were, however,\n" +
                "driving too fast, and the driver lost\n" +
                "control of the car. The car went into a\n" +
                "ditch and hit a tree. The driver and one\n" +
                "passenger were killed. The other two passengers escaped with\n" +
                "severe injuries. One of these passengers\n" +
                "was my nephew. Today, he is\n" +
                "finishing high school in a wheelchair, a wheelchair\n" +
                "he will occupy for the rest of his life. Unfortunately, tragic auto\n" +
                "accidents involving teenage drivers\n" +
                "are much too common in all parts\n" +
                "of the United States. After researching the\n" +
                "subject for my speech, I have come to the\n" +
                "same conclusion as the experts that the\n" +
                "best way to prevent such accidents is to\n" +
                "raise the age for full driving privileges\n" +
                "to 18 or older. I know from my audience analysis questionnaire that most of you\n" +
                "oppose such a plan. But I also know from\n" +
                "my questionnaire that most of you recognize that 16 and 17 year\n" +
                "old drivers are less skilled and less responsible than\n" +
                "older drivers. So I ask you\n" +
                "to listen with an open mind\n" +
                "while we discuss some of the problems associated with\n" +
                "teenage driving. Some of the major\n" +
                "causes of the problems and a plan that\n" +
                "will go a long way towards solving\n" +
                "some of the problems. No matter how one\n" +
                "looks at the evidence, it all leads to one fact. There are too many motor\n" +
                "vehicle accidents, deaths and injuries involving teenage drivers. According to the National Highway Traffic Safety\n" +
                "Administration, while teenagers make up 7% of the nation's\n" +
                "licensed drivers, they represent 14% of all motor vehicle\n" +
                "fatalities. The N HTSA reports\n" +
                "that last year, 3,657 drivers\n" +
                "aged 16 to 20 were killed in\n" +
                "automobile accidents. In addition to\n" +
                "killing the drivers, these same accidents\n" +
                "took the lives of 2384 teenage\n" +
                "passengers. But these accidents didn't affect teenagers alone. They also took\n" +
                "the lives of 2625 people aged\n" +
                "21 or older. So the total number of people killed last year in automobile accidents involving teenage drivers? W. 8,666, almost exactly\n" +
                "the number of full time students\n" +
                "at this campus. Evidence also shows that the younger the driver,\n" +
                "the greater the risk. According to the Insurance Institute for\n" +
                "highway safety, 16 year olds have the highest percentage of crashes involving\n" +
                "speeding, the highest percentage of single vehicle crashes, and the highest\n" +
                "percentage of crashes involving\n" +
                "driver error. Moreover, as USA\n" +
                "today reports, 16 year olds are three\n" +
                "times more likely to be involved\n" +
                "in fatal crashes than our older drivers. Now that we've seen the extent of the problem, we can explore its causes. One of the causes\n" +
                "is in experience. New drivers just\n" +
                "haven't had enough time on the road to develop their\n" +
                "driving skills. But inexperience is far from the only cause\n" +
                "of the problem. After all, there\n" +
                "will always be inexperienced drivers, even if the driving age is raised to 21\n" +
                "or even to 25. A second cause is revealed\n" +
                "by brain research. Findings from the National Institute of Mental Health show that the brain of an average 16\n" +
                "year old has not developed to\n" +
                "the point where he or she is able to effectively\n" +
                "judge the risk of a given situation. Doctor J Get who led\n" +
                "the research team that conducted the study\n" +
                "states when a smart, talented, very mature teen does something that a parent might\n" +
                "call stupid. It's this\n" +
                "undundeveloped part of the brain that has\n" +
                "most likely failed. Stephen Lohenstein,\n" +
                "a medical professor at the University\n" +
                "of Colorado, has just finished a five\n" +
                "year study comparing the traffic records of 16 year old drivers to\n" +
                "drivers aged 25 to 49. His conclusion, deliberate\n" +
                "risk taking and dangerous and aggressive\n" +
                "driving behaviors predominated among\n" +
                "the 16 year olds. A third cause of motor vehicle\n" +
                "fatalities among teenage drivers\n" +
                "is night driving. According to the\n" +
                "Washington Post, when 16 year olds get behind the wheel of\n" +
                "a car after dark, the likelihood of having an accident increases\n" +
                "several times over. Of course,\n" +
                "nighttime driving is less safe for everyone. But it becomes\n" +
                "particularly dangerous when\n" +
                "combined with a young driver's\n" +
                "inexperience and reduced ability\n" +
                "to gauge risk. Finally, there\n" +
                "is the presence of teenage passengers\n" +
                "in the car. We all know what it's like to drive\n" +
                "with our friends. The stereos up loud, cell phones are ringing, everybody's laughing\n" +
                "and having a good time. The problem is that\n" +
                "all these factors create distractions, distractions that\n" +
                "too often result in accidents,\n" +
                "injury and death. Alan Williams,\n" +
                "chief scientist at the Insurance\n" +
                "Institute for Highway Safety\n" +
                "reports that one teenage\n" +
                "passenger doubles the risk of a fatal crash. With two or more\n" +
                "passengers, the risk is five\n" +
                "times greater. Remember my\n" +
                "nephew's accident, I mentioned at the\n" +
                "start of my speech. There were three\n" +
                "passengers in the car. So the extent of the\n" +
                "problem is clear. So, too, are its causes. What steps can we take to help bring\n" +
                "about a solution? First, we need a\n" +
                "national policy that no one can receive a learner's permit\n" +
                "until age 16 and no one can receive full driving privileges\n" +
                "until age 18. This will allow 16\n" +
                "year olds time to gain driving experience before having an\n" +
                "unrestricted license, and to reach a stage of brain development\n" +
                "where they are better able to handle the risk and responsibility\n" +
                "of driving. Second, we need to\n" +
                "restrict nighttime driving so as to keep\n" +
                "younger drivers off the road when\n" +
                "conditions are riskiest. Some states have\n" +
                "tried to address this problem by banning teenagers from\n" +
                "driving after midnight or 1:00 A.M. But as the\n" +
                "Insurance Institute for Highway\n" +
                "Safety reports, these laws don't\n" +
                "go far enough. According to\n" +
                "the institute, we need a 9:00\n" +
                "P.M. Or 10:00 P.M. Limit until drivers\n" +
                "reach the age of 18. Third, we need to\n" +
                "restrict the number of teenage passengers\n" +
                "and cars driven by younger drivers. In fact, says,\n" +
                "Kevin Quinlan, from the National Transportation\n" +
                "Safety Board. Passenger restriction is the first and\n" +
                "foremost measure you can take to reduce teenage\n" +
                "driving fatalities. According to Quinlan,\n" +
                "the optimal policy would be to bar\n" +
                "drivers aged 17 or younger from having\n" +
                "any passengers in the car unless the riders are adults or\n" +
                "family members. Drivers in the age\n" +
                "of 17 to 18 should not be allowed\n" +
                "to carry more than one teenage\n" +
                "passenger. Okay. Now, I know\n" +
                "all of this might sound harsh and\n" +
                "perhaps inconvenient. But the evidence\n" +
                "is clear that it would save a significant\n" +
                "number of lives. If you want to\n" +
                "discuss Harsh, said one father who\n" +
                "17 year old son died in an accident\n" +
                "three years ago, I can talk to\n" +
                "you about Harsh. It's being awakened at\n" +
                "2:30 in the morning by the state patrol telling you that your son has\n" +
                "just been killed. Everyone in this room has lived to college age. But this year alone, thousands of\n" +
                "teenage drivers will not live that long. And they won't live\n" +
                "that long due to factors that we\n" +
                "can prevent. There's no way to\n" +
                "solve all the problems we encounter on the road, but we can do something to help save the lives of younger drivers and make the road safer\n" +
                "for all of us. As I said earlier, this might sound harsh\n" +
                "or inconvenient. But I know my\n" +
                "nephew would gladly trade both for the\n" +
                "chance to walk again."

        val summary =  geminiApi.summarizeText(videoTranscript)
        return summary
    }


    override suspend fun getIsFirstTimeUseApp(): Boolean {
        return authorizationPreferences.getIsFirstTimeUseApp() ?: true
    }

    override suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean) {
        return authorizationPreferences.saveIsFirstTimeUseApp(isFirstTimeUseApp)
    }

    override suspend fun getSearch(keyword: String,limit:Int): SearchResult {
        return  if (keyword.isNotEmpty()) {
            val result = SearchResult(
                universities = getUniversities().filter {
                    it.name.contains(
                        keyword,
                        ignoreCase = true
                    )
                },
                mentors = getMentors().filter { it.name.contains(keyword, ignoreCase = true) },
                subject = getSubject().filter { it.name.contains(keyword, ignoreCase = true) }
            )
            result
        } else {
            SearchResult()
        }
    }

    override suspend fun getDownloadDetails(id: String): Download {
        return Download(
            summariesNumber = "10",
            videoNumber = "8",
            meetingNumber = "16",
            subjects = getSubject(),
            summaries = getSummaries(),
            meeting = getMeeting(),
            video = getVideos()
        )
    }

    override suspend fun getMentors(): List<Mentor> {
        return listOf(
            Mentor(
                id = "1",
                name = "Sara Ali",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "University of Baghdad"
            ),
            Mentor(
                id = "2",
                name = "Ahmed Ali",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "American University of Beirut"
            ),
            Mentor(
                id = "3",
                name = "Omar Hassan",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "Cairo University"
            ),
            Mentor(
                id = "4",
                name = "Layla Kareem",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "University of Jordan"
            ),
            Mentor(
                id = "5",
                name = "Hassan Jaber",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "King Saud University"
            ),
            Mentor(
                id = "6",
                name = "Maha Saeed",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "Kuwait University"
            ),
            Mentor(
                id = "7",
                name = "Yousef Nasser",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "Qatar University"
            ),
            Mentor(
                id = "8",
                name = "Fatima Abdullah",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "Al Ain University"
            ),
            Mentor(
                id = "9",
                name = "Ali Kareem",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "Lebanese American University"
            ),
            Mentor(
                id = "10",
                name = "Nour Hassan",
                imageUrl = getProfileImage(),
                rate = (0..10).random().toDouble(),
                numberReviewers = (1..500).random(),
                summaries = (10..30).random(),
                videos = (3..10).random(),
                meeting = (10..25).random(),
                subjects = generateSubjects(),
                university = "University of Sharjah"
            )
        )
    }

    override suspend fun getMentorDetails(id: String): Mentor {
        return generatorMentor().find { it.id == id } ?: throw Exception("empty details")
    }

    override suspend fun getSummaries(): List<Summaries> {
        return listOf(
            Summaries(
                chapterNumber = "Chapter 1",
                chapterDescription = "15 page (pdf)",
                piedPrice = "",
                isBuy = false
            ),
            Summaries(
                chapterNumber = "Chapter 2",
                chapterDescription = "15 page (pdf)",
                piedPrice = "",
                isBuy = true
            ),
            Summaries(
                chapterNumber = "Chapter 3",
                chapterDescription = "15 page (pdf)",
                piedPrice = "10$",
                isBuy = true
            ),
            Summaries(
                chapterNumber = "Chapter 4",
                chapterDescription = "15 page (pdf)",
                piedPrice = "",
                isBuy = false
            ),
            Summaries(
                chapterNumber = "Chapter 5",
                chapterDescription = "15 page (pdf)",
                piedPrice = "5$",
                isBuy = false
            ),
        )
    }

    override suspend fun getVideos(): List<Summaries> {
        return listOf(
            Summaries(
                chapterNumber = "Chapter 1",
                chapterDescription = "15 page (pdf)",
                piedPrice = "10$",
                isBuy = false
            ),
            Summaries(
                chapterNumber = "Chapter 5",
                chapterDescription = "11 page (pdf)",
                piedPrice = "5$",
                isBuy = true
            ),
            Summaries(
                chapterNumber = "Chapter 11",
                chapterDescription = "3 page (pdf)",
                piedPrice = "",
                isBuy = false
            )
        )
    }

    override suspend fun getUserData(): User {
        return User(
            username = "Ahmed",
            profilePictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGuH6Vo5XDGGvgriYJwqI9I8efWEOeVQrVTw&usqp=CAU",
            email = "ahmed@gmail.com",
            userId = "1"
        )
    }

    override suspend fun getMeeting(): List<Meeting> {
        return generateMeeting()
    }

    override suspend fun getSubject(): List<Subject> {
        return generateSubjects()
    }

    override suspend fun getSubjectById(id: String): Subject {
        return getSubject().find { it.id == id } ?: throw Exception("empty subject")
    }

    override suspend fun getUniversities(): List<University> {
        return generateUniversities()
    }

    override fun getUniversitiesNames(): List<String> {
        return generateUniversitiesNames()
    }

    override suspend fun getUniversityById(id: String): University {
        return generateUniversities().find { it.id == id }
            ?: throw Exception("empty university details")
    }

    override suspend fun getUpComingMeetings(): List<Meeting> {
        return generateMeeting()
    }


    override fun getAvailableTimeForMentor(mentorId: String): List<Date> {
        return getDates()
    }

    override suspend fun getFields(): List<String> {
        return generateFields()
    }

    override suspend fun getLevels(): List<Int> {
        return generateLevels()
    }

    //region Fake Data
    private fun generatorMentor(): List<Mentor> {
        val list = mutableListOf<Mentor>()
        for (i in 0..10) {
            list.add(
                Mentor(
                    id = "$i",
                    name = "Sara Ali",
                    imageUrl = getProfileImage(),
                    rate = (0..10).random().toDouble(),
                    numberReviewers = (1..500).random(),
                    summaries = 20 + i,
                    videos = 5 + i,
                    meeting = 19 +i,
                    subjects = generateSubjects(),
                    university = "University Baghdad"
                )
            )
        }
        return list
    }

    private fun getProfileImage(): String {
        val list = listOf(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGuH6Vo5XDGGvgriYJwqI9I8efWEOeVQrVTw&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_p4wGt_hng5BeADmgd6lf0wPrY6aOssc3RA&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTFiWs0Sx8Omxw_qamwrZT_EYTz27HulwjRBA&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRo5xoN3QF2DBxrVUq7FSxymtDoD3-_IW5CgQ&usqp=CAU"
        )

        return list.shuffled().first()
    }

    private fun generateSubjects(): List<Subject> {
        return listOf(
            Subject(
                id = "1",
                name = "Design Pattern",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors = listOf("1", "3", "5"),
                imageUrl = "https://sourcemaking.com/images/content-public/store/didp-cover-en.png"
            ), Subject(
                id = "2",
                name = "Data Structures",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors = listOf("1", "6", "2"),
                imageUrl = "https://d2sofvawe08yqg.cloudfront.net/cdatastructuresandalgorithms-secondedition/s_hero2x?1734966432"
            ),
            Subject(
                id = "3",
                name = "Algorithms",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors = listOf("4", "2", "1"),
                imageUrl = "https://media.s-bol.com/mZZWQW4NYZMG/550x677.jpg"
            ), Subject(
                id = "4",
                name = "Software Engineering",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors = listOf("1", "3", "5"),
                imageUrl = "https://img.perlego.com/book-covers/1573030/9781498705318.jpg"
            ),
            Subject(
                id = "5",
                name = "Database Systems",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors = listOf("1", "3", "8"),
                imageUrl = "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1395001597i/18264746.jpg"
            ), Subject(
                id = "6",
                name = "Web Development",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors = listOf("1", "3", "5"),
                imageUrl = "https://learning.lpi.org/en/learning-materials/030-100/cover-030-100.en_hu_9937dd77a1521ff1.png"
            ), Subject(
                id = "7",
                name = "Mobile App Development",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors =listOf("1","3","5"),
                imageUrl = "https://riseuplabs.com/wp-content/uploads/2021/07/mobile-app-development-lifecycle.jpg"
            ), Subject(
                id = "8",
                name = "Artificial Intelligence",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors = listOf("1", "3", "5"),
                imageUrl = "https://yes-pdf.com/storage/media/Fvj2qlHA0PQexhTks370zPqo4wlmiJryyAal0NIi.jpg"
            ), Subject(
                id = "9",
                name = "Machine Learning",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors = listOf("4", "1", "3", "5"),
                imageUrl = "https://newtondesk.com/wp-content/uploads/2022/09/Machine-learning-study-notes-pdf-tutorial.jpg"
            ), Subject(
                id = "10",
                name = "Computer Networks",
                mentorNumber = "4",
                summaryNumber = "10",
                videoNumber = "8",
                mentors = listOf("3", "1", "3", "5"),
                imageUrl = "https://i0.wp.com/bcanepaltu.com/wp-content/uploads/2021/05/COmputer-Networking.png"
            )
        )
    }

    private suspend fun generateUniversities(): List<University> {
        val list = mutableListOf<University>()
        for (i in 0..10) {
            list.add(
                University(
                    id = "$i",
                    name = "First Last $i",
                    imageUrl = getImage(),
                    address = "Seattle, Washington",
                    mentorNumber = "5",
                    summaryNumber = "15",
                    videoNumber = "1 $i",
                    subjects = getSubject(),
                    mentors = getMentors()
                )
            )
        }
        return list
    }

    private fun generateUniversitiesNames(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0..10) {
            list.add(
                "University $i"
            )
        }
        return list
    }

    private fun generateFields(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0..10) {
            list.add(
                "Field $i"
            )
        }
        return list
    }

    private fun generateLevels(): List<Int> {
        val list = mutableListOf<Int>()
        for (i in 0..10) {
            list.add(
                i
            )
        }
        return list
    }

    private fun getImage(): String {
        val list = listOf(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgorKUEVujUWNUHzI_fM_pQX2or-AiH6j29Q&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQANtG6UPPvIwDcLr4wpV4pt3ixtkv8eHjlKg&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTY-Fzwk77TGkO86UCbElFcSkqwx1DcSI_bwQ&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsxt4TE55zRBBGspJT4FAm_pi1ZfDbLXGPUA&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTY9goD6bOsmy-JWoW-4u44Dp8tyR2WwpKlSw&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQq6qXvZ7aaZxvL2diHXHJ47C8J7NDJ2SLXaQ&usqp=CAU",
        )

        return list.shuffled().first()
    }


    private fun generateMeeting(): List<Meeting> {
        val meetings = mutableListOf<Meeting>()
        val mentors = generatorMentor()
        val subject = generateSubjects()
        for (i in 0..5) {
            val mentorIndex = (0..mentors.lastIndex).random()
            val subjectIndex = (0..subject.lastIndex).random()

            meetings.add(
                Meeting(
                    id = "$i",
                    mentor = mentors[mentorIndex],
                    subject = subject[subjectIndex].name,
                    notes = "This meet to recap data structure from ch$i to ch ${i + 3}",
                    time = System.currentTimeMillis() + i * (30 * 60 * 1000),
                    isBook = i % 2 == 0,
                    price = if (i % 2 == 0) "" else "1$i"
                )
            )
        }
        return meetings
    }

    private fun getDates(): List<Date> {
        val list = mutableListOf<Date>()
        for (i in 0..10) {
            val day = getFormattedDate(addDays = i)
            val times = mutableListOf<Long>()
            for (j in 0..20 step 2) {
                times.add(getFormattedTime(j))
            }
            list.add(Date(day = day, times = times))
        }
        return list
    }

    private fun getFormattedDate(addDays: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, addDays)

        val dateFormat = SimpleDateFormat("EEE d, MMM yyyy", Locale.US)
        return dateFormat.format(currentDate.time)
    }

    private fun getFormattedTime(addHours: Int): Long {
        val currentTime = Calendar.getInstance()
        currentTime.add(Calendar.HOUR_OF_DAY, addHours)
        return currentTime.timeInMillis
    }

    //endregion
}
