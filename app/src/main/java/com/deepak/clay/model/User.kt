package com.deepak.clay.model

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