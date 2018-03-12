package mobile.joehonour.newcastleuniversity.unitracker.coreapp

import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager

class CoreAppPageListener(private val navigation: BottomNavigationView) : ViewPager.OnPageChangeListener
{
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int)
    {
        navigation.menu.getItem(position).isChecked = true
    }
}