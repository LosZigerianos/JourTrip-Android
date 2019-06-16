package com.zigerianos.jourtrip.auth

import com.zigerianos.jourtrip.PrefsManager
import com.zigerianos.jourtrip.data.entities.User

data class AuthManager(
    private val prefsManager: PrefsManager
) : IAuthManager {

    private var mUser: User? = prefsManager.get(PrefsManager.Keys.User, User::class.java, null)

    override fun getCurrentAccessToken(): String = mUser?.accessToken ?: ""

    override fun isUserSignedIn() : Boolean =  mUser?.accessToken?.isNotEmpty() ?: false

    fun getUserId() : String? = mUser?.id

    fun getUser() : User? = mUser

    fun addUser(user: User) {
        mUser = user
        saveUser()
    }

    fun updateUser(user: User) {
        mUser?.let {
            mUser = it.copy(
                id = user.id,
                fullname = user.fullname,
                username = user.username,
                email = user.email,
                creationDate = user.creationDate,
                updatedAt = user.updatedAt,
                photo = user.photo
            )
        }
    }

    fun addToken(token: String) {
        mUser?.accessToken = token
        saveUser()
    }

    fun deleteUser() {
        mUser = null
        prefsManager.remove(PrefsManager.Keys.User)
    }

    fun hasBiometricPermission() : Boolean = prefsManager.getBoolean(PrefsManager.Keys.HasBiometricPermission, false)

    fun addBiometricPermission(value: Boolean) {
        prefsManager.setBoolean(PrefsManager.Keys.HasBiometricPermission, value)
    }

    private fun saveUser() {
        mUser?.let {
            prefsManager.set(PrefsManager.Keys.User, it)
        }
    }
}