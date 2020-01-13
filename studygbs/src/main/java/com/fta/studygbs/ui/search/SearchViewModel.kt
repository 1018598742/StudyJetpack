package com.fta.studygbs.ui.search

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.fta.studygbs.repository.RepoRepository
import com.fta.studygbs.util.AbsentLiveData
import com.fta.studygbs.vo.Repo
import com.fta.studygbs.vo.Resource
import com.fta.studygbs.vo.Status
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(repoRepository: RepoRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    private val nextPageHandler = NextPageHandler(repoRepository)

    val query: LiveData<String> = _query

    val results: LiveData<Resource<List<Repo>>> = _query.switchMap { search ->
        if (search.isBlank()) {
            AbsentLiveData.create()
        } else {
            repoRepository.search(search)
        }
    }

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        nextPageHandler.reset()
        _query.value = input

    }

    fun refresh() {
        _query.value?.let {
            _query.value = it
        }
    }

    fun loadNextPage() {
        _query.value?.let {
            if (it.isNotBlank()) {
                nextPageHandler.queryNextPage(it)
            }
        }
    }

    class LoadMoreState(val isRunning: Boolean, val errorMessage: String?) {

    }

    class NextPageHandler(private val repository: RepoRepository) : Observer<Resource<Boolean>> {

        private var nextPageLiveData: LiveData<Resource<Boolean>>? = null

        val loadMoreState = MutableLiveData<LoadMoreState>()

        private var query: String? = null

        private var _hasMore: Boolean = false

        val hasMore
            get() = _hasMore

        init {
            reset()
        }

        fun queryNextPage(query:String){
            if (this.query == query) {
                return
            }
            unregister()
            this.query = query
            nextPageLiveData = repository.searchNextPage(query)
            loadMoreState.value = LoadMoreState(
                isRunning = true,
                errorMessage = null
            )
            nextPageLiveData?.observeForever(this)
        }

        override fun onChanged(result: Resource<Boolean>?) {
            if (result == null) {
                reset()
            } else {
                when (result.status) {
                    Status.SUCCESS -> {
                        _hasMore = result.data == true
                        unregister()
                        loadMoreState.setValue(
                            LoadMoreState(
                                isRunning = false,
                                errorMessage = null
                            )
                        )
                    }
                    Status.ERROR -> {
                        _hasMore = true
                        unregister()
                        loadMoreState.setValue(
                            LoadMoreState(
                                isRunning = false,
                                errorMessage = result.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        // ignore
                    }
                }
            }

        }

        private fun unregister() {
            nextPageLiveData?.removeObserver(this)
            nextPageLiveData = null
            if (_hasMore) {
                query = null
            }
        }

        fun reset() {
            unregister()
            _hasMore = true
            loadMoreState.value = LoadMoreState(
                isRunning = false,
                errorMessage = null
            )
        }

    }

}