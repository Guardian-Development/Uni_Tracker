package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_modules.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels.ModulesViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.extensions.showDeleteItemConfirmationCheckbox
import mobile.joehonour.newcastleuniversity.unitracker.extensions.showDeletionMessage
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
        viewModel.refreshCurrentModules()
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

    private fun bindListOfModules(modules: List<ModuleModel>)
    {
        coreAppModulesFragmentModulesList.layoutManager = LinearLayoutManager(context)
        coreAppModulesFragmentModulesList.adapter =
                ModuleModelRecyclerAdapter(
                        modules,
                        this@ModulesFragment::handleClickEvent,
                        this@ModulesFragment::handleLongClickEvent)
    }

    private fun handleClickEvent(module: ModuleModel) {
        val intent = Intent(this.context, IndividualModuleActivity::class.java)
        intent.putExtra("module", module)
        startActivity(intent)
    }

    private fun handleLongClickEvent(module: ModuleModel) {
        this.activity?.showDeleteItemConfirmationCheckbox(module) {
            viewModel.deleteModule(module.moduleId,
                    { Log.e("ModulesFragment", it) },
                    { this.activity?.showDeletionMessage(it,
                            getString(R.string.deleteItemDialogSuccessMessageModule))
                    })
        }
    }

    companion object
    {
        fun newInstance(): ModulesFragment = ModulesFragment()
    }
}

