package com.example.user.injapanapp.app

import android.preference.PreferenceManager

class SharedPreferencesClass {
    companion object {

        fun saveStringInPreferences(saveString: String, token: String) {
            val context = ThisApplication.getInstance()
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(saveString, token)
            editor.apply()
        }

        fun getStringFromPreferences(tagString: String): String {
            var result: String? = ""
            val context = ThisApplication.getInstance()
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            if (preferences.contains(tagString)) {
                result = preferences.getString(tagString, null)
            }
            return if (result != null && result != "") {
                result
            } else "0"
        }

        fun saveBooleanInPreferences(saveString: String, boolean: Boolean) {
            val context = ThisApplication.getInstance()
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putBoolean(saveString, boolean)
            editor.apply()
        }

        fun getBooleanFromPreferences(tagString: String): Boolean {
            var result = false
            val context = ThisApplication.getInstance()
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            if (preferences.contains(tagString)) {
                result = preferences.getBoolean(tagString, false)
            }
            return result
        }

        fun deleteFromPrefs() {
            val context = ThisApplication.getInstance()
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.clear()
            editor.apply()
        }

        fun contains(saveStr: String): Boolean {
            val context = ThisApplication.getInstance()
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            if (preferences.contains(saveStr)) {
                val result = preferences.getString(saveStr, null)
                if (!result!!.isEmpty() || result != "") {
                    return true
                }
            }
            return false
        }
    }
}
