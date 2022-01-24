package com.alex.yakushev.app.torrentslistvisualizer.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.yakushev.app.torrentslistvisualizer.model.MovieInfo
import com.alex.yakushev.app.torrentslistvisualizer.service.ServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ListViewModel: ViewModel() {
    val movieInfoList = MutableLiveData<List<MovieInfo>>()
    val toastMessage = MutableLiveData<String>()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val generalMoviesData = ServiceApi.Instance.ytsApi.listOfMovies.execute().body()
                movieInfoList.postValue(generalMoviesData!!.data!!.movies)
            } catch (e: Exception) {
                toastMessage.postValue(e.message)
            }
        }
    }
}