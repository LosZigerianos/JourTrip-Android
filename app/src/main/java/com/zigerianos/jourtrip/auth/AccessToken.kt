package com.zigerianos.jourtrip.auth

import org.joda.time.DateTime

data class AccessToken(
    private var accessToken: String = "",
    private var tokenType: String = "",
    private var grantType: String = "",
    private var requestedAt: Long = 0
) {

    /*@SerializedName("expires_in")
    val expiringSeconds: Int = 0
    val refreshToken: String? = null*/

    /*val isValid: Boolean
        get() = getRequestedAt().plusSeconds(expiringSeconds).isAfterNow*/

    val isForUser: Boolean
        get() =
            GRANT_TYPE_RESOURCE_OWNER_PASSWORD == grantType || GRANT_TYPE_SOCIAL == grantType

    val authorizationHeader: String
        get() = getTokenType() + " " + accessToken

    init {
        requestedAt = DateTime.now().millis
    }

    fun getTokenType(): String {
        if (!Character.isUpperCase(tokenType[0])) {
            tokenType = Character.toString(tokenType[0]).toUpperCase() + tokenType.substring(1)
        }

        return tokenType
    }

    fun getRequestedAt(): DateTime {
        return DateTime(requestedAt)
    }

    /*fun canBeRefreshed(): Boolean {
        return refreshToken != null && !refreshToken.isEmpty()
    }*/

    companion object {
        const val GRANT_TYPE_RESOURCE_OWNER_PASSWORD = "password"
        const val GRANT_TYPE_SOCIAL = "social"
        //const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    }
}