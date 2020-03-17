package com.slavetny.ampersand

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class NotesApp : Application() {

    private val DARKMODE = "dark_mode"

    override fun onCreate() {
        super.onCreate()
    }

    fun get(): NotesApp? {
        var instance = NotesApp()

        return instance
    }

    fun setTheme(context: Context, url: Boolean) {
        var sPref = context.getSharedPreferences(DARKMODE, Context.MODE_PRIVATE)

        val ed: SharedPreferences.Editor? = sPref?.edit()

        ed?.putBoolean(DARKMODE, url)
        ed?.commit()
    }

    fun getTheme(context: Context): Boolean? {
        var sPref = context.getSharedPreferences(DARKMODE, Context.MODE_PRIVATE)

        var access = sPref?.getBoolean(DARKMODE, false)

        return access
    }
}