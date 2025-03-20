package json.gradesfromgeeks.data.source.remote.service

import json.gradesfromgeeks.data.entity.HuggingFaceRequest
import json.gradesfromgeeks.data.entity.HuggingFaceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HuggingFaceApiService {
    @POST("models/deepset/roberta-base-squad2")
    suspend fun documentQuestionAnswering(
        @Body payload: HuggingFaceRequest
    ): Response<HuggingFaceResponse>
}