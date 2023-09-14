package com.techmasan.jwellarybaisc.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techmasan.jwellarybaisc.LoginActivity
import com.techmasan.jwellarybaisc.R
import com.techmasan.jwellarybaisc.Util
import com.techmasan.jwellarybaisc.databinding.FragmentLoginBinding
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OtpRequest
import com.techmasan.jwellarybaisc.networkConfig.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.logging.Logger

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        binding.txtProceed.setOnClickListener {

            Log.d("otp: ","About to call otp generator")
            if(Util.phoneInputTextSizeCheck(binding.tiPhoneNumber,binding.phoneTextInputLayout,this.requireActivity())){
                lifecycleScope.launch {
                    var uPhone = binding.tiPhoneNumber.text.toString()
                    loginViewModel.sendotpRequest(OtpRequest(uPhone));
                }
            }
        }
        loginViewModel.otpResponse.observe(this.requireActivity()){
            when(it){
                is NetworkResult.Loading -> {
//                    binding.progressbar.isVisible = it.isLoading
//                    Logger.log("userNetwork","in loading..")
                    Log.d("otp: ","loading")
                }

                is NetworkResult.Failure -> {
                    Log.e("otp: ",it.errorMessage)
                    Util.mToast(this.activity as LoginActivity,"No Active User Found Please Consider sign up")
                }
                is  NetworkResult.Success -> {
                   Log.d("otp: ",it.data.otp)
                    var loginActivity = this.activity as LoginActivity
                    loginActivity.switchToOtpFragment(binding.tiPhoneNumber.text.toString())
                }
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}