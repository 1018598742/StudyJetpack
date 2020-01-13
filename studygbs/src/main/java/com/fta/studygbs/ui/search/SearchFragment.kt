package com.fta.studygbs.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fta.studygbs.AppExecutors
import com.fta.studygbs.R
import com.fta.studygbs.di.Injectable
import com.fta.studygbs.ui.common.RepoListAdapter
import com.fta.studygbs.util.autoCleared
import kotlinx.android.synthetic.main.loading_state.*
import kotlinx.android.synthetic.main.search_fragment.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var adapter by autoCleared<RepoListAdapter>()

    val searchViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

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
        adapter = RepoListAdapter(appExecutors) { repo ->
            navController().navigate(SearchFragmentDirections.showRepo(repo.owner.login, repo.name))
        }
        repo_list.adapter = adapter

        initSearchInputListener()

        retry.setOnClickListener {
            searchViewModel.refresh()
        }

        layoutShowOrGone(false)
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
        val query = input.text.toString()
        // Dismiss keyboard
        dismissKeyboard(view.windowToken)
        searchViewModel.setQuery(query)
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun initRecyclerView() {
        repo_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    searchViewModel.loadNextPage()
                }
            }
        })

        searchViewModel.results.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result?.data)
        })
    }

    private fun layoutShowOrGone(isShow: Boolean) {
        load_more_bar.visibility = if (isShow) View.VISIBLE else View.GONE
        no_results_text.visibility = if (isShow) View.VISIBLE else View.GONE
        loading_state_root.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    fun navController() = findNavController()
}