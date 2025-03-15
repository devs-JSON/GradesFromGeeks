package json.gradesfromgeeks.di

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import json.gradesfromgeeks.BuildConfig
import json.gradesfromgeeks.data.source.remote.service.GeminiApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val AiModel = module {
    single {
        GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = BuildConfig.API_KEY,
            generationConfig = generationConfig {
                temperature = 1f
                topK = 40
                topP = 0.95f
                maxOutputTokens = 8192
                responseMimeType = "text/plain"
            },
        )
    }
    singleOf(::GeminiApi)
}
