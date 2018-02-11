package com.deepak.clay.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.deepak.clay.databinding.ItemAccessLogBinding
import com.deepak.clay.model.AccessLog

/**
 * Created by deepakrameshrao on 11/02/18.
 */
class AccessLogRecyclerViewAdapter(var accessList: MutableCollection<AccessLog>): RecyclerView.Adapter<AccessLogRecyclerViewAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(accessList.elementAt(position))

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = ItemAccessLogBinding.inflate(layoutInflater, parent, false)
        return AccessLogRecyclerViewAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int = accessList.size

    fun setData(list: MutableCollection<AccessLog>) {
        accessList = list
        notifyDataSetChanged()
    }


    class ViewHolder(private var binding: ItemAccessLogBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(accessLog: AccessLog) {
            binding.accessLogViewModel = accessLog
            binding.accessLog.text = accessLog.accessLogMessage
            binding.executePendingBindings()
        }

    }
}