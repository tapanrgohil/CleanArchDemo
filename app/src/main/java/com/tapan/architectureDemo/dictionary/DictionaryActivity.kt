package com.tapan.architectureDemo.dictionary

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapan.architectureDemo.R
import com.tapan.architectureDemo.core.extensions.context.gone
import com.tapan.architectureDemo.core.extensions.context.observe
import com.tapan.architectureDemo.core.extensions.context.visible
import com.tapan.architectureDemo.core.platform.BaseActivity
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_dictionary.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class DictionaryActivity : BaseActivity<DictionaryViewModel>() {


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val dictionaryViewModel: DictionaryViewModel by viewModel()
    private val suggestionsAdapter = ResultAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        attachObservers()
        setViews()

    }

    private fun attachObservers() {
        rvSuggestions.layoutManager = LinearLayoutManager(this@DictionaryActivity)
        rvSuggestions.adapter = suggestionsAdapter

        observe(dictionaryViewModel.getResultLiveData()) {
            it?.apply {
                if (this.isEmpty()) {
                    groupError.visible()
                    tvError.text = "No data found try other word!"

                }else{
                    rvSuggestions.visible()
                    groupError.gone()
                }
                suggestionsAdapter.updateList(this.toMutableList())
            } ?: kotlin.run {
                suggestionsAdapter.updateList(mutableListOf())
            }
        }

    }


    private fun setViews() {
        etSearch.afterTextChangeEvents()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe({
                dictionaryViewModel.getSuggestions(etSearch.text.toString())
            }, {
                it.printStackTrace()
            }).apply {
                compositeDisposable.add(this)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sort, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionSortUp -> {
                sortByThumbsUp()
                true
            }
            R.id.actionSortDown -> {
                sortByThumbsDown()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortByThumbsUp() {
        suggestionsAdapter.updateList(dictionaryViewModel.getSortedLastData(true))
    }

    private fun sortByThumbsDown() {
        suggestionsAdapter.updateList(dictionaryViewModel.getSortedLastData(false))
    }

    override fun onPause() {
        compositeDisposable.clear()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
