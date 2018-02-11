package com.deepak.clay.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.deepak.clay.BR

/**
 * Created by deepakrameshrao on 11/02/18.
 */
class AccessLog(id: String, log: String) : BaseObservable() {
    @get:Bindable
    var accessLogMessage: String = log
    set(value) {
        field = value
        notifyPropertyChanged(BR.accessLogMessage)
    }

    var id: String = id

}