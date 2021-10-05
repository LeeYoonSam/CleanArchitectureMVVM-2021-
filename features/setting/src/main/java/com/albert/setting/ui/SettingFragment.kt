package com.albert.setting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.albert.setting.ContributorScreen
import com.albert.setting.Route
import com.albert.setting.ScreenAction
import com.albert.setting.SettingScreen
import com.albert.shared.model.User
import com.albert.shared.result.Result
import com.albert.shared.result.data
import com.albert.ui_core_compose.layout.FullScreenLoading
import com.albert.ui_core_compose.layout.LoadingContent
import com.albert.ui_core_compose.setThemeContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return setThemeContent {
            SettingContainer()
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun SettingContainer(
    viewModel: SettingViewModel = viewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Setting.destination) {
        composable(Route.Setting.destination) {
            SettingScreen { action ->
                val route = when (action) {
                    ScreenAction.Speaker -> Route.Speaker
                    ScreenAction.Contributor -> Route.Contributor
                    ScreenAction.Staff -> Route.Staff
                }
                navController.navigate(route.destination)
            }
        }

        composable(Route.Speaker.destination) {
            val result by viewModel.speakers.observeAsState()
            LoadingContent(
                loading = result is Result.Loading,
                loadingContent = { FullScreenLoading()}
            ) {
                SpeakerScreen(speakers = result?.data.orEmpty())
            }
        }

        composable(Route.Contributor.destination) {
            val result by viewModel.contributors.observeAsState()
            LoadingContent(
                loading = result is Result.Loading,
                loadingContent = { FullScreenLoading()}
            ) {
                ContributorScreen(users = result?.data.orEmpty())
            }
        }

        composable(Route.Staff.destination) {
            val result by viewModel.staff.observeAsState()
            LoadingContent(
                loading = result is Result.Loading,
                loadingContent = { FullScreenLoading()}
            ) {
                StaffScreen(staffs = result?.data.orEmpty())
            }
        }
    }
}
