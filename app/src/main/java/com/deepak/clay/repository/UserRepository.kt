package com.deepak.clay.repository

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
                        for(user in userList) {
                            usersMap.put(user.id, user)
                        }
                    }

                    override fun onCancelled(p0: DatabaseError?) {}
                })

        /*dbRef.addChildEventListener(
                object : ChildEventListener{
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
                        val user = p0?.getValue(User::class.java)

                        if(user != null) { userMap.put(user.id, user)
                        userList.add(user)}
                    }

                    override fun onChildRemoved(p0: DataSnapshot?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        )*/
    }

    fun getData() : MutableList<User> {
        return userList
    }

    fun addUser(name: String, password: String, id: Int, admin: Int) {
        var user = User(name, password, id, admin)

        val firebaseDB = FirebaseDatabase.getInstance()
        val dbRef = firebaseDB.getReference("users")
        val newRef = dbRef.push()
        newRef.setValue(user)
    }


    class User {

        constructor() {}
        constructor(userName: String, password: String, id: Int, admin: Int) {
            this.userName = userName
            this.password = password
            this.id = id
        }
        var userName: String = ""
        var password: String = ""
        var id: Int = 0
        var adminAccess: Boolean = false
    }

    companion object {
        var usersMap: HashMap<Int, User> = HashMap()
    }
}