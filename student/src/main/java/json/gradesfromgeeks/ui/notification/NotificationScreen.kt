package json.gradesfromgeeks.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import json.gradesFromGeeks.design_system.components.GGAppBar
import json.gradesFromGeeks.design_system.components.Loading
import json.gradesFromGeeks.design_system.theme.Theme
import json.gradesfromgeeks.R
import json.gradesfromgeeks.ui.search.composable.EmptyScreenItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationScreen(
    onNavigateBack: () -> Unit,
    viewModel: NotificationsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    NotificationContent(
            state = state,
            onClickBack =onNavigateBack,
        onNotificationClick = {},
    )
}

@Composable
private fun NotificationContent(
    state: NotificationsUIState,
    onClickBack: () -> Unit,
    onNotificationClick: (NotificationState) -> Unit,
) {
    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
    ) {
        GGAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Notification",
                onBack = onClickBack
        )

        if (state.isLoading) {
            Loading()
        } else if (state.notifications.isEmpty()) {
            EmptyScreenItem(
                    iconRes = R.drawable.notification,
                    title = stringResource(R.string.nothing_to_show),
                    description = stringResource(R.string.do_some_active_to_have_notification)
            )
        }
        LazyColumn(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.notifications) {
                NotificationItem(
                        notification = it,
                        onNotificationClick
                )
            }
        }

    }
}