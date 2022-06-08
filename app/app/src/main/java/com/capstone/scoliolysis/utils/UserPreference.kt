package com.capstone.scoliolysis.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.scoliolysis.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[ACCESS_TOKEN].toString(),
                preferences[REFRESH_TOKEN].toString(),
                preferences[USER_ID].toString(),
                preferences[EMAIL].toString(),
                preferences[ISLOGIN] ?: false
            )
        }
    }

    suspend fun setUser(user: User) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = user.accessToken
            preferences[REFRESH_TOKEN] = user.refreshToken
            preferences[USER_ID] = user.userId
            preferences[EMAIL] = user.email
            preferences[ISLOGIN] = true
        }
    }

    suspend fun logOutUser() {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = ""
            preferences[REFRESH_TOKEN] = ""
            preferences[USER_ID] = ""
            preferences[EMAIL] = ""
            preferences[ISLOGIN] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USER_ID = stringPreferencesKey("userid")
        private val EMAIL = stringPreferencesKey("email")
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val ISLOGIN = booleanPreferencesKey("is_login")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}