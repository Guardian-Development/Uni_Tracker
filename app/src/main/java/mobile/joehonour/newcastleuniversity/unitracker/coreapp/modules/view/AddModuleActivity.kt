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

/**
 * The add module screen is responsible for providing an ability to add a module to a users
 * configuration.
 */
class AddModuleActivity : AppCompatActivity()
{
    val viewModel : AddModuleViewModel by viewModel()

    /**
     * Responsible for binding the fields used to capture user input to the view models fields.
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_module)

        bindAvailableYearStudiedChoices()
        bindDataFieldsToViewModel()
        bindAddModuleButtonToAction()
    }

    /**
     * Responsible for binding the text entry fields, along with the year studied selector, to the
     * corresponding view model fields.
     */
    private fun bindDataFieldsToViewModel()
    {
        bindTextChangedListener(addModuleActivityModuleCode, viewModel) {
            moduleCode.value = it?.trim()
            it.executeIfNotNullOrEmpty(
                    { addModuleActivityModuleCodeTextInput.error = getString(R.string.addModuleActivityModuleCodeErrorMessage) },
                    { addModuleActivityModuleCodeTextInput.error = null })
        }
        bindTextChangedListener(addModuleActivityModuleName, viewModel) {
            moduleName.value = it?.trim()
            it.executeIfNotNullOrEmpty(
                    { addModuleActivityModuleNameTextInput.error = getString(R.string.addModuleActivityModuleNameErrorMessage) },
                    { addModuleActivityModuleNameTextInput.error = null })
        }
        bindTextChangedListener(addModuleActivityModuleCredits, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        moduleCredits.value = null
                        addModuleActivityModuleCreditsTextInput.error = getString(R.string.addModuleActivityModuleCreditsErrorMessage)
                    },
                    {
                        moduleCredits.value = it.toInt()
                        addModuleActivityModuleCreditsTextInput.error = null
                    })
        }
        bindItemSelectedListener(addModuleActivityYearStudiedSpinner, viewModel) {
            moduleYearStudied.value = it.toString().toInt()
        }
    }

    /**
     * Responsible for binding the available years the user has configured within their degree course,
     * to the drop down box of year studied.
     */
    private fun bindAvailableYearStudiedChoices()
    {
        viewModel.availableYearsStudied({ Log.e("AddModuleActivity", it)})
        {
            val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            addModuleActivityYearStudiedSpinner.adapter = dataAdapter
        }
    }

    /**
     * Responsible for binding the behaviour of the add module button.
     * If the data a user has input is invalid, displays an error message, else it saves the module.
     * Once saved, the user is returned to the modules page of the core app.
     */
    private fun bindAddModuleButtonToAction()
    {
        bindButtonClickedListener(addModuleActivityAddModuleButton, viewModel) {
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

