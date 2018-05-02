package mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.view

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_dashboard.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.viewmodels.DashboardViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import org.koin.android.architecture.ext.viewModel

/**
 * The dashboard screen is responsible for displaying statistics about the students degree. It is the
 * screen presented to the user as they enter the application, after configuration.
 */
class DashboardFragment : Fragment()
{
    val viewModel: DashboardViewModel by viewModel()

    /**
     * Responsible for registering the observers for the fields containing the calculation results.
     * Also requests the calculations to be executed asynchronously on initialisation.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        registerObservers()
        requestCalculationUpdates()
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    /**
     * Responsible for providing the swipe refresh functionality.
     * On the pull down on the screen, the calculation results are re-calculated.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        coreAppDashboardFragmentSwipeRefreshLayout.setOnRefreshListener {
            coreAppDashboardFragmentSwipeRefreshLayout.isRefreshing = true
            requestCalculationUpdates()
            coreAppDashboardFragmentSwipeRefreshLayout.isRefreshing = false
        }
    }

    /**
     * Responsible for querying the view model asking for the calculations to be performed.
     * On receiving a successful calculation result, posts this to the view mode field which is
     * observed by the fields within the view.
     */
    private fun requestCalculationUpdates()
    {
        viewModel.calculatePercentageRequiredToMeetTarget({ Log.e("DashboardFragment", it) }) {
            viewModel.percentageRequiredToMeetTarget.postValue(it)
        }
        viewModel.calculatePercentageOfDegreeCreditsCompleted({ Log.e("DashboardFragment", it) }) {
            viewModel.percentageOfDegreeCreditsCompleted.postValue(it)
        }
        viewModel.calculateAverageGradeAchievedInAllRecordedResults({ Log.e("DashboardFragment", it) }) {
            viewModel.averageGradeAchievedInAllRecordedResults.postValue(it)
        }
    }

    /**
     * Responsible for registering the field observers, updating the view whenever a calculation
     * result is updated.
     */
    private fun registerObservers()
    {
        viewModel.percentageRequiredToMeetTarget.observe(this, Observer {
            when {
                it.notNull() ->
                    coreAppDashboardFragmentPercentageRequiredToMeetTarget.text =
                            getString(R.string.displayedPercentageDouble, it)
            }
        })

        viewModel.averageGradeAchievedInAllRecordedResults.observe(this, Observer {
            when {
                it.notNull() -> coreAppDashboardFragmentAverageGradeAchieved.text =
                        getString(R.string.displayedPercentageDouble, it)
            }
        })

        viewModel.percentageOfDegreeCreditsCompleted.observe(this, Observer {
            when {
                it.notNull() -> updatePercentageOfDegreeCompletedChart(it!!.toFloat())
            }
        })
    }

    /**
     * Responsible for updating the graph displaying the students degree completion percentage.
     *
     * @param percentageComplete the students completion percentage through their degree.
     */
    private fun updatePercentageOfDegreeCompletedChart(percentageComplete: Float)
    {
        val values = mutableListOf(
                PieEntry(percentageComplete),
                PieEntry(100f - percentageComplete))

        val data = createPercentageCompletePieChartData(values)
        data.setValueTextSize(0.0f)

        stylePercentageCompletePieChart(percentageComplete, data)

        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.visibility = View.VISIBLE
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.invalidate()
    }

    /**
     * Responsible for placing the graphs data in a format it is able to understand.
     *
     * @param values a list containing the 2 values the pie chart requires:
     * remaining percentage and completion percentage.
     */
    private fun createPercentageCompletePieChartData(values: MutableList<PieEntry>): PieData
    {
        val dataSet = PieDataSet(values, "Degree Completion")
        val availableColors = mutableListOf(
                ContextCompat.getColor(context!!, R.color.colorAccent),
                ContextCompat.getColor(context!!, R.color.colorPrimary))
        dataSet.colors = availableColors

        return PieData(dataSet)
    }

    /**
     * Responsible for styling the graph using the apps current theme.
     */
    private fun stylePercentageCompletePieChart(percentageComplete: Float, data: PieData)
    {
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.setTouchEnabled(false)
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.description.isEnabled = false
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.legend.isEnabled = false
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.holeRadius = 70f
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad)
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.setHoleColor(Color.TRANSPARENT)
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.setCenterTextColor(
                ContextCompat.getColor(context!!, R.color.textColorPrimary))
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.setCenterTextSize(30f)
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.centerText =
                getString(R.string.displayedPercentageWithLabel, percentageComplete,
                        getString(R.string.coreAppDashboardRequiredToMeetTargetCompleteKeyword))
        coreAppDashboardFragmentPercentageDegreeCompletedPieChart.data = data
    }

    /**
     * Responsible for creating an instance of this fragment.
     */
    companion object
    {
        fun newInstance(): DashboardFragment = DashboardFragment()
    }
}

