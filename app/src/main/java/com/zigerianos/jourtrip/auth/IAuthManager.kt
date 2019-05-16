package com.zigerianos.jourtrip.auth

interface IAuthManager {

    fun getCurrentAccessToken(): String?

    fun isUserSignedIn(): Boolean

    //fun getValidAccessToken(): AccessToken

    //fun refreshAccessToken(): AccessToken

    /*fun signIn(username: String, password: String, callback: AccessTokenCallback)

    fun signIn(socialProvider: SocialProvider, accessToken: String, callback: AccessTokenCallback)

    fun signOut()*/

}