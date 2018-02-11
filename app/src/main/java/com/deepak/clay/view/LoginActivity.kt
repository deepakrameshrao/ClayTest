package com.deepak.clay.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.deepak.clay.R
import com.deepak.clay.databinding.ActivityLoginBinding
import com.deepak.clay.repository.UserRepository
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityLoginBinding
    var userList: MutableList<UserRepository.User> = mutableListOf()
    var userRepo = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        if(loginViewModel.isLoggedIn(getSharedPreferences(CLAY_PREFERENCE, Context.MODE_PRIVATE))) {
            val intent = Intent(this, DoorsActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginViewModel = loginViewModel
        binding.loginbtn.setOnClickListener(this)
        binding.executePendingBindings()
        userRepo.listenToData()
        /* val firebaseDB = FirebaseDatabase.getInstance()
         val dbRef = firebaseDB.getReference("users")
         UserRepository().listenToData()
         dbRef.addValueEventListener(object : ValueEventListener {
             override fun onDataChange(p0: DataSnapshot?) {
                 userList.clear()
                 p0?.children?.mapNotNullTo(userList) {
                     it.getValue<User>(User::class.java)
                 }
             }

             override fun onCancelled(p0: DatabaseError?) {
                 TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
             }
         })*/

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginbtn -> loginToFirebase()
        }

    }

    private var loginSuccessful: Boolean = false

    companion object {
        val CLAY_PREFERENCE = "clay_preference"

        val LOGGED_IN_USER_ID = "logged_in_user_id"
        val LOGGED_IN_USER_NAME = "logged_in_user_name"

        val IS_LOGGED_IN_USER_ADMIN = "is_logged_in_user_admin"

    }

    private fun loginToFirebase() {
        userList = userRepo.getData()
        var loggedInUser = UserRepository.User()
        loop@ for (i in userList) {
            loginSuccessful = i.userName == username.text.toString() && i.password == password.text.toString()
            if (loginSuccessful) {
                loggedInUser = i
                break@loop
            }
        }

        if (loginSuccessful) {
            val sharedPreferences = getSharedPreferences(CLAY_PREFERENCE, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt(LOGGED_IN_USER_ID, loggedInUser.id)
            editor.putString(LOGGED_IN_USER_NAME, loggedInUser.userName)
            if (loggedInUser.adminAccess) {
                editor.putInt(IS_LOGGED_IN_USER_ADMIN, 1)
            }
            editor.apply()

            val intent = Intent(this, DoorsActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_LONG).show()
            password.error = "Wrong credentials"
        }


    }
}
