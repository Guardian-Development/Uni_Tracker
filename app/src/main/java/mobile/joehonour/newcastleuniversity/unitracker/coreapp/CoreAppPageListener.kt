package mobile.joehonour.newcastleuniversity.unitracker.coreapp

import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager

/**
 * The core app page listener provides a container for the tabs within the core application.
 * This enables swiping between activities, keeping the bottom navigation bar in sync with the
 * swipe layout.
 *
 * @param navigation the bottom navigation bar that needs to be kept in sync with the view pager.
 */
class CoreAppPageListener(private val navigation: BottomNavigationView) : ViewPager.OnPageChangeListener
{
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    /**
     * Responsible for setting the page selected in the bottom navigation bar when this view pager
     * is swiped and therefore the page is changed.
     */
    override fun onPageSelected(position: Int)
    {
        navigation.menu.getItem(position).isChecked = true
    }
}