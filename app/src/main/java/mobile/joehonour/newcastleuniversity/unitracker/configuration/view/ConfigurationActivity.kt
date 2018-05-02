package mobile.joehonour.newcastleuniversity.unitracker.configuration.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_configuration.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.executeIfNotNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.helpers.TextChangedListener.Companion.bindTextChangedListener
import mobile.joehonour.newcastleuniversity.unitracker.helpers.bindButtonClickedListener
import org.koin.android.architecture.ext.viewModel

/**
 * The configuration screen is responsible for capturing all necessary information about a students
 * degree course.
 */
class ConfigurationActivity : AppCompatActivity()
{
    private val viewModel: ConfigurationViewModel by viewModel()

    /**
     * Responsible for binding all displayed text entry fields onto the corresponding view model
     * fields that capture the information.
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        bindDataFieldsToViewModel()
        bindContinueButtonToAction()
    }

    /**
     * Responsible for binding the data fields the user enters their course details in, to the
     * corresponding view model fields.
     *
     * This is done through posting the value the user enters on each text changed event. If the
     * user enters an incorrect value an error message is displayed.
     */
    private fun bindDataFieldsToViewModel()
    {
        bindTextChangedListener(configurationActivityUniversityName, viewModel) {
            universityName.value = it?.trim()
            it.executeIfNotNullOrEmpty(
                    { universityNameTextInput.error = getString(R.string.configurationActivityUniversityNameErrorMessage) },
                    { universityNameTextInput.error = null })
        }
        bindTextChangedListener(configurationActivityYearStarted, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        yearStarted.value = null
                        yearStartedTextInput.error = getString(R.string.configurationActivityYearStartedErrorMessage) },
                    {
                        yearStarted.value = it.toInt()
                        yearStartedTextInput.error = null
                    })
        }
        bindTextChangedListener(configurationActivityCourseLength, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        courseLength.value = null
                        courseLengthTextInput.error = getString(R.string.configurationActivityCourseLengthErrorMessage)
                    },
                    {
                        courseLength.value = it.toInt()
                        courseLengthTextInput.error = null
                    })
        }
        bindTextChangedListener(configurationActivityTargetPercentage, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        targetPercentage.value = null
                        targetPercentageTextInput.error = getString(R.string.configurationActivityTargetPercentageErrorMessage)
                    },
                    {
                        targetPercentage.value = it.toInt()
                        targetPercentageTextInput.error = null
                    })
        }
        bindTextChangedListener(configurationActivityTotalCredits, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        totalCredits.value = null
                        totalCreditsTextInput.error = getString(R.string.configurationActivityTotalCreditsErrorMessage)
                    },
                    {
                        totalCredits.value = it.toInt()
                        totalCreditsTextInput.error = null
                    })
        }
    }

    /**
     * Responsible for providing the action when a user clicks the continue button.
     *
     * If the user has not entered valid data, then an error message is displayed to screen, else
     * the user is navigated to a screen where they can configure their year weightings.
     */
    private fun bindContinueButtonToAction()
    {
        bindButtonClickedListener(continueSetup, viewModel) {
            when (viewModel.validDataEntered) {
                true -> {
                    val data = viewModel.buildConfigurationData()
                    val intent = Intent(this@ConfigurationActivity, ConfigurationYearWeightingActivity::class.java)
                    intent.putExtra("setupData", data)
                    startActivity(intent)
                }
                false -> {
                    Toast.makeText(
                            this@ConfigurationActivity,
                            "Invalid Data Entered",
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

