package mobile.joehonour.newcastleuniversity.unitracker.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.joehonour.newcastleuniversity.unitracker.R

class ModulesFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater!!.inflate(R.layout.fragment_modules, container, false)
    }

    companion object
    {
        fun newInstance(): ModulesFragment = ModulesFragment()
    }
}