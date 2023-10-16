package com.example.templateapplication

import androidx.annotation.StringRes

enum class NavigationRoutes(@StringRes val title: Int) {
    home(title = R.string.home_title),
    over(title = R.string.over_title)
}