package com.fta.aboutdragger.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class User(private val name: String) {

    @Inject
    constructor() : this(
        "Bob"
    )

    fun getName():String{
        return name
    }
}