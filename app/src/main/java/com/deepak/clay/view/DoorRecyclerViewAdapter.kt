package com.deepak.clay.view

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.deepak.clay.R
import com.deepak.clay.databinding.ItemDoorBinding
import com.deepak.clay.model.Door
import com.deepak.clay.repository.AccessLogRepository

/**
 * Created by deepakrameshrao on 08/02/18.
 */
class DoorRecyclerViewAdapter(var doorList: MutableCollection<Door>, var id: Int, var isAdmin: Int, var listener: OnItemClickListener, var userName: String)
    : RecyclerView.Adapter<DoorRecyclerViewAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(doorList.elementAt(position), id, isAdmin, listener, position, userName, context)

    override fun getItemCount(): Int {
        return doorList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = ItemDoorBinding.inflate(layoutInflater, parent, false)
        context = parent?.context
        return ViewHolder(binding)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(private var binding: ItemDoorBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(door: Door, id: Int, isAdmin: Int, listener: OnItemClickListener,
                 position: Int, name: String, context: Context?) {
            binding.doorViewModel = door
            door.checkForAdmin(isAdmin)
            binding.executePendingBindings()

            binding.openBtn.setOnClickListener({
                val userList = door.userList
                if (userList != null && userList.contains(id.toString())) {
                    binding.openBtn.setBackgroundColor(
                            ContextCompat.getColor(context, R.color.colorButtonBgColorSuccess))
                    binding.openBtn.setText(R.string.opened)
                    AccessLogRepository().addAccessLogs("$name - ${door.doorName} Door - Opened")
                } else {
                    binding.openBtn.setBackgroundColor(
                            ContextCompat.getColor(context, R.color.colorButtonBgColorFailed))
                    binding.openBtn.setText(R.string.access_denied)
                    AccessLogRepository().addAccessLogs("$name - ${door.doorName} Door - Access Denied")
                }
                android.os.Handler().postDelayed({
                    kotlin.run {
                        binding.openBtn.setBackgroundColor(
                                ContextCompat.getColor(context, R.color.colorButtonBgColor))
                        binding.openBtn.setText(R.string.open)
                    }
                }, 2000)
            })
            binding.configure.setOnClickListener({
                listener.onItemClick(position)
            })
        }
    }

    fun setData(list: MutableCollection<Door>) {
        doorList = list
        notifyDataSetChanged()
    }
}