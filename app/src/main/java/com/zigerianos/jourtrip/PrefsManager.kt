package com.zigerianos.jourtrip

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.preference.PreferenceManager
import org.joda.time.DateTime

@SuppressLint("CommitPrefEdits")
class PrefsManager(context: Context) : ContextWrapper(context) {
    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
    private val sharedPreferencesEditor by lazy { sharedPreferences.edit() }

    enum class Keys(val value: String) {
        AccessToken("AccessToken"),
        ApplicationLanguage("ApplicationLanguage"),
        User("User"),
        HasBiometricPermission("HasBiometricPermission")
    }

    fun setString(key: Keys, value: String) {
        sharedPreferencesEditor.putString(key.toString(), value)
        sharedPreferencesEditor.commit()
    }

    fun getString(key: Keys, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key.toString(), defaultValue)
    }

    fun setInt(key: Keys, value: Int) {
        sharedPreferencesEditor.putInt(key.toString(), value)
        sharedPreferencesEditor.commit()
    }

    fun getInt(key: Keys, defaultValue: Int = -1): Int {
        return sharedPreferences.getInt(key.toString(), defaultValue)
    }

    fun setLong(key: Keys, value: Long) {
        sharedPreferencesEditor.putLong(key.toString(), value)
        sharedPreferencesEditor.commit()
    }

    fun getLong(key: Keys, defaultValue: Long = -1): Long {
        return sharedPreferences.getLong(key.toString(), defaultValue)
    }

    fun setBoolean(key: Keys, value: Boolean) {
        sharedPreferencesEditor.putBoolean(key.toString(), value)
        sharedPreferencesEditor.commit()
    }

    fun getBoolean(key: Keys, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key.toString(), defaultValue)
    }

    fun setDate(key: Keys, value: DateTime?) {
        var valueConverted: Long = 0
        if (value != null) {
            valueConverted = value.millis
        }

        sharedPreferencesEditor.putLong(key.toString(), valueConverted)
        sharedPreferencesEditor.commit()
    }

    fun getDate(key: Keys, defaultValue: DateTime? = null): DateTime? {
        val value = sharedPreferences.getLong(key.toString(), 0)
        return if (value != 0L) DateTime(value) else defaultValue
    }

    operator fun set(key: Keys, value: Any) {
        sharedPreferencesEditor.putString(key.toString(), BaseApp.get(applicationContext).getGson().toJson(value))
        sharedPreferencesEditor.commit()
    }

    operator fun <T> get(key: Keys, cls: Class<T>, defaultValue: T? = null): T? {
        val value = sharedPreferences.getString(key.toString(), null)
        return if (value.isNullOrEmpty()) {
            defaultValue
        } else {
            BaseApp.get(applicationContext).getGson().fromJson(value, cls)
        }
    }

    fun remove(key: Keys) {
        sharedPreferencesEditor.remove(key.toString())
        sharedPreferencesEditor.commit()
    }

    fun clear() {
        sharedPreferencesEditor.clear()
        sharedPreferencesEditor.commit()
    }
}