package com.example.templateapplication

import androidx.annotation.StringRes

enum class NavigationRoutes(@StringRes val title: Int) {
    home(title = R.string.home_title),
    over(title = R.string.over_title),
    gegevens(title = R.string.gegevens_title),
    formules(title = R.string.formules_title),
    emailInfo(title = R.string.over_email)
}