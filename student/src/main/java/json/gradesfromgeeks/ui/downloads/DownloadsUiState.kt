package json.gradesfromgeeks.ui.downloads

import json.gradesfromgeeks.data.entity.Download
import json.gradesfromgeeks.ui.home.SubjectDetailsUiState
import json.gradesfromgeeks.ui.home.toSubjectUiState
import json.gradesfromgeeks.ui.sharedState.MeetingUIState
import json.gradesfromgeeks.ui.sharedState.SummeryDetailsUIState
import json.gradesfromgeeks.ui.sharedState.toListUIState
import json.gradesfromgeeks.ui.sharedState.toMeetingListUIState

data class DownloadsUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val downloadDetails: DownloadDetailsUIState = DownloadDetailsUIState(),
)

data class DownloadDetailsUIState(
    val summaryNumber: String = "",
    val videoNumber: String = "",
    val meetingNumber: String = "",
    val subjectList: List<SubjectDetailsUiState> = emptyList(),
    val summaryList: List<SummeryDetailsUIState> = emptyList(),
    val videoList: List<SummeryDetailsUIState> = emptyList(),
    val meetingList: List<MeetingUIState> = emptyList()
)

fun Download.toUIState(): DownloadDetailsUIState {
    return DownloadDetailsUIState(
        summaryNumber = summariesNumber,
        videoNumber = videoNumber,
        meetingNumber = meetingNumber,
        subjectList = subjects.toSubjectUiState(),
        summaryList = summaries.filter { it.isBuy }.toListUIState(),
        videoList = video.filter { it.isBuy }.toListUIState(),
        meetingList = meeting.filter { it.isBook }.toMeetingListUIState()
    )
}