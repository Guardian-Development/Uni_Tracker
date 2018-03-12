package mobile.joehonour.newcastleuniversity.unitracker.coreapp

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_core_app_tab_container.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.fragments.AddResultFragment
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.view.ConfigurationFragment
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.fragments.DashboardFragment
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.fragments.ModulesFragment

class CoreAppTabContainerActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core_app_tab_container)

        bindViewPager()
        bindBottomNavigationBar(viewpager)
    }

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

