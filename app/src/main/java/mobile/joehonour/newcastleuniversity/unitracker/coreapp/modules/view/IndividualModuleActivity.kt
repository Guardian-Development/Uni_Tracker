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

/**
 * The individual module screen is responsible for displaying an individual module to the user,
 * including displaying the calculated current grade a user has achieved within the module.
 */
class IndividualModuleActivity : AppCompatActivity()
{
    val viewModel: IndividualModuleViewModel by viewModel()

    /**
     * Responsible for extracting the module from the data passed to the activity.
     * It then binds the module fields that display the module to their respective view model fields.
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_module)
        val setupData = intent.extras.get("module") as ModuleModel
        viewModel.module.value = setupData

        registerObservers()
    }

    /**
     * Responsible for registering observers to listen when the module is set, and can be displayed,
     * along with listening for when calculations have been completed.
     */
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
                it.notNull() -> individualModuleActivityPercentageComplete.text =
                        getString(R.string.displayedPercentageDouble, it)
            }
        })

        viewModel.currentGrade.observe(this, Observer {
            when {
                it.notNull() -> individualModuleActivityModuleCurrentGrade.text =
                        getString(R.string.displayedPercentageInt, it)
            }
        })
    }

    /**
     * Responsible for requesting calculations for the modules percentage complete and current grade.
     * On successful completion of a calculation the view posts the result to the view model fields
     * which are being observed by the displayed fields.
     */
    private fun requestModuleCalculations()
    {
        viewModel.calculatePercentageComplete({ Log.e("IndividualModuleActivity", it) }) {
            viewModel.percentageComplete.postValue(it)
        }

        viewModel.calculateCurrentGrade({ Log.e("IndividualModuleActivity", it) }) {
            viewModel.currentGrade.postValue(it)
        }
    }

    /**
     * Responsible for updating the UI with the module details.
     *
     * @param moduleModel the module that should be displayed to the user.
     */
    private fun bindIndividualModuleData(moduleModel: ModuleModel)
    {
        individualModuleActivityModuleCode.text = moduleModel.moduleCode
        individualModuleActivityModuleName.text = moduleModel.moduleName
        individualModuleActivityModuleCredits.text = moduleModel.moduleCredits.toString()

        bindListOfResults(moduleModel.results)
    }

    /**
     * Responsible for updating the list of results displayed to the user.
     *
     * @param results the list of results that should be displayed to the user.
     */
    private fun bindListOfResults(results: List<ModuleResultModel>)
    {
        individualModuleActivityModuleResultsList.layoutManager = LinearLayoutManager(this)
        individualModuleActivityModuleResultsList.adapter = ModuleResultModelRecyclerAdapter(results)
    }
}