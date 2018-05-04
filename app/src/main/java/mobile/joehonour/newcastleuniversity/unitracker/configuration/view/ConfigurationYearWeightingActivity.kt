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

        bindCompleteSetupButtonToAction()
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
