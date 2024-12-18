package com.example.pruebatecnicatest.ui.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pruebatecnicatest.ui.screens.LoginScreen
import com.example.pruebatecnicatest.ui.screens.PrivacyScreen
import com.example.pruebatecnicatest.ui.screens.SavePostsScreen
import com.example.pruebatecnicatest.ui.screens.SettingsDetailScreen
import com.example.pruebatecnicatest.ui.screens.SettingsScreen
import com.example.pruebatecnicatest.ui.screens.TransactionDetailScreen
import com.example.pruebatecnicatest.ui.screens.TransactionListScreen
import com.example.pruebatecnicatest.ui.viewmodel.TransactionViewModel

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.Auth.route) {
        navigation(startDestination = Screens.Login.route, route = Screens.Auth.route) {
            composable(Screens.Login.route) { navBackStackEntry ->
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("transactions")
                    }
                )
            }
        }
        navigation(startDestination = Screens.Transactions.route, route = Screens.Dashboard.route) {
            composable(Screens.Transactions.route) { navBackStackEntry ->
                TransactionListScreen(
                    navController = navController,
                    viewModel = navBackStackEntry.transactionListViewModel(navController)
                )
            }

            composable(
                route = Screens.DetailTransaction.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType})
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: throw Exception("Missing id")
                TransactionDetailScreen(
                    navController = navController,
                    id = id,
                    viewModel = navBackStackEntry.transactionListViewModel(navController)
                )
            }

            composable(route = Screens.SavePost.route){ navBackStackEntry ->
                SavePostsScreen(
                    viewModel = navBackStackEntry.transactionListViewModel(navController),
                    navController = navController
                )
            }
            composable(route = Screens.Settings.route){ navBackStackEntry ->
                SettingsScreen(
                    navController = navController,
                    viewModel = navBackStackEntry.transactionListViewModel(navController)
                )
            }
            composable(route = Screens.Privacy.route){ navBackStackEntry ->
                PrivacyScreen(
                    navController = navController
                )
            }
            composable(route = Screens.SettingsDetail.route){ navBackStackEntry ->
                SettingsDetailScreen(
                    navController = navController,
                    viewModel = navBackStackEntry.transactionListViewModel(navController)
                )
            }
        }
    }
}

/**
 * Function to instantiate viewmodel, passing route in case it exists.
 */
@Composable
fun NavBackStackEntry.transactionListViewModel(navController: NavController): TransactionViewModel {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}