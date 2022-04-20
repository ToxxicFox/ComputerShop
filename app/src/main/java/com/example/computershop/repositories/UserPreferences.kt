package com.example.computershop.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore:
        DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences (
    context: Context
){

    private val appContext = context.applicationContext

    val authToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    suspend fun saveAuthToken(authToken: String) {
        appContext.dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    companion object {
        private val KEY_AUTH = stringPreferencesKey("auth_key")
    }

}