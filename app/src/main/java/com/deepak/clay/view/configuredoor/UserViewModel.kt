package com.deepak.clay.view.configuredoor

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableBoolean
import com.deepak.clay.BR

/**
 * Created by deepakrameshrao on 11/02/18.
 */
class UserViewModel(userName: String, var accessList: String, var userId: Int) : BaseObservable() {

    var switchCompat = ObservableBoolean(false)

    @get: Bindable
    var userNameTv = userName
        set(value) {
            field = value
            notifyPropertyChanged(BR.userNameTv)
        }

    fun checkSwitchToBeEnabled() {
        var accessArray: List<String> = accessList.split(",")
        for (access in accessArray) {
            if (access.contains(userId.toString())) {
                switchCompat.set(true)
            }
        }
    }

}