package com.alex.yakushev.app.torrentslistvisualizer.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alex.yakushev.app.torrentslistvisualizer.ui.list.ListViewModel

class MainViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel() as T
        }

        throw IllegalArgumentException ("Unknown ViewModel")
    }
}