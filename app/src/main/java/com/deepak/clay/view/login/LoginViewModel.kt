package com.deepak.clay.view.login

import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.text.Editable

/**
 * Created by deepakrameshrao on 07/02/18.
 */
class LoginViewModel : ViewModel() {

    var userName = ObservableField("")
    var password = ObservableField("")
    var loginButtonEnabled = ObservableBoolean(false)
    var userNameFilled: Boolean = false
    var passwordFilled: Boolean = false

    fun getusertext(editable: Editable) {
        userNameFilled = editable.toString().isNotEmpty()
        loginButtonEnabled.set(userNameFilled && passwordFilled)
    }

    fun getpasswordtext(editable: Editable) {
        passwordFilled = editable.toString().isNotEmpty()
        loginButtonEnabled.set(userNameFilled && passwordFilled)
    }

    fun isLoggedIn(sharedPreferences: SharedPreferences) : Boolean {
        return (sharedPreferences.getInt(LoginActivity.LOGGED_IN_USER_ID, 0) > 0)
    }
}