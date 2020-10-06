package com.mmajka.babycaremanager.utils

import android.content.Context

class Utils(context: Context) {
    private var PRIVATE_MODE = 0
    private val PREFERENCE_NAME = "ID_VALUE"
    private val ID = "ID"

    val preference = context!!.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
}