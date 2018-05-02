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

/**
 * The configuration of year weightings is responsible for capturing information about each year
 * within a students degree so we can make accurate calculations i.e. about what grade a user needs
 * in their remaining credits.
 */
class ConfigurationYearWeightingActivity : AppCompatActivity()
{
    private val viewModel: ConfigurationYearWeightingViewModel by viewModel()

    /**
     * Responsible for binding the fields a user can enter data in against the view model
     * corresponding fields.
     */
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

    /**
     * This re-binds the fields so a user can enter a new years weightings. It resets the current
     * fields that have been entered to null, and then binds the text changed listeners in order
     * to capture this years information.
     */
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

    /**
     * Responsible for binding the continue button, allowing the user to either enter details for
     * the next year in their degree, or complete the setup.
     */
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

    /**
     * Responsible for progressing from the current years details to the next year. If the user
     * has entered details for all years within the course, this hides the data entry fields and
     * presents the user with the complete setup button.
     */
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

    /**
     * Responsible for saving the users configuration when they click complete setup. On successful
     * saving of the configuration the user is entered into the core app.
     */
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
