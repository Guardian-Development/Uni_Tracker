package mobile.joehonour.newcastleuniversity.unitracker.configuration.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_configuration_year_weighting.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationYearWeightingViewModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.CoreAppTabContainerActivity
import mobile.joehonour.newcastleuniversity.unitracker.helpers.bindButtonClickedListener
import org.koin.android.architecture.ext.viewModel

class ConfigurationYearWeightingActivity : AppCompatActivity()
{
    private val viewModel: ConfigurationYearWeightingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration_year_weighting)

        val setupData = intent.extras.get("setupData") as ConfigurationDataModel
        viewModel.configurationData = setupData

        configurationConfigureYearsAddYearButton.setOnClickListener {
            startActivityForResult(Intent(this@ConfigurationYearWeightingActivity,
                    ConfigurationAddYearWeighting::class.java),
                    ConfigurationYearWeightingActivity.addYearWeightingActivityCode)
        }

        viewModel.yearWeightings.observe(this, Observer {
            when {
                it != null -> {
                    bindListOfConfiguredYearWeightings(it.toList())
                    displayCompleteSetupButton(it.toList())
                }
            }
        })

       bindCompleteSetupButtonToAction()
    }

    private fun bindListOfConfiguredYearWeightings(yearWeightings: List<ConfigurationYearWeightingModel>)
    {
        configurationYearsConfiguredRecyclerView.layoutManager =
                LinearLayoutManager(this@ConfigurationYearWeightingActivity)
        configurationYearsConfiguredRecyclerView.adapter =
                ConfigurationYearWeightingsModelRecyclerAdapter(yearWeightings)
    }

    private fun displayCompleteSetupButton(yearWeightings: List<ConfigurationYearWeightingModel>)
    {
        when {
            yearWeightings.isNotEmpty() -> configurationConfigureYearsCompleteSetupButton.visibility = View.VISIBLE
            else -> configurationConfigureYearsCompleteSetupButton.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ConfigurationYearWeightingActivity.addYearWeightingActivityCode)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                val yearWeightingModel = data?.extras?.get("yearWeightingModel") as ConfigurationYearWeightingModel?
                if(yearWeightingModel != null)
                {
                    val yearWeightings = viewModel.yearWeightings.value
                    yearWeightings?.add(yearWeightingModel)
                    viewModel.yearWeightings.postValue(yearWeightings)
                }
            }
        }
    }

    private fun bindCompleteSetupButtonToAction()
    {
        //todo: some problem with this being executed multiple times
        bindButtonClickedListener(configurationConfigureYearsCompleteSetupButton, viewModel) {
            saveConfiguration({ Log.e("initial setup", it ?: "error") }) {
//                startActivity(Intent(
//                        this@ConfigurationYearWeightingActivity,
//                        CoreAppTabContainerActivity::class.java))
//                this@ConfigurationYearWeightingActivity.finish()
            }
        }
    }

    companion object
    {
        const val addYearWeightingActivityCode = 1
    }
}
