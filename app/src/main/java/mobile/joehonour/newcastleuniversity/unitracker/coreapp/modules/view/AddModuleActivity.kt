package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_module.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels.AddModuleViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.executeIfNotNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.helpers.ItemSelectedListener.Companion.bindItemSelectedListener
import mobile.joehonour.newcastleuniversity.unitracker.helpers.TextChangedListener.Companion.bindTextChangedListener
import mobile.joehonour.newcastleuniversity.unitracker.helpers.bindButtonClickedListener
import org.koin.android.architecture.ext.viewModel

class AddModuleActivity : AppCompatActivity()
{
    val viewModel : AddModuleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_module)

        bindAvailableYearStudiedChoices()
        bindDataFieldsToViewModel()
        bindAddModuleButtonToAction()
    }

    private fun bindDataFieldsToViewModel()
    {
        bindTextChangedListener(moduleCode, viewModel) {
            moduleCode.value = it
        }
        bindTextChangedListener(moduleName, viewModel) {
            moduleName.value = it
        }
        bindTextChangedListener(moduleCredits, viewModel) {
            it.executeIfNotNullOrEmpty(
                    { moduleCredits.value = null },
                    { moduleCredits.value = it.toInt()})
        }
        bindItemSelectedListener(yearStudiedSpinner, viewModel) {
            moduleYearStudied.value = it.toString().toInt()
        }
    }

    private fun bindAvailableYearStudiedChoices()
    {
        viewModel.availableYearsStudied({ Log.e("AddModuleActivity", it)})
        {
            val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            yearStudiedSpinner.adapter = dataAdapter
        }
    }

    private fun bindAddModuleButtonToAction()
    {
        bindButtonClickedListener(addModuleButton, viewModel) {
            when (viewModel.validDataEntered) {
                true -> {
                    viewModel.saveModule({ Log.e("AddModuleActivity", it)}) {
                        finish()
                    }
                }
                false -> {
                    Toast.makeText(
                            this@AddModuleActivity,
                            "Invalid Data Entered",
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

