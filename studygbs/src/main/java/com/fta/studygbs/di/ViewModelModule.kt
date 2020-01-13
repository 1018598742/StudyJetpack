package com.fta.studygbs.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fta.studygbs.ui.search.SearchViewModel
import com.fta.studygbs.viewmodel.GithubViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}