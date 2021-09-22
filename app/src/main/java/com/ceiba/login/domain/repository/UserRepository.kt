package com.ceiba.login.domain.repository

import com.ceiba.login.domain.entity.User

interface UserRepository {
    suspend fun loginUser(user: User)
    suspend fun createUser(user: User)
    suspend fun createGuestUser(user: User)
    fun logOut()
}