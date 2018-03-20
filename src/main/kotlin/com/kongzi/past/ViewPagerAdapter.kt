package com.kongzi.past

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

internal class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private val TITLES = arrayOf("Backlinks", "Context", "Replace", "Social")

    override fun getItem(position: Int) = DefaultTabFragment.getInstance(position)
    override fun getCount() = TITLES.size
    override fun getPageTitle(position: Int) = TITLES[position]
}