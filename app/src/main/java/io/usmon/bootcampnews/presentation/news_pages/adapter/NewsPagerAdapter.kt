package io.usmon.bootcampnews.presentation.news_pages.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.usmon.bootcampnews.presentation.news_list.NewsListFragment

class NewsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    //We have only 3 pages
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return NewsListFragment.newInstance(position)
    }
}