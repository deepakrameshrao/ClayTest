package com.deepak.clay.view.accesslog

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.deepak.clay.R
import com.deepak.clay.model.AccessLog
import com.deepak.clay.repository.AccessLogRepository
import kotlinx.android.synthetic.main.activity_access_log.*
import android.support.v7.widget.DividerItemDecoration



/**
 * Created by deepakrameshrao on 11/02/18.
 */
class AccessLogActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_log)
        AccessLogRepository().listenToData()

        var accessAdapter = AccessLogRecyclerViewAdapter(AccessLogRepository.accessLogLive.value?.values ?: mutableListOf())
        AccessLogRepository.accessLogLive.observe(this, Observer<LinkedHashMap<String, AccessLog>> {
            it?.let {
                accessAdapter.setData(AccessLogRepository.accessLogLive.value?.values ?: mutableListOf())
            }
        })
        var layoutManager = LinearLayoutManager(this)
        access_recyclerview.layoutManager = layoutManager
        access_recyclerview.adapter = accessAdapter
        var dividerItemDecoration = DividerItemDecoration(
                this,
                layoutManager.orientation
        )
        access_recyclerview.addItemDecoration(dividerItemDecoration)
        supportActionBar?.title = getString(R.string.access_logs)
    }
}