package com.techmasan.jwellarybaisc.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techmasan.jwellarybaisc.R
import com.techmasan.jwellarybaisc.Util
import com.techmasan.jwellarybaisc.databinding.FragmentProfileBinding
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.UserUpdate
import com.techmasan.jwellarybaisc.networkConfig.viewModels.UpdateUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment() {
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
    lateinit var binding:FragmentProfileBinding
    private val updateUserViewModel:UpdateUserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding.root
        binding.tiName.setText(Util.getUser(this.requireActivity()).name)
        binding.tiEmail.setText(Util.getUser(this.requireActivity()).email)
        binding.tiAddressLine.setText(Util.getUser(this.requireActivity()).addressLine)
        binding.tiPincode.setText(Util.getUser(this.requireActivity()).pinCode)
        binding.tiState.setText(Util.getUser(this.requireActivity()).state)
        binding.txtProceed.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch {
                updateUserViewModel.updateUser(UserUpdate(Util.getUser(this@ProfileFragment.requireActivity()).phoneNumber,
                    binding.tiAddressLine.text.toString(),
                    binding.tiEmail.text.toString(),
                    binding.tiName.text.toString(),
                    binding.tiPincode.text.toString(),
                    binding.tiState.text.toString()),
                    Util.getToken(this@ProfileFragment.requireActivity())!!)
            }
        }
        updateUserViewModel.userUpdateResponse.observe(this@ProfileFragment.requireActivity()){
            when(it){
                is NetworkResult.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Failure->{
                    binding.progressBar.visibility = View.GONE
                }
                is NetworkResult.Success->{
                    binding.progressBar.visibility = View.GONE
                    Util.reFreshUserData(this@ProfileFragment.requireActivity(),it.data)
                    onStart()
                }

            }
        }
        binding.imgLogout.setOnClickListener {
            Util.mToast(this@ProfileFragment.requireActivity(),"Logging Out")
            Util.logOut(this@ProfileFragment.requireActivity(),this@ProfileFragment.requireContext())
        }
        binding.imgHelpLine.setOnClickListener {
            Util.callIntent(this@ProfileFragment.requireContext(),this@ProfileFragment.requireActivity())
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}