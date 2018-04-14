package mobile.joehonour.newcastleuniversity.unitracker.configuration.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_configuration_year_weighting.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationYearWeightingViewModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.CoreAppTabContainerActivity
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.executeIfNotNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.extensions.hideKeyboard
import mobile.joehonour.newcastleuniversity.unitracker.helpers.TextChangedListener.Companion.bindTextChangedListener
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

        bindNewYearWeightingToModel()
        bindContinueButtonToAction()
        bindCompleteSetupButtonToAction()
    }

    private fun bindNewYearWeightingToModel()
    {
        viewModel.bindModelForCurrentYear {
                yearBeingSelected.text = viewModel.currentYear.toString()
                configurationYearWeightingActivityYearWeighting.text = null
                configurationYearWeightingActivityCreditsCompletedWithinYear.text = null
                bindTextChangedListener(configurationYearWeightingActivityYearWeighting, this) {
                    it.executeIfNotNullOrEmpty(
                            {
                                currentYearWeighting.value = null
                                yearWeightingTextInput.error = "Year Weighting must be supplied"
                            },
                            {
                                currentYearWeighting.value = it.toInt()
                                yearWeightingTextInput.error = null
                            })
                }
                bindTextChangedListener(configurationYearWeightingActivityCreditsCompletedWithinYear, this) {
                    it.executeIfNotNullOrEmpty(
                            {
                                currentYearCreditsCompleted.value = null
                                creditsCompletedWithinYearTextInput.error = "Credits Completed must be supplied"
                            },
                            {
                                currentYearCreditsCompleted.value = it.toInt()
                                creditsCompletedWithinYearTextInput.error = null
                            })
                }
        }
    }

    private fun bindContinueButtonToAction()
    {
        bindButtonClickedListener(continueButton, viewModel) {
            when (viewModel.validDataEnteredForCurrentYearWeighting) {
                true -> {
                    hideKeyboard()
                    progressYearWeightingEntry()
                }
                false -> Toast.makeText(
                        this@ConfigurationYearWeightingActivity,
                        "Invalid Data Entered",
                        Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun progressYearWeightingEntry()
    {
        viewModel.finishEditingCurrentYear()
        when(viewModel.completedWeightingsForAllYear) {
            true -> {
                yearBeingSelected.visibility = View.INVISIBLE
                yearBeingSelectedMessage.visibility = View.INVISIBLE
                yearSelectHelpMessage.visibility = View.INVISIBLE
                yearWeightingTextInput.visibility = View.INVISIBLE
                creditsCompletedWithinYearTextInput.visibility = View.INVISIBLE
                continueButton.visibility = View.INVISIBLE
                completeSetupButton.visibility = View.VISIBLE
            }
            false -> {
                bindNewYearWeightingToModel()
            }
        }
    }

    private fun bindCompleteSetupButtonToAction()
    {
        bindButtonClickedListener(completeSetupButton, viewModel) {
            saveConfiguration({ Log.e("initial setup", it ?: "error") }) {
                startActivity(Intent(
                        this@ConfigurationYearWeightingActivity,
                        CoreAppTabContainerActivity::class.java))
            }
        }
    }
}
