package com.techmasan.jwellarybaisc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.techmasan.jwellarybaisc.Entity.Cart
import com.techmasan.jwellarybaisc.RoomViewModel.CartViewModel
import com.techmasan.jwellarybaisc.adaptor.CartAdapter
import com.techmasan.jwellarybaisc.adaptor.CartClickDeleteInterface
import com.techmasan.jwellarybaisc.databinding.ActivityCartBinding
import com.techmasan.jwellarybaisc.databinding.ActivityMainBinding


class CartActivity : AppCompatActivity(), CartClickDeleteInterface {
    lateinit var viewModal: CartViewModel
    private lateinit var binding: ActivityCartBinding
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

        binding.txtProceed.setOnClickListener {
            if(Util.isLogin()){
                var dialog = Util.makeDialog(R.layout.item_order_finilise_dialog,this)
                dialog.setCancelable(false)
                var etAddress = dialog.findViewById<EditText>(R.id.etAddress)
                var etPin = dialog.findViewById<EditText>(R.id.etPin)
                var etLandmark = dialog.findViewById<EditText>(R.id.etLandmark)
                var payRadioGroup = dialog.findViewById<RadioGroup>(R.id.payRadioGroup)
                var rbCod = dialog.findViewById<RadioButton>(R.id.rbCod)
                var rbUpi = dialog.findViewById<RadioButton>(R.id.rbUpi)
                var txtCancel = dialog.findViewById<TextView>(R.id.txtCancel)
                var txtProceed = dialog.findViewById<TextView>(R.id.txtProceed)
                var txtTotalPayable = dialog.findViewById<TextView>(R.id.txtTotalPayable)
                txtTotalPayable.text = binding.totalPrice.text.toString().substring(14)
                txtCancel.setOnClickListener {
                    dialog.cancel()
                }
                txtProceed.setOnClickListener {
                    Util.mToast(this,"placed")
                    if(!etAddress.text.toString().equals("")) {
                        
                    }
                    else
                    {
                        etAddress.setError("Adress can't be Empty",this.getDrawable(R.drawable.baseline_error_24))
                    }
                    if(!etPin.text.toString().equals("")){

                    }
                    else
                    {
                        etPin.setError("Pincode can't be Empty",this.getDrawable(R.drawable.baseline_error_24))
                    }
                    if(!etLandmark.text.toString().equals("")){

                    }
                    else{
                        etLandmark.setError("Landmark can't be Empty",this.getDrawable(R.drawable.baseline_error_24))
                    }
                }


                dialog.show();

            }
            else{
                Util.mToast(this,"Please Consider Login/Sign up First.")
            }
        }



    }
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