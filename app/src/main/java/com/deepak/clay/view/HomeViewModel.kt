package com.deepak.clay.view

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import android.databinding.ObservableBoolean
import com.deepak.clay.view.LoginActivity.Companion.IS_LOGGED_IN_USER_ADMIN

/**
 * Created by deepakrameshrao on 09/02/18.
 */
class HomeViewModel : ViewModel() {

    var configureVisibility = ObservableBoolean(false)
    var accessLogs = ObservableBoolean(false)

    fun initialize(sharedpreference: SharedPreferences) {
        if(sharedpreference.getInt(IS_LOGGED_IN_USER_ADMIN, 0) == 1) {
            configureVisibility.set(true)
            accessLogs.set(true)
        } else {
            configureVisibility.set(false)
            accessLogs.set(false)
        }
    }
}