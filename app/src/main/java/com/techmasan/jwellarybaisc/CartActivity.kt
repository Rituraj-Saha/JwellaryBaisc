package com.techmasan.jwellarybaisc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.techmasan.jwellarybaisc.Entity.Cart
import com.techmasan.jwellarybaisc.RoomViewModel.CartViewModel
import com.techmasan.jwellarybaisc.adaptor.CartAdapter
import com.techmasan.jwellarybaisc.adaptor.CartClickDeleteInterface
import com.techmasan.jwellarybaisc.databinding.ActivityCartBinding
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OrderRequest
import com.techmasan.jwellarybaisc.networkConfig.data.ProductRequestForOrder
import com.techmasan.jwellarybaisc.networkConfig.viewModels.LoadProductViewModel
import com.techmasan.jwellarybaisc.networkConfig.viewModels.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartActivity : AppCompatActivity(), CartClickDeleteInterface {
    lateinit var viewModal: CartViewModel
    private lateinit var binding: ActivityCartBinding
    private val orderViewModel: OrderViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CartViewModel::class.java)



        var cartAdaptor:CartAdapter = CartAdapter(this,this,binding.totalPrice)
        binding.recyclCart.layoutManager = LinearLayoutManager(this)
        binding.recyclCart.adapter = cartAdaptor

        viewModal.allCart.observe(this, Observer { list ->
            list?.let {
                // on below line we are updating our list.
                cartAdaptor.updateList(it)
            }
        })
        var pd = Util.showProgress(this,"Placing Order","Placing your order")
        var dialog = Util.makeDialog(R.layout.item_order_finilise_dialog,this)
        binding.txtProceed.setOnClickListener {

            if(Util.isLogin(this)){

                dialog.setCancelable(false)
                var etAddress = dialog.findViewById<EditText>(R.id.tiAddressLine)
                var etPin = dialog.findViewById<EditText>(R.id.tipincode)
                var etLandmark = dialog.findViewById<EditText>(R.id.tiLandmark)
                var payRadioGroup = dialog.findViewById<RadioGroup>(R.id.payRadioGroup)
                var rbCod = dialog.findViewById<RadioButton>(R.id.rbCod)
                var rbUpi = dialog.findViewById<RadioButton>(R.id.rbUpi)
                var txtCancel = dialog.findViewById<TextView>(R.id.txtCancel)
                var txtProceed = dialog.findViewById<TextView>(R.id.txtProceed)
                var txtTotalPayable = dialog.findViewById<TextView>(R.id.txtTotalPayable)
                txtTotalPayable.text = binding.totalPrice.text.toString().substring(14)
                var txtexpectedDelivary = dialog.findViewById<TextView>(R.id.txtexpectedDelivary)
                txtexpectedDelivary.text = "Within 7 days"

                etAddress.setText(Util.getUser(this).addressLine)
                etPin.setText(Util.getUser(this).pinCode)

                rbCod.isChecked = true
                var paymentType = "cod"

                payRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // on below line we are getting radio button from our group.

                    when(checkedId){
                        R.id.rbCod->{
                            Util.mToast(this,"Cod is selected")
                            paymentType = "cod"
                        }
                        R.id.rbUpi->{
                            Util.mToast(this,"UPI is selected")
                            paymentType = "upi"
                        }
                    }
                })




                txtCancel.setOnClickListener {
                    dialog.cancel()
                    pd.dismiss()
                }
                txtProceed.setOnClickListener {
                    lifecycleScope.launch {
                        var cartList = cartAdaptor.getCartList()
                        var productRequestList = ArrayList<ProductRequestForOrder>()
                        for(i in cartList){
                            var productRequestForOrder:ProductRequestForOrder = ProductRequestForOrder(i.pid,i.pname,i.image,i.basePrice,i.discount,i.sellPrice,i.qty)
                            productRequestList.add(productRequestForOrder)
                        }


                        var orderRequest:OrderRequest = OrderRequest(Util.getUser(this@CartActivity).phoneNumber,productRequestList,binding.totalPrice.text.toString().substring(15).toDouble(),etAddress.text.toString(),Util.getUser(this@CartActivity).email,paymentType,"NP")
                        //check wheteher token is present and not expired and user is loged in
                        orderViewModel.sendOrderRequest(orderRequest,Util.getToken(this@CartActivity)!!);

                    }

                }

                dialog.show();

            }
            else{
               var intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        orderViewModel.orderResponse.observe(this){
            when(it){
                is NetworkResult.Loading ->{
                    pd.show()
                }
                is NetworkResult.Success ->{
                    Log.d("OrderPlace:","Success")
                   Util.mToast(this@CartActivity,"Order Placed Successfully")
                    pd.dismiss()
                    dialog.dismiss()
                    for(i in cartAdaptor.getCartList()){
                        onDeleteIconClick(i);

                    }
                    cartAdaptor.notifyDataSetChanged()

                }
                is NetworkResult.Failure ->{
                 Log.d("OrderPlace",""+it.errorMessage)
                }
            }
        }




    }


  /*  //Add empty validator or the following field and call in procced button
//    if(!etAddress.text.toString().equals("")) {
//
//    }
//    else
//    {
//        etAddress.setError("Adress can't be Empty",this.getDrawable(R.drawable.baseline_error_24))
//    }
//    if(!etPin.text.toString().equals("")){
//
//    }
//    else
//    {
//        etPin.setError("Pincode can't be Empty",this.getDrawable(R.drawable.baseline_error_24))
//    }
//    if(!etLandmark.text.toString().equals("")){
//
//    }
//    else{
//        etLandmark.setError("Landmark can't be Empty",this.getDrawable(R.drawable.baseline_error_24))
//    }
    */


    override fun onDeleteIconClick(cart: Cart) {
        // in on note click method we are calling delete
        // method from our view modal to delete our not.
        viewModal.deleteCart(cart)
        viewModal.allCart.observe(this,{
            if(it.size==0){
                binding.totalPrice.text = "Total Amount: \u20B9"+0.0
                this.finish()
            }
        })
        // displaying a toast message
//        Util.mToast(this, "${cart.pname} Deleted")

    }



}