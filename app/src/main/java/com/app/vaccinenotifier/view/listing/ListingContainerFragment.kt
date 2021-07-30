package com.app.vaccinenotifier.view.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.app.vaccinenotifier.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*

class ListingContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val tabDateFormat = SimpleDateFormat("dd MMM", Locale.ENGLISH)
        val tabTitles = arrayOf("Today", "", "")

        val today = dateFormat.format(Date(cal.timeInMillis))
        cal.add(Calendar.DAY_OF_YEAR, 1)
        val todayPlusOne = dateFormat.format(Date(cal.timeInMillis))
        tabTitles[1] = tabDateFormat.format(Date(cal.timeInMillis))
        cal.add(Calendar.DAY_OF_YEAR, 1)
        val todayPlusTwo = dateFormat.format(Date(cal.timeInMillis))
        tabTitles[2] = tabDateFormat.format(Date(cal.timeInMillis))

        val fragments = listOf<Fragment>(
            VaccineCentersListingFragment.newInstance(today),
            VaccineCentersListingFragment.newInstance(todayPlusOne),
            VaccineCentersListingFragment.newInstance(todayPlusTwo),
        )


        val viewPager = requireView().findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = PagerAdapter(requireActivity(), fragments)
        val tabLayout = requireView().findViewById<TabLayout>(R.id.tabs)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}