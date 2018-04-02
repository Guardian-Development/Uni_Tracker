package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_individual_module.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleResultModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels.IndividualModuleViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import org.koin.android.architecture.ext.viewModel

class IndividualModuleActivity : AppCompatActivity()
{
    val viewModel: IndividualModuleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_module)
        val setupData = intent.extras.get("module") as ModuleModel
        viewModel.module.value = setupData

        registerObservers()
    }

    private fun registerObservers()
    {
        viewModel.module.observe(this, Observer {
            when {
                it.notNull() -> {
                    requestModuleCalculations()
                    bindIndividualModuleData(it!!)
                }
            }
        })

        viewModel.percentageComplete.observe(this, Observer {
            when {
                it.notNull() -> individualModulePercentageComplete.text =
                        "Percentage Complete: " + it.toString()
            }
        })

        viewModel.currentGrade.observe(this, Observer {
            when {
                it.notNull() -> individualModuleCurrentGrade.text =
                        "Current Grade: " + it.toString()
            }
        })
    }

    private fun requestModuleCalculations()
    {
        viewModel.calculatePercentageComplete({ Log.e("IndividualModuleActivity", it) }) {
            viewModel.percentageComplete.postValue(it)
        }

        viewModel.calculateCurrentGrade({ Log.e("IndividualModuleActivity", it) }) {
            viewModel.currentGrade.postValue(it)
        }
    }

    private fun bindIndividualModuleData(moduleModel: ModuleModel)
    {
        individualModuleCode.text = moduleModel.moduleCode
        individualModuleName.text = moduleModel.moduleName
        individualModuleCredits.text = moduleModel.moduleCredits.toString()

        bindListOfResults(moduleModel.results)
    }

    private fun bindListOfResults(results: List<ModuleResultModel>)
    {
        individualModuleResultsList.layoutManager = LinearLayoutManager(this)
        individualModuleResultsList.adapter = ModuleResultModelRecyclerAdapter(results)
    }
}