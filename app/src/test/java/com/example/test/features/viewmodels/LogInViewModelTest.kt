package com.example.test.features.viewmodels

import com.example.test.R
import com.example.test.features.NetworkStatus
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LogInViewModelTest {

    private lateinit var viewModel: LogInViewModel

     @Before
    fun initSetUp() {
        viewModel = LogInViewModel()
    }

    @Test
    fun `check connection status true`() {
        val resId = viewModel.mapNetworkStatusToMessage(NetworkStatus.Connection)
        Assert.assertEquals(resId, R.string.connection)
    }

    @Test
    fun `check connection error status true`() {
        val resId = viewModel.mapNetworkStatusToMessage(NetworkStatus.ConnectionError)
        Assert.assertEquals(resId, R.string.connection_error)
    }

    @Test
    fun `check connection status established true`() {
        val resId = viewModel.mapNetworkStatusToMessage(NetworkStatus.ConnectionEstablished)
        Assert.assertEquals(resId, R.string.connection_established)
    }

    @Test
    fun `check connection status false`() {
        val resId = viewModel.mapNetworkStatusToMessage(NetworkStatus.Connection)
        Assert.assertNotEquals(resId, R.string.connection_error)
    }

    @Test
    fun `generate some commands true`() {
        val listCommands = viewModel.generateSomeCommands()
        Assert.assertEquals(listCommands.size, 10)
    }

    @Test
    fun `generate some commands false`() {
        val listCommands = viewModel.generateSomeCommands()
        Assert.assertNotEquals(listCommands.size, 9)
    }

    @Test
    fun `check generate connection status true`() {
        val status = viewModel.findStatus()
        Assert.assertTrue(viewModel.currentStatus != status)
    }

    @Test
    fun `check generate connection status false`() {
        val status = viewModel.findStatus()
        Assert.assertFalse(viewModel.currentStatus == status)
    }

    @Test
    fun `check connection true`() {
        viewModel.currentStatus = NetworkStatus.ConnectionEstablished
        Assert.assertTrue(viewModel.checkConnection())
    }

    @Test
    fun `check connection false`() {
        viewModel.currentStatus = NetworkStatus.ConnectionError
        Assert.assertFalse(viewModel.checkConnection())
    }
}

