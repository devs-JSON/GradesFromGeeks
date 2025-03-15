package json.gradesfromgeeks.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import json.gradesFromGeeks.design_system.theme.GGTheme
import json.gradesfromgeeks.ui.main.navigation.Screen
import json.gradesfromgeeks.ui.main.navigation.graph.RootNavGraph

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun App() {
     GGTheme() {
            CompositionLocalProvider() {
                val navController = rememberNavController()
                RootNavGraph(navController = navController, startDestination = Screen.Test)
            }
    }
}
