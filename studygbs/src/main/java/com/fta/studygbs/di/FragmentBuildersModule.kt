package com.fta.studygbs.di

import com.fta.studygbs.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment():SearchFragment
}