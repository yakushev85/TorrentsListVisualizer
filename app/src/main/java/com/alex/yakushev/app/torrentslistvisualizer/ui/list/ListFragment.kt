package com.alex.yakushev.app.torrentslistvisualizer.ui.list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alex.yakushev.app.torrentslistvisualizer.R
import com.alex.yakushev.app.torrentslistvisualizer.model.MovieInfo
import com.alex.yakushev.app.torrentslistvisualizer.ui.adapter.YtsRecycleListAdapter
import com.alex.yakushev.app.torrentslistvisualizer.ui.base.MainViewModelFactory
import java.util.*


class ListFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    private var mRecyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewFragment = inflater.inflate(R.layout.fragment_list, container, false)
        mRecyclerView = viewFragment.findViewById<View>(R.id.recyclerView) as RecyclerView
        initRecyclerView()
        initData()
        return viewFragment
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragmentInteractionListener) {
            context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(movieInfo: MovieInfo)
    }

    private fun initRecyclerView() {
        val currentOrientation = activity?.resources?.configuration?.orientation
        val layoutManager: RecyclerView.LayoutManager
        val dividerItemDecoration: DividerItemDecoration

        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            dividerItemDecoration =
                DividerItemDecoration(
                    mRecyclerView?.context,
                    DividerItemDecoration.HORIZONTAL
                )
        } else {
            layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
            dividerItemDecoration =
                DividerItemDecoration(
                    mRecyclerView?.context,
                    DividerItemDecoration.VERTICAL
                )
        }

        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.addItemDecoration(dividerItemDecoration)
        mRecyclerView?.itemAnimator =
            DefaultItemAnimator()
        mRecyclerView?.adapter = YtsRecycleListAdapter(ArrayList(), requireActivity())
    }

    private fun initData() {
        val listViewModel = ViewModelProvider(this, MainViewModelFactory())
            .get(ListViewModel::class.java)

        listViewModel.movieInfoList.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            val listAdapter = YtsRecycleListAdapter(it, requireActivity())

            listAdapter.setOnClickListener(object : YtsRecycleListAdapter.MovieInfoOnClickListener {
                override fun onClick(movieInfo: MovieInfo) {
                    mListener?.onFragmentInteraction(movieInfo)
                }
            })

            mRecyclerView!!.adapter = listAdapter
        })

        listViewModel.toastMessage.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        listViewModel.fetchData()
    }
}