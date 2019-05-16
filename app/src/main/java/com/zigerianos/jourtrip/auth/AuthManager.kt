package com.zigerianos.jourtrip.auth

import com.zigerianos.jourtrip.PrefsManager
import com.zigerianos.jourtrip.data.entities.User

data class AuthManager(
    private val prefsManager: PrefsManager
) : IAuthManager {
    override fun getCurrentAccessToken(): String? = mUser?.accessToken

    private var mUser: User? = prefsManager.get(PrefsManager.Keys.User, User::class.java, null)

    override fun isUserSignedIn() : Boolean {
        mUser?.let { user ->
            return user.accessToken.isNotEmpty()
        }

        return false
    }

    fun getUser() : User? = mUser

    fun addUser(user: User) {
        mUser = user
        saveUser()
    }

    fun addToken(token: String) {
        mUser?.accessToken = token
    }

    fun deleteUser() {
        mUser = null
        prefsManager.remove(PrefsManager.Keys.User)
    }

    private fun saveUser() {
        mUser?.let {
            prefsManager.set(PrefsManager.Keys.User, it)
        }
    }
}