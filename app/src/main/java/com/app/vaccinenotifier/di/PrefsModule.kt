package com.app.vaccinenotifier.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class PrefsModule(val context: Context) {

    @Provides
    fun provideSharedPref(): SharedPreferences {
        return context.getSharedPreferences("settings", 0)
    }
}