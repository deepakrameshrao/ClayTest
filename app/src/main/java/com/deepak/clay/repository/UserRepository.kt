package com.deepak.clay.repository

import com.deepak.clay.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by deepakrameshrao on 08/02/18.
 */
class UserRepository() {

    var userList: MutableList<User> = mutableListOf()

    fun listenToData() {
        val firebaseDB = FirebaseDatabase.getInstance()
        val dbRef = firebaseDB.getReference("users")
        dbRef.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot?) {
                        userList.clear()
                        p0?.children?.mapNotNullTo(userList) {
                            it.getValue<User>(User::class.java)
                        }
                        for (user in userList) {
                            usersMap.put(user.id, user)
                        }
                    }

                    override fun onCancelled(p0: DatabaseError?) {}
                })
    }

    fun getData(): MutableList<User> {
        return userList
    }

    fun addUser(name: String, password: String, id: Int, admin: Int) {
        var user = User(name, password, id, admin)

        val firebaseDB = FirebaseDatabase.getInstance()
        val dbRef = firebaseDB.getReference("users")
        val newRef = dbRef.push()
        newRef.setValue(user)
    }


    companion object {
        var usersMap: HashMap<Int, User> = HashMap()
    }
}