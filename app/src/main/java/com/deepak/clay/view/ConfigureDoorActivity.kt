package com.deepak.clay.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.deepak.clay.R
import com.deepak.clay.repository.DoorRepository
import com.deepak.clay.repository.UserRepository
import kotlinx.android.synthetic.main.activity_configure_door.*

/**
 * Created by deepakrameshrao on 11/02/18.
 */
class ConfigureDoorActivity : AppCompatActivity() {

    companion object {
        var BUNDLE_DOOR_NAME = "door_name"
        var BUNDLE_DOOR_ACCESS_LIST = "access_list"
    }

    var doorName: String = ""
    lateinit var usersRecyclerViewAdapter: UsersRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configure_door)
        doorName = intent.getStringExtra(BUNDLE_DOOR_NAME)
        var accessList = intent.getStringExtra(BUNDLE_DOOR_ACCESS_LIST)
        door_name.setText(doorName)
        users_recyclerview.layoutManager = LinearLayoutManager(this)
        usersRecyclerViewAdapter = UsersRecyclerViewAdapter(UserRepository.usersMap.values, doorName, accessList)
        users_recyclerview.adapter = usersRecyclerViewAdapter
        supportActionBar?.title = getString(R.string.configure)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.door_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_save -> {
                var accessibleUsers = StringBuffer()
                for(id in UsersRecyclerViewAdapter.accessSet) {
                    accessibleUsers.append(id)
                    accessibleUsers.append(",")
                }
                DoorRepository().addDoor(door_name.text.toString(), accessibleUsers.toString())
                finish()
            }
        }
        return true
    }

}