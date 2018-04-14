package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.view

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_year_weightings_list_item.view.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationYearWeightingModel

class ConfigurationYearWeightingsModelRecyclerAdapter(private val yearWeightingModels: List<ConfigurationYearWeightingModel>)
    : RecyclerView.Adapter<ConfigurationYearWeightingsModelRecyclerAdapter.ConfigurationYearWeightingHolder>()
{
    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View
            = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

    override fun onBindViewHolder(holder: ConfigurationYearWeightingHolder, position: Int)
            = holder.bindYearWeighting(yearWeightingModels[position])

    override fun getItemCount(): Int = yearWeightingModels.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ConfigurationYearWeightingHolder
            = ConfigurationYearWeightingHolder(parent?.inflate(R.layout.recycler_year_weightings_list_item))

    class ConfigurationYearWeightingHolder(v: View?) : RecyclerView.ViewHolder(v)
    {
        fun bindYearWeighting(yearWeightingModel: ConfigurationYearWeightingModel) {
            itemView.yearWeightingListItemYear.text = yearWeightingModel.year.toString()
            itemView.yearWeightingListItemWeighting.text = yearWeightingModel.weighting.toString()
            itemView.yearWeightingListItemCreditsTaken.text = yearWeightingModel.creditsCompletedWithinYear.toString()
        }
    }
}