package com.deepak.clay.repository

import android.arch.lifecycle.MutableLiveData
import com.deepak.clay.model.Door
import com.google.firebase.database.*

/**
 * Created by deepakrameshrao on 08/02/18.
 */
class DoorRepository {

    var doorList: MutableLiveData<MutableList<Door>> = MutableLiveData()
    var tempList: MutableList<Door> = mutableListOf()

    constructor() {
        doorList.value = mutableListOf()
    }


    fun listenToData() {
        val firebaseDB = FirebaseDatabase.getInstance()
        val dbRef = firebaseDB.getReference("doors")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                doorList.value = tempList
                doorsMutableLiveData.value = doorsMap
                /*doorList.value?.clear()
                val tempList: MutableList<Door> = mutableListOf()
                p0?.children?.mapNotNullTo(tempList) {
                    it.getValue<Door>(Door::class.java)
                }
                doorList.value = tempList*/
            }

            override fun onCancelled(p0: DatabaseError?) {
            }

        })

        dbRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                var door = Door(p0?.key , (p0?.value as HashMap<String, String>).get("userList"))
                tempList.add(door)
                doorsMap.put(door.doorName, door)
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                var door = Door(p0?.key , (p0?.value as HashMap<String, String>).get("userList"))
                tempList.add(door)
                doorsMap.put(door.doorName, door)
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                var door = Door(p0?.key , (p0?.value as HashMap<String, String>).get("userList"))
                tempList.add(door)
                doorsMap.put(door.doorName, door)
            }

            override fun onChildRemoved(p0: DataSnapshot?) {}

        })
    }

    fun getData() : MutableLiveData<MutableList<Door>> {
        return doorList
    }

    fun addDoor(name: String, userList: String?) {
        val firebaseDB = FirebaseDatabase.getInstance()
        val dbRef = firebaseDB.getReference("doors")
        dbRef.child(name).child("userList").setValue(userList)
        dbRef.push()
    }

    companion object {
        var doorsMutableLiveData: MutableLiveData<HashMap<String, Door>> = MutableLiveData()
        var doorsMap: HashMap<String, Door> = HashMap()
    }

    /*class Door {

        constructor(doorName: String?, userList: String) { //Changing from ArrayList to String with COMMA separated
            this.doorName = doorName ?: ""
            this.userList = userList
        }
        var doorName: String = ""
        var userList: String = ""
    }*/
}