package com.techmasan.jwellarybaisc.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techmasan.jwellarybaisc.LoginActivity
import com.techmasan.jwellarybaisc.R
import com.techmasan.jwellarybaisc.Util
import com.techmasan.jwellarybaisc.binding
import com.techmasan.jwellarybaisc.databinding.FragmentSignUpBinding
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.User
import com.techmasan.jwellarybaisc.networkConfig.viewModels.AddNewUserViewModel
import com.techmasan.jwellarybaisc.networkConfig.viewModels.GenerateTokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SignUpFragment : Fragment() {
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
    lateinit var signUpBinding: FragmentSignUpBinding
    private val addNewUserViewModel: AddNewUserViewModel by viewModels()
    lateinit var progress: ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var loginActivity = this.activity as LoginActivity

        progress = Util.showProgress(this.requireContext(),"New User Registration","in progres..")

        signUpBinding = FragmentSignUpBinding.inflate(layoutInflater)
        val view = signUpBinding.root
        signUpBinding.txtProceed.setOnClickListener {

           if(Util.signUpValidator(signUpBinding.tiName,
                signUpBinding.tiEmail,
                signUpBinding.tiPhoneNumber,
                signUpBinding.tinPhoneNumber,
                signUpBinding.tiAddressLine,
                signUpBinding.tiPincode,
                signUpBinding.tiState,
                this.requireActivity())) {
               var user = User(
                   signUpBinding.tiName.text.toString(),
                   signUpBinding.tiEmail.text.toString(),
                   signUpBinding.tiPhoneNumber.text.toString(),
                   signUpBinding.tiAddressLine.text.toString(),
                   signUpBinding.tiPincode.text.toString(),
                   signUpBinding.tiState.text.toString())
               lifecycleScope.launch {
                   Log.d("NewUser::SignUp: ","user is: "+user)
                   addNewUserViewModel.sendAddNewRequest(user);
               }


           }
        }

        addNewUserViewModel.addNewUser.observe(this.requireActivity()){
            when(it){
                is NetworkResult.Loading -> {
                    progress.show()
                }
                is NetworkResult.Failure -> {
                    Util.mToast(this.requireActivity(),"New User Failed to Add Check For Login")
                    progress.dismiss()
                }
                is NetworkResult.Success -> {
                    progress.dismiss()
                    Util.mToast(this.requireActivity(),"New User added successfully")
                    loginActivity.switchToOtpFragment(signUpBinding.tiPhoneNumber.text.toString())
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
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}