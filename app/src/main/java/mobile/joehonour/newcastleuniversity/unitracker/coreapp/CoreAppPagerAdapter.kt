package mobile.joehonour.newcastleuniversity.unitracker.coreapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class CoreAppPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>)
    : FragmentPagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount(): Int = fragments.size
}