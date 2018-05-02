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

/**
 * The modules screen is responsible for displaying the list of configured modules to the user,
 * while allowing the user to add a new module within the application.
 */
class ModulesFragment : Fragment()
{
    val viewModel : ModulesViewModel by viewModel()

    /**
     * Responsible for binding the listener for binding the available modules to the list of modules
     * displayed within the UI.
     */
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

    /**
     * Responsible for registering the swipe refresh layout, that allows for refreshing of all
     * available modules within the application. Also registers the button click event for the add
     * module button.
     */
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

    /**
     * Responsible for listening for when this fragment is displayed to the user.
     * If it is visible it automatically refreshes the list of available modules.
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean)
    {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser)
        {
            onResume()
        }
    }

    /**
     * Responsible for refreshing the list of available modules when the fragment is resumed.
     */
    override fun onResume()
    {
        super.onResume()
        viewModel.refreshCurrentModules()
    }

    /**
     * Responsible for building the list of modules displayed to the user.
     *
     * @param modules the list of modules you wish to display to the user.
     */
    private fun bindListOfModules(modules: List<ModuleModel>)
    {
        coreAppModulesFragmentModulesList.layoutManager = LinearLayoutManager(context)
        coreAppModulesFragmentModulesList.adapter = ModuleModelRecyclerAdapter(modules) {
            val intent = Intent(this.context, IndividualModuleActivity::class.java)
            intent.putExtra("module", it)
            startActivity(intent)
        }
    }

    /**
     * Provides a static way of initialising the this fragment.
     */
    companion object
    {
        fun newInstance(): ModulesFragment = ModulesFragment()
    }
}

