package com.example.mvvmwithdaggerhilt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mvvmwithdaggerhilt.R
import com.example.mvvmwithdaggerhilt.databinding.FragmentLoginBinding
import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import com.example.mvvmwithdaggerhilt.ui.viewmodel.AuthViewModel
import com.example.mvvmwithdaggerhilt.utils.NetworkResult
import com.example.mvvmwithdaggerhilt.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val TAG = this.javaClass.name
    var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!
    private val viewModel : AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.btnLogin.setOnClickListener{
            val edtUsername = binding.edtUsername.text.toString()
            val edtPassword = binding.edtPassword.text.toString()
            val validation = viewModel.validateCredentials("", edtUsername, edtPassword, true)
            if (validation.first){
                val userRequest = UserRequest(email = edtUsername, password = edtPassword, username = edtUsername)
                loginUser(userRequest)
            }else{
                Toast.makeText(requireContext(), validation.second, Toast.LENGTH_LONG).show()
            }
        }

        binding.btnRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        return binding.root
    }

    private fun loginUser(userRequest: UserRequest) {
        lifecycleScope.launch {
            Utils.setLog(TAG, "login-A-loginUser-${Thread.currentThread().name}")
            viewModel.loginUser.collect{
                Utils.setLog(TAG, "login-B-loginUser-${Thread.currentThread().name}")
                binding.pbProgress.isVisible = false
                when(it){
                    is NetworkResult.Error -> {Utils.setLog(TAG, "Error="+ it.data.toString())
                        cancel()}
                    is NetworkResult.Loading -> {binding.pbProgress.isVisible = true}
                    is NetworkResult.Success -> {
                        Utils.setLog(TAG, "Success=" + it.data.toString())
                        cancel()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            Utils.setLog(TAG, "login-CC-loginUser-${Thread.currentThread().name}")
            viewModel.loginUser(userRequest)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}