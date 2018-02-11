package com.deepak.clay.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import com.deepak.clay.R
import com.deepak.clay.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val viewmodel: HomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewmodel.initialize(getSharedPreferences(LoginActivity.CLAY_PREFERENCE, Context.MODE_PRIVATE))
        binding.homeViewModel = viewmodel
        binding.executePendingBindings()
        doors.setOnClickListener(this)
        signout.setOnClickListener(this)
        supportActionBar?.title = "HOME"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.doors ->
                startActivity(Intent(this, DoorsActivity::class.java))

            R.id.signout -> {
                var sharedPrefrencesEditor = getSharedPreferences(LoginActivity.CLAY_PREFERENCE, Context.MODE_PRIVATE).edit()
                sharedPrefrencesEditor.remove(LoginActivity.LOGGED_IN_USER_ID)
                sharedPrefrencesEditor.remove(LoginActivity.IS_LOGGED_IN_USER_ADMIN)
                sharedPrefrencesEditor.apply()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }
    }

}