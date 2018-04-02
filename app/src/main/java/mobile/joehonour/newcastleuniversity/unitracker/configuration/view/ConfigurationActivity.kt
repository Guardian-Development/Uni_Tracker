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
        bindTextChangedListener(universityName, viewModel) {
            universityName.value = it
        }
        bindTextChangedListener(yearStarted, viewModel) {
            it.executeIfNotNullOrEmpty(
                    { yearStarted.value = null },
                    { yearStarted.value = it.toInt()})
        }
        bindTextChangedListener(courseLength, viewModel) {
            it.executeIfNotNullOrEmpty(
                    { courseLength.value = null },
                    { courseLength.value = it.toInt()})
        }
        bindTextChangedListener(targetPercentage, viewModel) {
            it.executeIfNotNullOrEmpty(
                    { targetPercentage.value = null },
                    { targetPercentage.value = it.toInt()})
        }
        bindTextChangedListener(totalCredits, viewModel) {
            it.executeIfNotNullOrEmpty(
                    { totalCredits.value = null },
                    { totalCredits.value = it.toInt()})
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

