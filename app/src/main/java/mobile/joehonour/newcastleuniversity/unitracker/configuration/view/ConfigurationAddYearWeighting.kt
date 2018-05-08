package mobile.joehonour.newcastleuniversity.unitracker.configuration.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationAddYearWeightingViewModel
import org.koin.android.architecture.ext.viewModel

class ConfigurationAddYearWeighting : AppCompatActivity()
{
    private val viewModel: ConfigurationAddYearWeightingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration_add_year_weighting)

        //setResult(Activity.RESULT_OK, returnIntent)
        //todo: Add functionality to capture and validate individual years.
    }
}
