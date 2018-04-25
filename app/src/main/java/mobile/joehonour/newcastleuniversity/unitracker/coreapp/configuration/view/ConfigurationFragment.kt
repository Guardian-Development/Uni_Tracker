package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.fragment_configuration.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels.ConfigurationViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.login.view.LoginActivity
import org.koin.android.architecture.ext.viewModel

class ConfigurationFragment : Fragment()
{
    private val viewModel: ConfigurationViewModel by viewModel()

    private var isSettingsMenuOpen = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        viewModel.configuration.observe(this, Observer {
            when {
                it.notNull() -> bindDisplayToConfiguration(it!!)
            }
        })

        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        registerSettingsMenuButtonOpenCloseAnimation()

        coreAppConfigurationFragmentSettingsLogoutButton.setOnClickListener {
            viewModel.logoutOfApplication({ Log.e("ConfigurationFragment", it)}) {
                startActivity(Intent(this.context, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }

    private fun bindDisplayToConfiguration(configurationModel: ConfigurationModel)
    {
        coreAppConfigurationFragmentUniversity.text = configurationModel.universityName
        coreAppConfigurationFragmentYearStarted.text = configurationModel.yearStarted.toString()
        coreAppConfigurationFragmentCourseLength.text = configurationModel.courseLength.toString()
        coreAppConfigurationFragmentTargetPercentage.text = getString(R.string.displayedPercentageInt, configurationModel.targetPercentage)
        coreAppConfigurationFragmentTotalCredits.text = configurationModel.totalCredits.toString()

        coreAppConfigurationFragmentYearWeightingList.layoutManager = LinearLayoutManager(context)
        coreAppConfigurationFragmentYearWeightingList.adapter =
                ConfigurationYearWeightingsModelRecyclerAdapter(configurationModel.yearWeightings)
    }

    private fun registerSettingsMenuButtonOpenCloseAnimation()
    {
        coreAppConfigurationFragmentSettingsMenuButton.setOnClickListener {
            toggleButtonMenuVisibility()
        }
    }

    private fun toggleButtonMenuVisibility()
    {
        val openAnimation = AnimationUtils.loadAnimation(context, R.anim.animation_fab_open)
        val closeAnimation = AnimationUtils.loadAnimation(context, R.anim.animation_fab_close)

        when(isSettingsMenuOpen) {
            true -> {
                coreAppConfigurationFragmentSettingsLogoutButton.startAnimation(closeAnimation)
            }
            false -> {
                coreAppConfigurationFragmentSettingsLogoutButton.startAnimation(openAnimation)
            }
        }
        isSettingsMenuOpen = !isSettingsMenuOpen
    }

    companion object
    {
        fun newInstance(): ConfigurationFragment = ConfigurationFragment()
    }
}

