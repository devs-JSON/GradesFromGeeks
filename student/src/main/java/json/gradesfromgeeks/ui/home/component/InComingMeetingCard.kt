package json.gradesfromgeeks.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import json.gradesFromGeeks.design_system.components.GGButton
import json.gradesFromGeeks.design_system.components.GGTextChipStyle
import json.gradesFromGeeks.design_system.theme.Theme
import json.gradesfromgeeks.R
import json.gradesfromgeeks.ui.home.MeetingUiState
import json.gradesfromgeeks.utils.formatHoursMinutes
import json.gradesfromgeeks.utils.formatTimestamp

@Composable
fun InComingMeetingCard(
    meeting: MeetingUiState,
    modifier: Modifier = Modifier,
    onJoinClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Theme.colors.card, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = meeting.subject,
            style = Theme.typography.titleSmall,
            color = Theme.colors.primaryShadesDark
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GGTextChipStyle(meeting.time.formatTimestamp())
            GGTextChipStyle(meeting.mentorName)
        }
        Text(
            text = meeting.notes,
            maxLines = 2,
            style = Theme.typography.labelLarge
        )

        GGButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            title = if (meeting.enableJoin) {
                stringResource(id = R.string.join_now)
            } else {
                stringResource(R.string.join_after) + "(${meeting.reminder.formatHoursMinutes()})"
            },
            enabled = meeting.enableJoin,
            onClick = onJoinClicked
        )
    }
}
