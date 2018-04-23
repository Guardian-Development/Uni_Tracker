package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_configuration.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels.ConfigurationViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import org.koin.android.architecture.ext.viewModel

class ConfigurationFragment : Fragment()
{
    private val viewModel: ConfigurationViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        viewModel.configuration.observe(this, Observer {
            when {
                it.notNull() -> bindDisplayToConfiguration(it!!)
            }
        })

        return inflater!!.inflate(R.layout.fragment_configuration, container, false)
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

    companion object
    {
        fun newInstance(): ConfigurationFragment = ConfigurationFragment()
    }
}

