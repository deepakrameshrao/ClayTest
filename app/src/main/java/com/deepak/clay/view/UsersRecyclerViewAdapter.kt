package com.deepak.clay.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.deepak.clay.databinding.ItemUserBinding
import com.deepak.clay.repository.UserRepository
import java.util.TreeSet


class UsersRecyclerViewAdapter(var usersList: MutableCollection<UserRepository.User>, var doorName: String, var accessList: String)
    : RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>() {

    companion object {
        var accessSet : TreeSet<Int> = TreeSet()
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return UsersRecyclerViewAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(usersList.elementAt(position), doorName, accessList)

    override fun getItemCount(): Int {
        return usersList.size
    }

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserRepository.User, doorName: String, accessList: String) {
            var userModel = UserViewModel(user.userName, accessList, user.id)
            binding.userViewModel = userModel
            userModel.checkSwitchToBeEnabled()
            binding.userNameTv.text = user.userName
            binding.accessSwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if(isChecked && binding.userViewModel != null) {
                        accessSet.add(userModel.userId)
                    } else {
                        accessSet.remove(userModel.userId)
                    }
                }

            })
        }
    }
}