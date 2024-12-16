package com.example.pruebatecnicatest.ui.nav

sealed class Screens(val route: String) {
    object Auth : Screens("auth")

    object Dashboard : Screens("dashboard")

    object Transactions: Screens("transactions")

    object Login: Screens("login")

    object DetailTransaction : Screens("transactions/{id}")
}