package mobile.joehonour.newcastleuniversity.unitracker.coreapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * This is responsible for keeping hold of all the fragments that you wish to display within the
 * view pager, which enables swiping between pages.
 *
 * @param fm the fragment manager to be used when changing the displayed page.
 * @param fragments the list of fragments that are contained within the view pager.
 */
class CoreAppPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>)
    : FragmentPagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount(): Int = fragments.size
}