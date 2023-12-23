package com.example.templateapplication.navigation

import android.content.Context
import androidx.annotation.StringRes
import com.example.templateapplication.R

enum class NavigationRoutes(@StringRes val title: Int) {
    Home(title = R.string.home_title),
    About(title = R.string.about_title),
    ContactDetails(title = R.string.contactDetails_title),
    Formulas(title = R.string.formulas_title),
    EventDetails(title = R.string.eventDetails_title),
    SummaryData(title = R.string.summaryData_title),
    ExtraItems(title = R.string.extraItems_title),
    GuidePrice(title = R.string.guidePrice_title),
    ExtrasOverview(title = R.string.extrasOverview_title);

    fun getString(context: Context): String {
        return context.getString(title)
    }
}
