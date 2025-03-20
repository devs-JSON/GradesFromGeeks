package json.gradesfromgeeks.ui.mentor

import json.gradesfromgeeks.data.entity.Meeting
import json.gradesfromgeeks.data.entity.Mentor
import json.gradesfromgeeks.data.entity.Summaries
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import json.gradesfromgeeks.ui.sharedState.toListUIState
import json.gradesfromgeeks.ui.sharedState.toMeetingListUIState
import json.gradesfromgeeks.ui.sharedState.toUIState
import kotlinx.coroutines.delay

class MentorViewModel(
    private val id: String,
    private val ggRepository: GradesFromGeeksRepository
) : BaseViewModel<MentorUIState, MentorUIEffect>(MentorUIState()) {

    init {
        getDataOfMentor()
    }

    private fun getDataOfMentor() {
        updateState { it.copy(isLoading = true) }
        onGetMentorDetails()
        getSummariesOfMentor()
        getVideosOfMentor()
        getMeetingOfMentor()
    }

    private fun onGetMentorDetails() {
        tryToExecute(
            {
                delay(1000)
                ggRepository.getMentorDetails(id)
            },
            ::onGetMentorDetailsSuccess,
            ::onError
        )
    }

    private fun onGetMentorDetailsSuccess(mentor: Mentor) {
        updateState {
            it.copy(
                isSuccess = true,
                isError = false,
                isLoading = false,
                mentorDetailsUIState = mentor.toUIState()
            )
        }
    }

    private fun getSummariesOfMentor() {
        tryToExecute(
            ggRepository::getSummaries,
            ::onGetSummariseSuccess,
            ::onError
        )
    }

    private fun onGetSummariseSuccess(summaries: List<Summaries>) {
        updateState {
            it.copy(
                mentorSummariseList = summaries.filter { !it.isBuy }.toListUIState()
            )
        }
    }

    private fun getVideosOfMentor() {
        tryToExecute(
            ggRepository::getVideos,
            ::onGetVideoSuccess,
            ::onError
        )
    }

    private fun onGetVideoSuccess(video: List<Summaries>) {
        updateState {
            it.copy(
                mentorVideoList = video.filter { !it.isBuy }.toListUIState()
            )
        }
    }

    private fun getMeetingOfMentor() {
        tryToExecute(
            ggRepository::getMeeting,
            ::onGetMeetingSuccess,
            ::onError
        )
    }

    private fun onGetMeetingSuccess(meeting: List<Meeting>) {
        updateState {
            it.copy(
                mentorMeetingList = meeting.filter { item -> !item.isBook }.toMeetingListUIState()
            )
        }
    }

    private fun onError() {
        updateState {
            MentorUIState(
                isError = true,
                isLoading = false,
                isSuccess = false
            )
        }
        sendNewEffect(MentorUIEffect.MentorError)
    }

}
