package com.example.mvvmwithdaggerhilt.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvmwithdaggerhilt.R
import com.example.mvvmwithdaggerhilt.databinding.FragmentRegistrationBinding
import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import com.example.mvvmwithdaggerhilt.ui.viewmodel.AuthViewModel
import com.example.mvvmwithdaggerhilt.utils.NetworkResult
import com.example.mvvmwithdaggerhilt.utils.Utils.setLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private val TAG = this.javaClass.name
    lateinit var binding: FragmentRegistrationBinding
    private val viewModel : AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        binding.btnRegistration.setOnClickListener{
            val edtEmail = binding.edtEmail.text.toString()
            val edtUsername = binding.edtUsername.text.toString()
            val edtPassword = binding.edtPassword.text.toString()
            val validation = viewModel.validateCredentials(edtEmail, edtUsername, edtPassword, false)
            if (validation.first){
                val userRequest = UserRequest(email = edtEmail, username = edtUsername, password = edtPassword)
                /*val address = Address("Gandhinagar", "Gujarat")
                val sectorList = ArrayList<Sectors>()
                sectorList.add(Sectors("Sector 1"))
                sectorList.add(Sectors("Sector 2"))
                sectorList.add(Sectors("Sector 3"))
                sectorList.add(Sectors("Sector 4"))
                sectorList.add(Sectors("Sector 5"))
                val personData = PersonModel(username = edtUsername, password = edtPassword, address = address,
                    sector = sectorList
                )
                CoroutineScope(Dispatchers.IO).launch{
                    AppDatabase.getInstance(requireContext()).personDao().insertUser(personData)
                    val user = AppDatabase.getInstance(requireContext()).personDao().getUser()
                    //Utils.setLog("UserData", user.get(0).sector.toString())
                }*/
                registerUser(userRequest)
                //findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }else{
                Toast.makeText(requireContext(), validation.second, Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    private fun registerUser(userRequest: UserRequest) {
        lifecycleScope.launch {
            viewModel.registerUser.collect{
                binding.pbProgress.isVisible = false
                when(it){
                    is NetworkResult.Error -> {"Error="+setLog(TAG, it.data.toString())
                        cancel()}
                    is NetworkResult.Loading -> {binding.pbProgress.isVisible = true}
                    is NetworkResult.Success -> {setLog(TAG, "Success="+it.data.toString())
                        cancel()}
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.registerUser(userRequest)
        }

    }
}