package mobile.joehonour.newcastleuniversity.unitracker.configuration.view

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_configuration_add_year_weighting.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationAddYearWeightingViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.executeIfNotNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.helpers.TextChangedListener.Companion.bindTextChangedListener
import mobile.joehonour.newcastleuniversity.unitracker.helpers.bindButtonClickedListener
import org.koin.android.architecture.ext.viewModel

class ConfigurationAddYearWeighting : AppCompatActivity()
{
    private val viewModel: ConfigurationAddYearWeightingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration_add_year_weighting)

        registerObservers()
        registerAddYearWeightingButtonAction()
    }

    private fun registerObservers()
    {
        bindTextChangedListener(addYearWeightingActivityYearNumber, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        yearNumber.value = null
                        addYearWeightingActivityYearNumberTextInput.error = "Year Number must be supplied"
                    },
                    {
                        yearNumber.value = it.toInt()
                        addYearWeightingActivityYearNumberTextInput.error = null
                    })
        }
        bindTextChangedListener(addYearWeightingActivityYearWeighting, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        yearWeighting.value = null
                        addYearWeightingActivityYearWeightingTextInput.error = "Year Weighting must be supplied"
                    },
                    {
                        yearWeighting.value = it.toInt()
                        addYearWeightingActivityYearWeightingTextInput.error = null
                    })
        }
        bindTextChangedListener(addYearWeightingActivityCreditsCompletedWithinYear, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        yearCreditsCompleted.value = null
                        addYearWeightingActivityCreditsCompletedWithinYearTextInput.error = "Year Number must be supplied"
                    },
                    {
                        yearCreditsCompleted.value = it.toInt()
                        addYearWeightingActivityCreditsCompletedWithinYearTextInput.error = null
                    })
        }
    }

    private fun registerAddYearWeightingButtonAction()
    {
        bindButtonClickedListener(addYearWeightingActivityAddYearWeightingButton, viewModel) {
            when (viewModel.validDataEnteredForYear) {
                true -> {
                    val yearModel = viewModel.buildYearWeightingModel()
                    val returnIntent = Intent()
                    returnIntent.putExtra("yearWeightingModel", yearModel)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }
                false -> Toast.makeText(
                        this@ConfigurationAddYearWeighting,
                        "Invalid Data Entered",
                        Toast.LENGTH_LONG).show()
            }
        }
    }
}
