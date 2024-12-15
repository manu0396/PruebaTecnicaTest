package com.example.pruebatecnicatest.ui.nav

sealed class Screens(val route: String) {
    object Auth : Screens("auth")

    object Dashboard : Screens("dashboard")

    object Home: Screens("home")

    object Login: Screens("login")
}