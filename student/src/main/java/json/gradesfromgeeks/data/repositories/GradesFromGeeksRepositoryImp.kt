package json.gradesfromgeeks.data.repositories

import com.google.ai.client.generativeai.type.asTextOrNull
import json.gradesfromgeeks.data.source.BaseRepository
import json.gradesfromgeeks.data.source.remote.service.GeminiApi

class GradesFromGeeksRepositoryImp(
    private val geminiApi: GeminiApi,
) : BaseRepository(), GradesFromGeeksRepository {

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
//        return "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        return "https://vbcache1152.videobuster.de/clips/1uyTL32KI_M-Xml3RgwTIw/public/vod005/8bsus0u3w54/video-h264-1.mp4"
    }


}
