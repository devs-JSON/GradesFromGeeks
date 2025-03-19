package json.gradesfromgeeks.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.compose.rememberNavController
import json.gradesFromGeeks.design_system.theme.GGTheme
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.navigation.graph.RootNavGraph
import json.gradesfromgeeks.ui.main.screen.AppViewModel
import json.gradesfromgeeks.ui.utils.updateLanguage
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun App(viewModel: AppViewModel = koinViewModel()) {
    val firstTime by viewModel.isFirstTimeOpenApp.collectAsState()
    val language by viewModel.language.collectAsState()
    val isLogin by viewModel.isLogin.collectAsState()
    val isDarkTheme by viewModel.theme.collectAsState()

    language?.let {
        updateLanguage(context = LocalContext.current, language = it.abbreviation)
        GGTheme(isDarkTheme = isDarkTheme ?: false) {
            CompositionLocalProvider(LocalLayoutDirection provides it.layoutDirection) {
                val navController = rememberNavController()

                RootNavGraph(navController = navController, startDestination = Screen.Main)

            }
        }
    }
}
