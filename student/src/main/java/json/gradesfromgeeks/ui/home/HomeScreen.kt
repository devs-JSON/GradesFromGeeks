package json.gradesfromgeeks.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import json.gradesFromGeeks.design_system.components.GGMentor
import json.gradesFromGeeks.design_system.components.GGSubject
import json.gradesFromGeeks.design_system.components.GGTitleWithSeeAll
import json.gradesFromGeeks.design_system.components.GGUniversity
import json.gradesFromGeeks.design_system.theme.GGTheme
import json.gradesFromGeeks.design_system.theme.Theme
import json.gradesfromgeeks.R
import json.gradesfromgeeks.ui.home.component.ChatBotButton
import json.gradesfromgeeks.ui.home.component.HomeAppBar
import json.gradesfromgeeks.ui.home.component.InComingMeetingCard
import json.gradesfromgeeks.ui.sharedState.showSeeAll

@Composable
fun HomeScreen(
) {

    HomeContent(
        state = HomeUIState(),
        onNavigateToSubjectScreen = {},
        onNavigateToSeeALLUniversities = {},
        onNavigateToChatBot = {},
        onNavigateToMentorProfile = {},
        onNavigateToNotification = {},
        onNavigateToSeeAllMentors = {},
        onNavigateToSeeALLSubjects = {},
        onNavigateToUniversityProfile = {}
    )
}


@Composable
private fun HomeContent(
    state: HomeUIState,
    onNavigateToSeeAllMentors: () -> Unit,
    onNavigateToChatBot: () -> Unit,
    onNavigateToMentorProfile: (String) -> Unit,
    onNavigateToSubjectScreen: (String) -> Unit,
    onNavigateToUniversityProfile: (String) -> Unit,
    onNavigateToSeeALLUniversities: () -> Unit,
    onNavigateToSeeALLSubjects: () -> Unit,
    onNavigateToNotification: () -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        HomeAppBar(
            modifier = Modifier.fillMaxWidth(),
            onNotificationClicked = onNavigateToNotification
        )

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
            ) {

                ChatBotButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = onNavigateToChatBot
                )

                state.upComingMeetings.forEach { meeting ->
                    InComingMeetingCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        meeting = meeting,
                        onJoinClicked = {}
                    )
                }

                GGTitleWithSeeAll(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .padding(horizontal = 16.dp),
                    title = stringResource(id = R.string.mentors),
                    showSeeAll = state.mentors.showSeeAll(),
                    onClick = onNavigateToSeeAllMentors
                )

                state.mentors.take(3).forEach { mentor ->
                    GGMentor(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .padding(horizontal = 16.dp),
                        name = mentor.name,
                        rate = mentor.rate,
                        numberReviewers = mentor.numberReviewers,
                        profileUrl = mentor.imageUrl,
                        onClick = { onNavigateToMentorProfile(mentor.id) }
                    )
                }

                GGTitleWithSeeAll(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 10.dp)
                        .padding(horizontal = 16.dp),
                    title = stringResource(id = R.string.subjects),
                    showSeeAll = state.subjects.showSeeAll(),
                    onClick = onNavigateToSeeALLSubjects
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(state.subjects) { subject ->
                        GGSubject(
                            modifier = Modifier.width(100.dp),
                            name = subject.name,
                            onClick = { onNavigateToSubjectScreen(subject.id) }
                        )
                    }
                }

                if (state.university.isNotEmpty()) {
                    GGTitleWithSeeAll(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 10.dp)
                            .padding(horizontal = 16.dp),
                        title = stringResource(id = R.string.universities),
                        showSeeAll = state.university.showSeeAll(),
                        onClick = onNavigateToSeeALLUniversities
                    )
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(state.university) { university ->
                        GGUniversity(
                            modifier = Modifier.size(height = 215.dp, width = 322.dp),
                            name = university.name,
                            address = university.address,
                            imageUrl = university.imageUrl,
                            onClick = { onNavigateToUniversityProfile(university.id) }
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPrev() {
    GGTheme {
        HomeContent(
            state = HomeUIState(),
            onNavigateToSubjectScreen = {},
            onNavigateToSeeALLUniversities = {},
            onNavigateToChatBot = {},
            onNavigateToMentorProfile = {},
            onNavigateToNotification = {},
            onNavigateToSeeAllMentors = {},
            onNavigateToSeeALLSubjects = {},
            onNavigateToUniversityProfile = {}
        )
    }
}


