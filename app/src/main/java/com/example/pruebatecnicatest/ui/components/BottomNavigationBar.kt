package com.example.pruebatecnicatest.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.pruebatecnicatest.ui.nav.Screens

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val currentDestination = navController.currentBackStackEntry?.destination
    BottomAppBar(
        containerColor = Color.LightGray
    ){
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "posts") },
            selected = currentDestination?.route == Screens.Transactions.route,
            onClick = { navController.navigate(Screens.Transactions.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "localPosts") },
            selected = currentDestination?.route == Screens.SavePost.route,
            onClick = { navController.navigate(Screens.SavePost.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "settings") },
            selected = currentDestination?.route == Screens.Settings.route,
            onClick = { navController.navigate(Screens.Settings.route) }
        )
    }
}