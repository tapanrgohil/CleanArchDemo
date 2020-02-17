package com.tapan.architectureDemo.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tapan.architectureDemo.R
import com.tapan.architectureDemo.dictionary.ResultAdapter.ViewHolder
import com.tapan.architectureDemo.dictionary.model.ResultView
import com.tapan.architectureDemo.dictionary.model.SuggestionsViewDiff
import kotlinx.android.synthetic.main.cell_suggestions.view.*

/**
 * Recycler view adapter for Suggestions
 */
class ResultAdapter(private var result: MutableList<ResultView>) : RecyclerView.Adapter<ViewHolder>() {

    /**
     * Update list wtih  Use of 'diffUtils' while updating data for sorting data
     */
    fun updateList(newSuggestions: MutableList<ResultView>) {
        val diffCallBack = SuggestionsViewDiff(result, newSuggestions)

        val diffResult = DiffUtil.calculateDiff(diffCallBack)

        this.result.clear()
        this.result.addAll(newSuggestions)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cell_suggestions,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = result.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            result[position].apply {
                tvWord.text = word
                tvDefinition.text = definition
                tvThumbsUp.text = thumbsUp.toString()
                tvThumbsDown.text = thumbsDown.toString()
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}