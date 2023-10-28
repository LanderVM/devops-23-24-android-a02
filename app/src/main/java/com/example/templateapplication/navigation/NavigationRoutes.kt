package com.example.templateapplication.navigation

import android.content.Context
import androidx.annotation.StringRes
import com.example.templateapplication.R

enum class NavigationRoutes(@StringRes val title: Int) {
    home(title = R.string.home_title),
    over(title = R.string.over_title),
    contactGegevens(title = R.string.contact_gegevens_title),
    formules(title = R.string.formules_title),
    emailInfo(title = R.string.over_email),
    evenementGegevens(title=R.string.evenement_gegevens_titel),
    samenvattingGegevens(title=R.string.samenvatting_gegevens_titel),
    extras(title=R.string.extras_title);

    fun getString(context: Context): String {
        return context.getString(title)
    }
}
    
