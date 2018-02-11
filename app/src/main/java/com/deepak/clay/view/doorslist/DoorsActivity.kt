package com.deepak.clay.view.doorslist

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.deepak.clay.R
import com.deepak.clay.model.Door
import com.deepak.clay.repository.DoorRepository
import com.deepak.clay.view.configuredoor.ConfigureDoorActivity
import com.deepak.clay.view.login.LoginActivity
import com.deepak.clay.view.accesslog.AccessLogActivity
import kotlinx.android.synthetic.main.activity_doors.*

class DoorsActivity : AppCompatActivity(), DoorRecyclerViewAdapter.OnItemClickListener {

    lateinit var doorsList: MutableCollection<Door>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doors)
        var doorRepo = DoorRepository()
        doorRepo.listenToData()
        var layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        var id: Int = getSharedPreferences(LoginActivity.CLAY_PREFERENCE, Context.MODE_PRIVATE).getInt(LoginActivity.LOGGED_IN_USER_ID, 0)
        var name: String = getSharedPreferences(LoginActivity.CLAY_PREFERENCE, Context.MODE_PRIVATE).getString(LoginActivity.LOGGED_IN_USER_NAME, "")
        doorsList = DoorRepository.doorsMutableLiveData.value?.values ?: mutableListOf()
        val repoAdapter = DoorRecyclerViewAdapter(DoorRepository.doorsMutableLiveData.value?.values ?: mutableListOf(), id, getAdminStatus(), this, name)
        recyclerview.adapter = repoAdapter
        var dividerItemDecoration = DividerItemDecoration(
                this,
                layoutManager.orientation
        )
        recyclerview.addItemDecoration(dividerItemDecoration)

        DoorRepository.doorsMutableLiveData.observe(this, Observer<HashMap<String, Door>> {
            it?.let {
                doorsList = it.values
                repoAdapter.setData(it.values)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_configure)?.isVisible = getAdminStatus() > 0
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_logout -> {
                var sharedPrefrencesEditor = getSharedPreferences(LoginActivity.CLAY_PREFERENCE, Context.MODE_PRIVATE).edit()
                sharedPrefrencesEditor.remove(LoginActivity.LOGGED_IN_USER_ID)
                sharedPrefrencesEditor.remove(LoginActivity.IS_LOGGED_IN_USER_ADMIN)
                sharedPrefrencesEditor.apply()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.action_configure -> {
                startActivity(Intent(this, AccessLogActivity::class.java))
            }
        }

        return true
    }

    override fun onItemClick(position: Int) {
        var door = doorsList.elementAt(position)
        var intent = Intent(this, ConfigureDoorActivity::class.java)
        intent.putExtra(ConfigureDoorActivity.BUNDLE_DOOR_NAME, door.doorName)
        intent.putExtra(ConfigureDoorActivity.BUNDLE_DOOR_ACCESS_LIST, door.userList)
        startActivity(intent)
    }

    fun getAdminStatus(): Int {
        return getSharedPreferences(LoginActivity.CLAY_PREFERENCE, Context.MODE_PRIVATE)
                .getInt(LoginActivity.IS_LOGGED_IN_USER_ADMIN, 0)
    }
}