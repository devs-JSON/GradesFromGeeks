package json.gradesfromgeeks.di

import json.gradesfromgeeks.ui.chat.ChatBotViewModel
import json.gradesfromgeeks.ui.downloads.DownloadsViewModel
import json.gradesfromgeeks.ui.individualMeeting.IndividualMeetingViewModel
import json.gradesfromgeeks.ui.main.AppViewModel
import json.gradesfromgeeks.ui.notification.NotificationsViewModel
import json.gradesfromgeeks.ui.profile.ProfileViewModel
import json.gradesfromgeeks.ui.review.ReviewViewModel
import json.gradesfromgeeks.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::ChatBotViewModel)
    viewModelOf(::IndividualMeetingViewModel)
    viewModelOf(::NotificationsViewModel)
    viewModelOf(::DownloadsViewModel)
    viewModelOf(::ReviewViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::AppViewModel)
}
