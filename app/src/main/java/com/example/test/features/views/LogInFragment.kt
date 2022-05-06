package com.example.test.features.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentLogInBinding
import com.example.test.features.viewmodels.LogInViewModel
import com.example.test.showDialogAlert
import com.example.test.showNetworkDialogAlert

class LogInFragment : Fragment(R.layout.fragment_log_in) {

    private val viewModel by lazy { ViewModelProvider(this).get(LogInViewModel::class.java) }
    private var viewBinding: FragmentLogInBinding? = null
    private var alertDialog: AlertDialog? = null
    private var alertDialogMessageSend: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentLogInBinding.bind(view)
        setOnClickListeners()
        viewModel.message.observe(viewLifecycleOwner) {
            alertDialog?.cancel()
            alertDialog = requireContext().showNetworkDialogAlert(it)
        }
        viewModel.messageSend.observe(viewLifecycleOwner) {
            alertDialogMessageSend?.cancel()
            alertDialogMessageSend = requireContext().showDialogAlert(it)
        }
    }

    private fun setOnClickListeners() {
        viewBinding?.let {
            it.followToSignUpBtn.setOnClickListener { navigateToSingUpFragment() }
            it.loginCheckConnectionBtn.setOnClickListener { viewModel.onCheckConnection() }
            it.sendCommandBtn.setOnClickListener { viewModel.onSendCommands() }
        }
    }

    private fun navigateToSingUpFragment() {
        view?.findNavController()?.navigate(R.id.action_logInFragment_to_signUpFragment)
    }

    override fun onStart() {
        super.onStart()
        viewModel.startGenerateConnectionStatus()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopGenerateConnectionStatus()
    }
}