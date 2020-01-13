package com.fta.studygbs.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fta.studygbs.vo.Repo
import com.fta.studygbs.vo.RepoSearchResult
import com.fta.studygbs.vo.User

@Database(
    entities = [
        User::class,
        Repo::class,
        RepoSearchResult::class
    ], version = 1, exportSchema = true
)
abstract class GithubDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao
}