package com.example.pruebatecnicatest.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.pruebatecnicatest.ui.screens.LoginScreen
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
        navigation(startDestination = Screens.Home.route, route = Screens.Dashboard.route) {
            composable("transactions") { navBackStackEntry ->
                TransactionListScreen(
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