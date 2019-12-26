package com.kardusinfo.amikomup.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kardusinfo.amikomup.view.bimbingan.fragments.BimbinganBook
import com.kardusinfo.amikomup.view.bimbingan.fragments.BimbinganStatus

class FragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val pages = listOf(
        BimbinganBook(),
        BimbinganStatus()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Booking Bimbingan"
            else -> "Status Bimbingan"
        }
    }
}