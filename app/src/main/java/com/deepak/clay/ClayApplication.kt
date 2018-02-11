package com.deepak.clay

import android.app.Application
import com.deepak.clay.repository.AccessLogRepository
import com.deepak.clay.repository.DoorRepository
import com.deepak.clay.repository.UserRepository

/**
 * Created by deepakrameshrao on 08/02/18.
 */
class ClayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UserRepository().listenToData()
        DoorRepository().listenToData()
        AccessLogRepository().listenToData()
    }
}