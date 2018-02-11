package com.deepak.clay.repository

import android.arch.lifecycle.MutableLiveData
import com.deepak.clay.model.AccessLog
import com.google.firebase.database.*
import java.util.LinkedHashMap

/**
 * Created by deepakrameshrao on 11/02/18.
 */
class AccessLogRepository() {
    companion object {
        var accessLogLive: MutableLiveData<LinkedHashMap<String, AccessLog>> = MutableLiveData()
        var accessHashMap: LinkedHashMap<String, AccessLog> = LinkedHashMap()
    }

    fun listenToData() {
        val firebaseDB = FirebaseDatabase.getInstance()
        val dbRef = firebaseDB.getReference("accesslogs")
        val query = dbRef.orderByKey().limitToLast(100)
        dbRef.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                //var temp = accessLogLiveData.value
                var accessLog = AccessLog(p0?.key as String, p0?.value as String)
                /*if(temp == null) temp = mutableListOf()
                temp.add(accessLog)
                accessLogLiveData.value = temp*/

                accessHashMap.put(accessLog.id, accessLog)
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                accessLogLive.value = accessHashMap
            }
        })
    }

    fun addAccessLogs(message: String) {
        val firebaseDB = FirebaseDatabase.getInstance()
        val dbRef = firebaseDB.getReference("accesslogs")
        dbRef.push().setValue(message)
    }
}