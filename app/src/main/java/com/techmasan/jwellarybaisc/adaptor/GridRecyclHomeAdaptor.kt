package com.techmasan.jwellarybaisc.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.techmasan.jwellarybaisc.R

import com.techmasan.jwellarybaisc.model.Grid1Home


class GridRecyclHomeAdaptor(var mList:List<Grid1Home>, var context: Context) :RecyclerView.Adapter<GridRecyclHomeAdaptor.ViewHolder>(){

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var imgGrid:ImageView = itemView.findViewById(R.id.imgGrid)
        var txtTitle:TextView = itemView.findViewById(R.id.txtTitle)
        var txtPrice:TextView = itemView.findViewById(R.id.txtPrice)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid_home,parent,false)
        return GridRecyclHomeAdaptor.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var data = mList[position]
        Picasso.get().load(data.image).into(holder.imgGrid);
        holder.txtTitle.text = data.title
        holder.txtPrice.text = data.price


    }
}