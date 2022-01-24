package com.alex.yakushev.app.torrentslistvisualizer.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex.yakushev.app.torrentslistvisualizer.model.GeneralMoviesData
import com.alex.yakushev.app.torrentslistvisualizer.model.MovieInfo
import com.alex.yakushev.app.torrentslistvisualizer.service.ServiceApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListViewModel: ViewModel() {
    val movieInfoList = MutableLiveData<List<MovieInfo>>()
    val toastMessage = MutableLiveData<String>()

    private val mCompositeDisposable = CompositeDisposable()

    fun fetchData() {
        val moviesObservable =ServiceApi.Instance.ytsApi.listOfMovies!!

        mCompositeDisposable.add(moviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ inData: GeneralMoviesData? ->
                movieInfoList.value = inData?.data?.movies
            }) { exception: Throwable ->
                toastMessage.value = exception.message
            }
        )
    }
}