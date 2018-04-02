package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.view

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_module_list_item.view.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.extensions.listenForClick

class ModuleModelRecyclerAdapter(private val modules: List<ModuleModel>,
                                 private val clickHandler: (ModuleModel) -> Unit)
    : RecyclerView.Adapter<ModuleModelRecyclerAdapter.ModuleHolder>()
{
    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View
            = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

    override fun onBindViewHolder(holder: ModuleHolder, position: Int)
    {
        holder.bindYearWeighting(modules[position])
    }

    override fun getItemCount(): Int = modules.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ModuleHolder =
            ModuleHolder(parent?.inflate(R.layout.recycler_module_list_item))
                .listenForClick { clickHandler(modules[it]) }

    inner class ModuleHolder(v: View?) : RecyclerView.ViewHolder(v)
    {
        fun bindYearWeighting(module: ModuleModel) : RecyclerView.ViewHolder {
            itemView.moduleCode.text = "Code: " + module.moduleCode
            itemView.moduleName.text = "Name: " + module.moduleName
            itemView.moduleCredits.text = "Credits: " + module.moduleCredits.toString()
            itemView.moduleYearStudied.text = "Year Studied: " + module.moduleYearStudied.toString()
            return this
        }
    }
}