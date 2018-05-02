package mobile.joehonour.newcastleuniversity.unitracker.coreapp

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_core_app_tab_container.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.view.AddResultFragment
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.view.ConfigurationFragment
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.view.DashboardFragment
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.view.ModulesFragment

/**
 * The core app container activity is responsible for containing the navigation of all the core app
 * pages, along with providing a container to display each page.
 */
class CoreAppTabContainerActivity : AppCompatActivity()
{
    /**
     * Responsible for binding the bottom navigation bar and view page (swiping functionality) in order
     * to provide navigation within the application.
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core_app_tab_container)

        bindViewPager()
        bindBottomNavigationBar(viewpager)
    }

    /**
     * Responsible for binding the pages within the swipe layout.
     */
    private fun bindViewPager()
    {
        val fragments = listOf(
                AddResultFragment.newInstance(),
                ModulesFragment.newInstance(),
                DashboardFragment.newInstance(),
                ConfigurationFragment.newInstance())
        viewpager.adapter = CoreAppPagerAdapter(supportFragmentManager, fragments)
        viewpager.currentItem = 2
        viewpager.addOnPageChangeListener(CoreAppPageListener(bottom_navigation))
    }

    /**
     * Responsible for binding the bottom navigation bar, using the click events to update the
     * view pager in order to get both swipe, and click, navigation.
     *
     * @param viewPager the swipe layout to keep in sync with the bottom navigation bar.
     */
    private fun bindBottomNavigationBar(viewPager: ViewPager)
    {
        bottom_navigation.selectedItemId = R.id.dashboard
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.add_result -> viewPager.currentItem = 0
                R.id.modules -> viewPager.currentItem = 1
                R.id.dashboard -> viewPager.currentItem = 2
                R.id.configuration -> viewPager.currentItem = 3
                else -> throw IllegalStateException("can't handle this fragment ID")
            }
            true
        }
    }
}

