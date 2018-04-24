package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.view

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_result_list_item.view.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleResultModel

class ModuleResultModelRecyclerAdapter(private val results: List<ModuleResultModel>)
    : RecyclerView.Adapter<ModuleResultModelRecyclerAdapter.ModuleResultHolder>()
{
    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View
            = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

    override fun onBindViewHolder(holder: ModuleResultHolder, position: Int)
    {
        holder.bindYearWeighting(results[position])
    }

    override fun getItemCount(): Int = results.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ModuleResultHolder =
            ModuleResultHolder(parent?.inflate(R.layout.recycler_result_list_item))

    inner class ModuleResultHolder(private val v: View?) : RecyclerView.ViewHolder(v)
    {
        fun bindYearWeighting(result: ModuleResultModel) : RecyclerView.ViewHolder {
            itemView.recyclerResultListItemResultName.text = result.resultName
            itemView.recyclerResultListItemResultWeightingPercentage.text =
                    v?.context?.getString(R.string.displayedPercentageInt, result.resultWeighting)
            itemView.recyclerResultListItemResultPercentage.text =
                    v?.context?.getString(R.string.displayedPercentageDouble, result.resultPercentage)
            return this
        }
    }
}