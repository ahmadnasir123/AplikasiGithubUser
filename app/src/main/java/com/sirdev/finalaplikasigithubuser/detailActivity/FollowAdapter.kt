package com.sirdev.finalaplikasigithubuser.detailActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FollowAdapter(activity: AppCompatActivity, data: Bundle) : FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentFollowers()
            1 -> fragment = FragmentFollowing()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}