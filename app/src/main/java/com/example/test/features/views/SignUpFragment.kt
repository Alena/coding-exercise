package com.example.test.features.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentSignUpBinding
import com.example.test.features.viewmodels.SignUpViewModel
import com.example.test.showDialogAlert
import com.example.test.showNetworkDialogAlert

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val viewModel by lazy { ViewModelProvider(this).get(SignUpViewModel::class.java) }
    private var viewBinding: FragmentSignUpBinding? = null
    private var alertDialog: AlertDialog? = null
    private var alertDialogMessageSend: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentSignUpBinding.bind(view)
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
            it.followToPasswordBtn.setOnClickListener { navigateToForgotPasswordFragment() }
            it.signUpCheckConnectionBtn.setOnClickListener { viewModel.onCheckConnection() }
        }
    }

    private fun navigateToForgotPasswordFragment() {
        view?.findNavController()?.navigate(R.id.action_signUpFragment_to_forgotPasswordFragment)
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