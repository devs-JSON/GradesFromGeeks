package json.gradesfromgeeks.data.repositories

import com.google.ai.client.generativeai.type.asTextOrNull
import json.gradesfromgeeks.data.entity.HuggingFaceRequest
import json.gradesfromgeeks.data.entity.InputData
import json.gradesfromgeeks.data.source.BaseRepository
import json.gradesfromgeeks.data.source.remote.service.GeminiApi
import json.gradesfromgeeks.data.source.remote.service.HuggingFaceApiService

class GradesFromGeeksRepositoryImp(
    private val geminiApi: GeminiApi,
    private val huggingFaceApi: HuggingFaceApiService
) : BaseRepository(), GradesFromGeeksRepository {

    override suspend fun queryDocument(context: String, question: String): String {
        val response = huggingFaceApi.documentQuestionAnswering(
            HuggingFaceRequest(InputData(question, context))
        )

        return if (response.isSuccessful) {
            response.body()?.answer ?: "No answer found"
        } else {
            "Error: ${response.errorBody()?.string()}"
        }
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
}
