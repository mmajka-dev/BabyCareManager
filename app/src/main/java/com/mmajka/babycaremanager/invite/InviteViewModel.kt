package com.mmajka.babycaremanager.invite

import android.app.Application
import android.content.Intent
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmajka.babycaremanager.utils.Utils

class InviteViewModel(application: Application): AndroidViewModel(application) {
    val prefInstance = Utils(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")

    val id = MutableLiveData<String>()

    fun getCode(): LiveData<String> {
        id.postValue(path)
        return id
    }

    fun getShareIntent(view: TextView): Intent {

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").
        putExtra(Intent.EXTRA_TEXT, "Hi! You have been invited to use BabyCare app and take care together about child. Your code is: $path")
        return shareIntent
    }
}