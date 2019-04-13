package com.example.user.injapanapp.ui.main_activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.example.user.injapanapp.database.TaskObject

class MyPageAdapter(fm: FragmentManager, private val titleArray: Array<String>, private val list: List<TaskObject>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val newList = if (position == 0) {
            list
        } else {
            val filteredList = mutableListOf<TaskObject>()
            for (o in list) {
                if (o.taskType == array[position])
                    filteredList.add(o)
            }
            filteredList
        }
        return MainFragment.newInstance(titleArray[position], newList)
    }

    private val array = arrayOf("ALL", "TEST", "SEPARATE", "TRASH", "保留リスト")

    override fun getCount(): Int {
        return titleArray.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return array[position]
    }

}