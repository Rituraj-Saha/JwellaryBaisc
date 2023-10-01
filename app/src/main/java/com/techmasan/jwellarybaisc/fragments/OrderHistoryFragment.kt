package com.techmasan.jwellarybaisc.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.techmasan.jwellarybaisc.R
import com.techmasan.jwellarybaisc.SortItems
import com.techmasan.jwellarybaisc.Util
import com.techmasan.jwellarybaisc.adaptor.OrderHistoryAdaptor
import com.techmasan.jwellarybaisc.databinding.FragmentOrderHistoryBinding
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OrderHistoryResponse
import com.techmasan.jwellarybaisc.networkConfig.viewModels.LoadProductViewModel
import com.techmasan.jwellarybaisc.networkConfig.viewModels.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Collections

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentOrderHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderHistoryBinding.inflate(layoutInflater)
        var view = binding.root
        lifecycleScope.launch {
            orderHistoryViewModel.orderHistoryResponse(Util.getUser(this@OrderHistoryFragment.requireActivity()).phoneNumber,Util.getToken(this@OrderHistoryFragment.requireActivity())!!)
        }
        orderHistoryViewModel.orderHistoryResponse.observe(this@OrderHistoryFragment.requireActivity()){
           when(it){
               is NetworkResult.Loading ->{
                   binding.progress.visibility = View.VISIBLE
               }
               is NetworkResult.Success ->{
                   binding.progress.visibility = View.GONE
                   var mLinearLayoutManager = LinearLayoutManager(this@OrderHistoryFragment.requireContext())
                   Collections.sort<OrderHistoryResponse>(it.data.toMutableList(), SortItems())

                   var adapter = OrderHistoryAdaptor(it.data,this@OrderHistoryFragment.requireContext(),this.requireActivity())
                   binding.rcylOrderHistory.layoutManager = mLinearLayoutManager
                   binding.rcylOrderHistory.adapter = adapter
               }
               is NetworkResult.Failure ->{
                   binding.progress.visibility = View.GONE
                   Util.mToast(this@OrderHistoryFragment.requireActivity(),it.errorMessage)
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
         * @return A new instance of fragment OrderHistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}