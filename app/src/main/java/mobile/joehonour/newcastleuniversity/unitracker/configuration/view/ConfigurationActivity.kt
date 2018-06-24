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

class ConfigurationActivity : AppCompatActivity()
{
    private val viewModel: ConfigurationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        bindDataFieldsToViewModel()
        bindContinueButtonToAction()
    }

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
    }

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

