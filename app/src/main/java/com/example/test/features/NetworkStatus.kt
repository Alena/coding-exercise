package com.example.test.features

sealed interface NetworkStatus {
    object Connection : NetworkStatus
    object ConnectionError : NetworkStatus
    object ConnectionEstablished : NetworkStatus
}