package com.ceiba.login.domain.services

import com.ceiba.login.domain.entity.User
import com.ceiba.login.domain.repository.UserRepository
import javax.inject.Inject

class UserService @Inject constructor(private val userRepository: UserRepository) {

    suspend fun createUser(user: User) {
        userRepository.createUser(user = user)
    }

    suspend fun createGuestUser(user: User) {
        userRepository.createGuestUser(user = user)
    }

    suspend fun loginUser(user: User) {
        userRepository.loginUser(user = user)
    }

    fun logOutUser() {
        userRepository.logOut()
    }

}