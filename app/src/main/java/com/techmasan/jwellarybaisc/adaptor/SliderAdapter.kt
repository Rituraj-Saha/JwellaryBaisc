package com.techmasan.jwellarybaisc.adaptor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView

import com.techmasan.jwellarybaisc.R

import com.techmasan.jwellarybaisc.model.SliderItemModel
import java.util.logging.Logger

class SliderAdapter(private val mList: ArrayList<SliderItemModel>, private val viewPager2: ViewPager2,private val context:Context):RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    lateinit var _mList: ArrayList<SliderItemModel>

    init {
        this._mList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slide_item_container, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        Log.d("imageString", itemsViewModel.img);
//        Picasso.get().load(itemsViewModel.img).into(holder.imageView);
        Glide.with(context).load(itemsViewModel.img).into(holder.imageView)
        if (position == mList.size - 2) {
            viewPager2.post(run);
        }
        //holder.imageView.setImageURI(itemsViewModel.img.toUri());
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageSlide)
    }

    val run = object : Runnable {
        override fun run() {
            _mList.addAll(mList);
            notifyDataSetChanged();
        }
    }

}
