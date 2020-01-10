package com.fta.studygbs.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fta.studygbs.AppExecutors
import com.fta.studygbs.R
import com.fta.studygbs.di.Injectable
import com.fta.studygbs.ui.common.RepoListAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var appExecutors: AppExecutors

    var adapter: RepoListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        adapter = RepoListAdapter(appExecutors){
//            navController().navigate()
        }
        repo_list.adapter = adapter

        initSearchInputListener()

    }

    private fun initSearchInputListener() {
        input.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }

        input.setOnKeyListener { view, keyCode, event ->
            when (event.action) {
                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER -> {
                    doSearch(view)
                    true
                }
                else -> false
            }
        }
    }

    private fun doSearch(view: View) {

    }

    private fun initRecyclerView() {
        repo_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    fun navController() = findNavController()
}