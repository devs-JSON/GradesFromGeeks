package json.gradesfromgeeks.di

import json.gradesfromgeeks.ui.chat.ChatBotViewModel
import json.gradesfromgeeks.ui.review.ReviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::ChatBotViewModel)
    viewModelOf(::ReviewViewModel)
}
