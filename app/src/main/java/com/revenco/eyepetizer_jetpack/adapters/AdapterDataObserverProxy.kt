package com.revenco.eyepetizer_jetpack.adapters

import androidx.recyclerview.widget.RecyclerView


class AdapterDataObserverProxy constructor(
    private val headCount: Int,
    private val observer: RecyclerView.AdapterDataObserver
) : RecyclerView.AdapterDataObserver() {
    override fun onChanged() {
        observer.onChanged()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        observer.onItemRangeChanged(positionStart + headCount, itemCount)
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        observer.onItemRangeChanged(positionStart + headCount, itemCount, payload)
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        observer.onItemRangeInserted(positionStart + headCount, itemCount)
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        observer.onItemRangeMoved(fromPosition + headCount, toPosition + headCount, itemCount)
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        observer.onItemRangeRemoved(positionStart + headCount, itemCount)
    }


}