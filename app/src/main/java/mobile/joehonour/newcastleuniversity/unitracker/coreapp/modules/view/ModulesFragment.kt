package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_modules.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels.ModulesViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import org.koin.android.architecture.ext.viewModel

class ModulesFragment : Fragment()
{
    val viewModel : ModulesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        viewModel.currentModules.observe(this, Observer {
            when {
                it.notNull() -> bindListOfModules(it!!)
            }
        })
        return inflater.inflate(R.layout.fragment_modules, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        coreAppModulesFragmentModuleListSwipeRefresh.setOnRefreshListener {
            coreAppModulesFragmentModuleListSwipeRefresh.isRefreshing = true
            viewModel.refreshCurrentModules()
            coreAppModulesFragmentModuleListSwipeRefresh.isRefreshing = false
        }
        coreAppModulesFragmentAddModuleButton.setOnClickListener {
            startActivity(Intent(this.context, AddModuleActivity::class.java))
        }
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
        viewModel.refreshCurrentModules()
    }

    private fun bindListOfModules(modules: List<ModuleModel>)
    {
        coreAppModulesFragmentModulesList.layoutManager = LinearLayoutManager(context)
        coreAppModulesFragmentModulesList.adapter = ModuleModelRecyclerAdapter(modules) {
            val intent = Intent(this.context, IndividualModuleActivity::class.java)
            intent.putExtra("module", it)
            startActivity(intent)
        }
    }

    companion object
    {
        fun newInstance(): ModulesFragment = ModulesFragment()
    }
}

