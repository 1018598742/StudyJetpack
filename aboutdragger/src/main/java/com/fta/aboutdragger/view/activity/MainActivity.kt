package com.fta.aboutdragger.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fta.aboutdragger.R
import com.fta.aboutdragger.domain.User
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = user.getName()
        Log.i("Main_Name", "MainActivity-onCreate: ${name}");
    }
}
