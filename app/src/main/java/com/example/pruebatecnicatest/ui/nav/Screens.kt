package com.example.pruebatecnicatest.ui.nav

sealed class Screens(val route: String) {
    object SettingsDetail : Screens("screens_detail")

    object Auth : Screens("auth")

    object Dashboard : Screens("dashboard")

    object Transactions: Screens("transactions")

    object Login: Screens("login")

    object DetailTransaction : Screens("transactions/{saved}/{id}")

    object SavePost: Screens("savePost")

    object Settings: Screens("settings")

    object Privacy: Screens("privacy")
}