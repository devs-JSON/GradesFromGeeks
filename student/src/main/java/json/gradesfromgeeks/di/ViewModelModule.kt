package json.gradesfromgeeks.di

import json.gradesfromgeeks.ui.chat.ChatBotViewModel
import json.gradesfromgeeks.ui.downloads.DownloadsViewModel
import json.gradesfromgeeks.ui.individualMeeting.IndividualMeetingViewModel
import json.gradesfromgeeks.ui.review.ReviewViewModel
import json.gradesfromgeeks.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::ChatBotViewModel)
    viewModelOf(::ReviewViewModel)
    viewModelOf(::DownloadsViewModel)
    viewModelOf(::IndividualMeetingViewModel)
    viewModelOf(::SearchViewModel)
}
