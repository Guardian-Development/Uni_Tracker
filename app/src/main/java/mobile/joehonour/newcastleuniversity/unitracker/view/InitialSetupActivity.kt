package mobile.joehonour.newcastleuniversity.unitracker.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_initial_setup.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.executeIfNotNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.view.support.TextChangedListener.Companion.bindTextChangedListener
import mobile.joehonour.newcastleuniversity.unitracker.view.support.bindButtonClickedListener
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.InitialSetupViewModel
import org.koin.android.architecture.ext.viewModel

class InitialSetupActivity : AppCompatActivity()
{
    private val viewModel: InitialSetupViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_setup)

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
            when(viewModel.validDataEntered) {
                true -> {
                    val data = viewModel.buildInitialSetupData()
                    Log.e("Test", data.toString())

                    val intent = Intent(this@InitialSetupActivity, InitialSetupYearWeightingActivity::class.java)
                    intent.putExtra("setupData", data)
                    startActivity(intent)
                }
                false -> {
                    Toast.makeText(
                            this@InitialSetupActivity,
                            "Invalid Data Entered",
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

