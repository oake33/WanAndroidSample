package com.jjf.template.di

import com.jjf.template.di.scope.ActivityScoped
import com.jjf.template.ui.HomeActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author xj
 * date: 18-11-7
 * description :
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

}
