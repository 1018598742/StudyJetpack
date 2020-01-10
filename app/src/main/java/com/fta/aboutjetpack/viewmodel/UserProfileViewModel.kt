package com.fta.aboutjetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.fta.aboutjetpack.domain.User

class UserProfileViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val userId: String =
        savedStateHandle["uid"] ?: throw IllegalArgumentException("missing user id")
    val user: LiveData<User> = TODO()
}