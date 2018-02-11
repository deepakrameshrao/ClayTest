package com.deepak.clay.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableBoolean
import com.deepak.clay.BR

/**
 * Created by deepakrameshrao on 08/02/18.
 */
class Door(doorName: String?, var userList: String?) : BaseObservable() {

    @get: Bindable
    var doorName: String = doorName ?: ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.doorName)
        }

    var isAdmin = ObservableBoolean(false)

    fun checkForAdmin(isAdminUser: Int) {
        if (isAdminUser > 0) {
            isAdmin.set(true)
        }
    }
}