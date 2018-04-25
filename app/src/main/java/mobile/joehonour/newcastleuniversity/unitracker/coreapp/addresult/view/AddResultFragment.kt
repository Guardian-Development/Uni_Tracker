package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add_result.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.viewmodels.AddResultViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.executeIfNotNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.extensions.hideKeyboard
import mobile.joehonour.newcastleuniversity.unitracker.helpers.ItemSelectedListener.Companion.bindItemSelectedListener
import mobile.joehonour.newcastleuniversity.unitracker.helpers.TextChangedListener.Companion.bindTextChangedListener
import mobile.joehonour.newcastleuniversity.unitracker.helpers.bindButtonClickedListener
import org.koin.android.architecture.ext.viewModel
import java.util.*

class AddResultFragment : Fragment()
{
    val viewModel: AddResultViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        viewModel.availableModules.observe(this, Observer {
            when {
                it.notNull() -> bindSpinnerWithModules(it!!)
            }
        })
        return inflater.inflate(R.layout.fragment_add_result, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean)
    {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser)
        {
            onResume()
        }
    }

    override fun onResume()
    {
        super.onResume()
        viewModel.refreshAvailableModules()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        bindAddResultButtonToAction()
        bindTextChangedListener(addResultFragmentResultName, viewModel) {
            addResultName.value = it?.trim()
            it.executeIfNotNullOrEmpty(
                    { addResultFragmentResultNameTextInput.error = getString(R.string.addResultFragmentResultNameErrorMessage) },
                    { addResultFragmentResultNameTextInput.error = null })
        }
        bindTextChangedListener(addResultFragmentResultWeighting, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        addResultWeightingPercentage.value = null
                        addResultFragmentResultWeightingTextInput.error = getString(R.string.addResultFragmentResultWeightingErrorMessage)
                    },
                    {
                        addResultWeightingPercentage.value = it.toInt()
                        addResultFragmentResultWeightingTextInput.error = null
                    })
        }
        bindTextChangedListener(addResultFragmentResultPercentage, viewModel) {
            it.executeIfNotNullOrEmpty(
                    {
                        addResultPercentage.value = null
                        addResultFragmentResultPercentageTextInput.error = getString(R.string.addResultFragmentResultPercentageErrorMessage)
                    },
                    {
                        addResultPercentage.value = it.toDouble()
                        addResultFragmentResultPercentageTextInput.error = null
                    })
        }
        bindItemSelectedListener(addResultFragmentSelectModuleSpinner, viewModel) {
            when (it) {
                is ModuleModel -> addResultModule.value = it
            }
        }
    }

    private fun bindSpinnerWithModules(modules: List<ModuleModel>)
    {
        val dataAdapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, modules)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        addResultFragmentSelectModuleSpinner.adapter = dataAdapter
    }

    private fun bindAddResultButtonToAction()
    {
        bindButtonClickedListener(addResultFragmentAddResultButton, viewModel) {
            when (viewModel.validDataEntered) {
                true -> {
                    viewModel.saveResultForModule(UUID.randomUUID().toString(), { Log.e("AddResultFragment", it)}) {
                       clearFieldsAndValues()
                    }
                }
                false -> {
                    Toast.makeText(
                            this@AddResultFragment.context,
                            "Invalid Data Entered",
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun clearFieldsAndValues()
    {
        addResultFragmentSelectModuleSpinner.setSelection(0)
        addResultFragmentResultName.setText("")
        addResultFragmentResultWeighting.setText("")
        addResultFragmentResultPercentage.setText("")
        addResultFragmentResultNameTextInput.error = null
        addResultFragmentResultWeightingTextInput.error = null
        addResultFragmentResultPercentageTextInput.error = null
        this.activity?.hideKeyboard()
    }

    companion object
    {
        fun newInstance(): AddResultFragment = AddResultFragment()
    }
}