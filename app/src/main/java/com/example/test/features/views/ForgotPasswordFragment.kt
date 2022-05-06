package com.example.test.features.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.databinding.FragmentForgotPasswordBinding
import com.example.test.features.viewmodels.ForgotPasswordViewModel
import com.example.test.showDialogAlert
import com.example.test.showNetworkDialogAlert

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private val viewModel by lazy { ViewModelProvider(this).get(ForgotPasswordViewModel::class.java) }
    private var viewBinding: FragmentForgotPasswordBinding? = null
    private var alertDialog: AlertDialog? = null
    private var alertDialogMessageSend: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentForgotPasswordBinding.bind(view)
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
            it.passwordCheckConnectionBtn.setOnClickListener { viewModel.onCheckConnection() }
        }
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