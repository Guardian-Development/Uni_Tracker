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
import mobile.joehonour.newcastleuniversity.unitracker.helpers.ItemSelectedListener.Companion.bindItemSelectedListener
import mobile.joehonour.newcastleuniversity.unitracker.helpers.TextChangedListener.Companion.bindTextChangedListener
import mobile.joehonour.newcastleuniversity.unitracker.helpers.bindButtonClickedListener
import org.koin.android.architecture.ext.viewModel
import java.util.*

class AddResultFragment : Fragment()
{
    val viewModel: AddResultViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        viewModel.availableModules.observe(this, Observer {
            when {
                it.notNull() -> bindSpinnerWithModules(it!!)
            }
        })
        viewModel.refreshAvailableModules()
        return inflater!!.inflate(R.layout.fragment_add_result, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        bindAddResultButtonToAction()
        bindTextChangedListener(addResultName, viewModel) {
            addResultName.value = it
        }
        bindTextChangedListener(addResultWeighting, viewModel) {
            it.executeIfNotNullOrEmpty(
                    { addResultWeightingPercentage.value = null },
                    { addResultWeightingPercentage.value = it.toInt()})
        }
        bindTextChangedListener(addResultResultPercentage, viewModel) {
            it.executeIfNotNullOrEmpty(
                    { addResultPercentage.value = null },
                    { addResultPercentage.value = it.toDouble()})
        }
        bindItemSelectedListener(addResultSelectModuleSpinner, viewModel) {
            when (it) {
                is ModuleModel -> addResultModule.value = it
            }
        }
    }

    private fun bindSpinnerWithModules(modules: List<ModuleModel>)
    {
        val dataAdapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, modules)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        addResultSelectModuleSpinner.adapter = dataAdapter
    }

    private fun bindAddResultButtonToAction()
    {
        bindButtonClickedListener(addResultButton, viewModel) {
            when (viewModel.validDataEntered) {
                true -> {
                    viewModel.saveResultForModule(UUID.randomUUID(), { Log.e("AddResultFragment", it)}) {
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
        addResultSelectModuleSpinner.setSelection(0)
        addResultName.setText("")
        addResultWeighting.setText("")
        addResultResultPercentage.setText("")
    }

    companion object
    {
        fun newInstance(): AddResultFragment = AddResultFragment()
    }
}