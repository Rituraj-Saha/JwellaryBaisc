package com.techmasan.jwellarybaisc.adaptor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.techmasan.jwellarybaisc.Entity.Cart
import com.techmasan.jwellarybaisc.R

class CartAdapter(val context: Context,
                  val noteClickDeleteInterface: CartClickDeleteInterface,val totalCount:TextView) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    private val allCarts = ArrayList<Cart>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are creating an initializing all our
        // variables which we have added in layout file.
        var imgCart:ImageView = itemView.findViewById(R.id.imgCart)
        var txtId:TextView = itemView.findViewById(R.id.txtId)
        var txtName:TextView = itemView.findViewById(R.id.txtName)
        var txtPrice:TextView = itemView.findViewById(R.id.txtPrice)
        var txtQty:TextView = itemView.findViewById(R.id.txtQty)
        var imgDelete:ImageView = itemView.findViewById(R.id.imgDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_cart,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        var data:Cart = allCarts[position]
        var totalPrce = 0.0;

        for(i in allCarts){
            totalPrce = totalPrce + i.price;
        }
        totalCount.text = "Total Amount: \u20B9"+totalPrce
//        Picasso.get().load(data.image).into(holder.imgCart)
        Glide.with(context).load(data.image).into(holder.imgCart)
        holder.txtName.text = "Product Name: "+data.pname
        holder.txtId.text = "Product ID: "+data.pid.toString()
        holder.txtPrice.text = "Total Price: \u20B9"+data.price.toString()
        holder.txtQty.text = "Product Qantity: "+data.qty.toString()
        holder.imgDelete.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(data)
        }
    }

    override fun getItemCount(): Int {
        return allCarts.size
    }
    fun updateList(newList: List<Cart>) {
        // on below line we are clearing
        // our notes array list
        allCarts.clear()
        // on below line we are adding a
        // new list to our all notes list.
        for(i in newList)
            Log.d("cartList", i.pid.toString())
        allCarts.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }
}
interface CartClickDeleteInterface {
    // creating a method for click
    // action on delete image view.
    fun onDeleteIconClick(cart: Cart)
}