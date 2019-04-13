package com.example.user.injapanapp.ui.main_activity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.injapanapp.R
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.adapter.MainAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_ARRAY = "array"

/**
 * A simple {@link Fragment} subclass.
 */
class MainFragment : Fragment(), MainAdapter.OnMainTaskListener {

    override fun onMainTaskClick(taskNum: String) {
        (activity as MainActivity).onMainTaskClick(taskNum)
    }

    override fun onDeleteTaskClick(taskObject: TaskObject) {
        (activity as MainActivity).onDeleteTaskClick(taskObject)
    }

    private var list: List<TaskObject>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = arguments!!.getParcelableArrayList(ARG_PARAM1)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainAdapter()
        adapter.setOnMainTaskListener(this)
        adapter.setListToShow(list!!)
        mainRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(titleArray: String, list: List<TaskObject>): MainFragment {
            val mainFragment = MainFragment()
            val bundle = Bundle()

            bundle.putParcelableArrayList(ARG_PARAM1, list as ArrayList)
            bundle.putString(ARG_ARRAY, titleArray)
            mainFragment.arguments = bundle

            return mainFragment
        }
    }
}
