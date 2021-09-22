package com.ceiba.login.infraestructure.repository

import androidx.lifecycle.MutableLiveData
import com.ceiba.login.domain.exception.InvalidDataException
import com.ceiba.login.domain.entity.User
import com.ceiba.login.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryFirebase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : UserRepository {

    private val userLiveData = MutableLiveData<FirebaseUser>()


    override fun logOut() = firebaseAuth.signOut()

    override suspend fun loginUser(user: User) {
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password).await()
        userLiveData.postValue(firebaseAuth.currentUser)
    }

    override suspend fun createUser(user: User) {
        val task = firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
        userLiveData.postValue(firebaseAuth.currentUser)
        if (task.user != null) {
            firestore.collection("users")
                .document(user.email)
                .set(user)
                .addOnFailureListener { error -> throw InvalidDataException(error.message) }
        }
    }

    override suspend fun createGuestUser(user: User) {
        //TODO SIGN IN GUEST USER
    }
}
