package com.example.test.features.viewmodels

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class LogInViewModel : BaseViewModel() {

    private var disposable: Disposable? = null

    fun onSendCommands() {
        disposable?.dispose()
        disposable = Observable.fromIterable(generateSomeCommands())
            .concatMap { id -> Observable.just(id).delay(id.toLong(), TimeUnit.SECONDS).map { id } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ println("ID = $it") }, { println("Error") })
        compositeDisposable.add(disposable!!)
    }

    fun generateSomeCommands() = (START..END).map { Random.nextInt(START, END) }

    companion object {
        private const val START = 1
        private const val END = 10
    }
}