package com.example.test.features.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.R
import com.example.test.features.NetworkStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    var currentStatus: NetworkStatus = NetworkStatus.Connection
    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    private val _messageSend = MutableLiveData<Int>()
    val messageSend: LiveData<Int> = _messageSend

    fun startGenerateConnectionStatus() {
        compositeDisposable.add(
            Observable.interval(5, TimeUnit.SECONDS)
                .map { findStatus() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.currentStatus = it
                    setMessage(mapNetworkStatusToMessage(it))
                }, { println("Connection error $it") })
        )
    }

    private fun setMessage(message: Int) {
        _message.postValue(message)
    }

    fun stopGenerateConnectionStatus() {
        compositeDisposable.clear()
    }

    private fun getRandomConnectionsStatus() = listOf(
        NetworkStatus.Connection,
        NetworkStatus.ConnectionError,
        NetworkStatus.ConnectionEstablished,
    ).random()

    fun findStatus(): NetworkStatus {
        while (true) {
            val status = getRandomConnectionsStatus()
            if (status != currentStatus) return status
            else findStatus()
        }
    }

    fun onCheckConnection() {
        if (checkConnection()) {
            _messageSend.postValue(R.string.message_send)
        }
    }

    fun checkConnection() = currentStatus == NetworkStatus.ConnectionEstablished

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun mapNetworkStatusToMessage(status: NetworkStatus): Int {
        return when (status) {
            NetworkStatus.Connection -> R.string.connection
            NetworkStatus.ConnectionError -> R.string.connection_error
            NetworkStatus.ConnectionEstablished -> R.string.connection_established
        }
    }
}