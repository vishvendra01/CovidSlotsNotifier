package com.app.vaccinenotifier.di

import com.app.vaccinenotifier.data.executor.BaseSchedulerProvider
import com.app.vaccinenotifier.data.executor.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
interface SchedulerModule {
    @Binds
    fun binds(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}