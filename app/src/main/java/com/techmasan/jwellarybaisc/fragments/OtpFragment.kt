package com.techmasan.jwellarybaisc.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techmasan.jwellarybaisc.LoginActivity
import com.techmasan.jwellarybaisc.Util
import com.techmasan.jwellarybaisc.databinding.FragmentOtpBinding
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OtpRequest
import com.techmasan.jwellarybaisc.networkConfig.viewModels.GenerateTokenViewModel
import com.techmasan.jwellarybaisc.networkConfig.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OtpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class OtpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var phoneNumber: String? = null
    private var param2: String? = null
    ;lateinit var progress:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phoneNumber = it.getString("phoneNumber")
            param2 = it.getString(ARG_PARAM2)
        }
        progress = Util.showProgress(this.requireContext(),"OTP Validation","Otp validation on progess")

    }
    lateinit var binding: FragmentOtpBinding
    private val tokenViewModel: GenerateTokenViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(layoutInflater)
        var view = binding.root
        Util.mToast(this.requireActivity(),"Check this Phone Number For OTP: "+phoneNumber!!)
        binding.txtProceed.setOnClickListener {

            object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.txtCountDownTimer.setText("seconds remaining: " + millisUntilFinished / 1000)
                    // logic to set the EditText could go here
                }

                override fun onFinish() {
                    binding.txtButtonResendOtp.visibility = View.VISIBLE

                }
            }.start()
            //call for token Generation
            lifecycleScope.launch {
                Log.d("phoneNumberFromOtp: ","phoneis: "+phoneNumber!!)
                tokenViewModel.sendTokenRequest(GenerateTokenRequest(phoneNumber!!,binding.tiOtp.text.toString()));
            }
        }
        tokenViewModel.generateTokenResponse.observe(this.requireActivity()){

            when(it){

                is NetworkResult.Loading -> {
//                    binding.progressbar.isVisible = it.isLoading
//                    Logger.log("userNetwork","in loading..")
                    Log.d("otp: ","loading")
                    progress.show()
                }
                is NetworkResult.Failure -> {
                    progress.cancel()
                    Util.mToast(this.requireActivity(),"Validation Failed")
                }
                is  NetworkResult.Success -> {
                    Util.sessionManager(this.requireActivity(),true,it.data.token,it.data.user)
                    progress.cancel()
                    var loginActivity = this.activity as LoginActivity
                    loginActivity.switchToMaiinActivity()
                }
            }
        }
        binding.txtButtonResendOtp.setOnClickListener {
            var loginActivity = this.activity as LoginActivity
            loginActivity.requestOTP(phoneNumber!!)
            binding.txtButtonResendOtp.visibility = View.INVISIBLE
            object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.txtCountDownTimer.setText("seconds remaining: " + millisUntilFinished / 1000)
                    // logic to set the EditText could go here
                }

                override fun onFinish() {
                    binding.txtButtonResendOtp.visibility = View.VISIBLE

                }
            }.start()

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
         * @return A new instance of fragment OtpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OtpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}