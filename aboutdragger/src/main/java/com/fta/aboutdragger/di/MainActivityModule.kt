package com.fta.aboutdragger.di

import com.fta.aboutdragger.view.activity.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindYourAndroidInjectorFactory(factory: MainActivitySubcomponent.Factory):AndroidInjector.Factory<*>
}