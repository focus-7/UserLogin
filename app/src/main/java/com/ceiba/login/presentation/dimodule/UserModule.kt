package com.ceiba.login.presentation.dimodule

import com.ceiba.login.domain.repository.UserRepository
import com.ceiba.login.infraestructure.repository.UserRepositoryFirebase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class UserModule {
    @Binds
    abstract fun bindUserRepository(userRepositoryFirebase: UserRepositoryFirebase): UserRepository
}