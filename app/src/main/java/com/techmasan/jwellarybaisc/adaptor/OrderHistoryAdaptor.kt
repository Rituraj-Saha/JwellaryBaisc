package com.techmasan.jwellarybaisc.adaptor

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.techmasan.jwellarybaisc.databinding.ItemOrderhistoryBinding
import com.techmasan.jwellarybaisc.databinding.ItemProductDisplayOrderBinding
import com.techmasan.jwellarybaisc.networkConfig.data.OrderHistoryOrderValue
import com.techmasan.jwellarybaisc.networkConfig.data.OrderHistoryResponse
import java.util.Collections


class OrderHistoryAdaptor(var mList:List<OrderHistoryResponse>, var context: Context, var activity: Activity):RecyclerView.Adapter<OrderHistoryAdaptor.ViewHolder>() {
    val TAG ="OrderHistoryAdaptor"
   inner class ViewHolder(val binding: ItemOrderhistoryBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderhistoryBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = mList[position]
        holder.binding.txtOid.text = data.oid.toString()
        holder.binding.txtphoneNumber.text = data.phoneNumber
        holder.binding.txtTotalPrice.text = data.totalPrice.toString()
        holder.binding.txtorderDate.text = data.orderDate
        holder.binding.txtAddress.text = data.address
        holder.binding.txtEmail.text = data.email
        holder.binding.txtStatus.text = data.status
        holder.binding.txtpaymentMethod.text = data.paymentMethod
        var orderValue = data.orderValue.replace("\\","");
        Log.d(TAG,orderValue)
        var gson = Gson()
        try {
            var mOrderValue: Array<OrderHistoryOrderValue> = gson.fromJson(
                orderValue,
                Array<OrderHistoryOrderValue>::class.java
            )
            if(mOrderValue!=null) {
                for(i in mOrderValue!!)
                {
                    addLayout(holder.binding.linOrders as ViewGroup,i)
                    Log.d(TAG,i.toString());
                }
            }
        }
        catch (e:Exception){
            Log.e(TAG,e.message.toString());
        }

    }
    private fun addLayout(viewGroup: ViewGroup,orderHistoryOrderValue: OrderHistoryOrderValue) {
        var binding:ItemProductDisplayOrderBinding = ItemProductDisplayOrderBinding.inflate(
            LayoutInflater.from(context), viewGroup.parent as ViewGroup?,false)
        val layout2: View = binding.root
        binding.requestQty.text = orderHistoryOrderValue.requestQty.toString()
        binding.txtTitle.text = orderHistoryOrderValue.pname
        Glide.with(context).load(orderHistoryOrderValue.thumbnail).into(binding.imgProduct)
        viewGroup.addView(layout2)
    }
}