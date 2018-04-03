package mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_dashboard.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.viewmodels.DashboardViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import org.koin.android.architecture.ext.viewModel

class DashboardFragment : Fragment()
{
    val viewModel: DashboardViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        registerObservers()
        viewModel.calculatePercentageRequiredToMeetTarget({ Log.e("DashboardFragment", it)}) {
            viewModel.percentageRequiredToMeetTarget.postValue(it)
        }
        viewModel.calculatePercentageOfDegreeCreditsCompleted({ Log.e("DashboardFragment", it)}) {
            viewModel.percentageOfDegreeCreditsCompleted.postValue(it)
        }
        viewModel.calculateAverageGradeAchievedInAllRecordedResults({ Log.e("DashboardFragment", it)}) {
            viewModel.averageGradeAchievedInAllRecordedResults.postValue(it)
        }
        return inflater!!.inflate(R.layout.fragment_dashboard, container, false)
    }

    private fun registerObservers()
    {
        viewModel.percentageRequiredToMeetTarget.observe(this, Observer {
            when {
                it.notNull() -> dashboardPercentageRequiredToMeetTarget.text =
                        "Percentage Required to Meet Target: " + it.toString()
            }
        })

        viewModel.percentageOfDegreeCreditsCompleted.observe(this, Observer {
            when {
                it.notNull() -> dashboardPercentageOfDegreeCreditsCompleted.text =
                        "Percentage of Degree Completed: " + it.toString()
            }
        })

        viewModel.averageGradeAchievedInAllRecordedResults.observe(this, Observer {
            when {
                it.notNull() -> dashboardAverageGradeAchievedInAllRecordedResults.text =
                        "Average Grade Achieved so far: " + it.toString()
            }
        })
    }

    companion object
    {
        fun newInstance(): DashboardFragment = DashboardFragment()
    }
}

