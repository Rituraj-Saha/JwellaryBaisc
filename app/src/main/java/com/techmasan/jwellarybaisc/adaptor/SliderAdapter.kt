package com.techmasan.jwellarybaisc.adaptor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.techmasan.jwellarybaisc.CartActivity
import com.techmasan.jwellarybaisc.Entity.Cart
import com.techmasan.jwellarybaisc.LoginActivity
import com.techmasan.jwellarybaisc.MainActivity

import com.techmasan.jwellarybaisc.R
import com.techmasan.jwellarybaisc.Util
import com.techmasan.jwellarybaisc.model.Grid1Home

import com.techmasan.jwellarybaisc.model.SliderItemModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.logging.Logger

class SliderAdapter(private val mList: ArrayList<Grid1Home>, private val viewPager2: ViewPager2, private val context:Context,private val activity:Activity,var addToCartInterface:AddToCartInterface):RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    lateinit var _mList: ArrayList<Grid1Home>

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
        val data = mList[position]
    //    Log.d("imageString", itemsViewModel.img);
//        Picasso.get().load(itemsViewModel.img).into(holder.imageView);
        Glide.with(context).load(data.image).into(holder.imageView)
        holder.imageView.setOnClickListener {
            var dialog = Util.makeDialog(R.layout.item_product_dialog, activity)
            var imgClose: ImageView = dialog.findViewById(R.id.imgClose)
            var vpProductBanner: ViewPager = dialog.findViewById(R.id.vpProductBanner)
            var SliderDots: LinearLayout = dialog.findViewById(R.id.SliderDots)
            imgClose.setOnClickListener {
                dialog.dismiss()
            }
            var viewPagerAdapter = ProductBannerVpAdaptor(context, data.imageList)
            vpProductBanner.adapter = viewPagerAdapter
            var dotscount = viewPagerAdapter.getCount();
            var dots = ArrayList<ImageView>();

            for (i in 0..dotscount - 1) {
                dots.add(ImageView(context))
                dots[i].setImageDrawable(context.getDrawable(R.drawable.non_active_dot))
                var params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(
                    8, 0, 8, 0
                )
                SliderDots.addView(dots[i], params)

            }
            dots[0].setImageDrawable(context.getDrawable(R.drawable.active_dot))
            vpProductBanner.addOnPageChangeListener(object :
                ViewPager.SimpleOnPageChangeListener() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                }

                override fun onPageSelected(position: Int) {
                    for (i in 0 until dotscount) {
                        dots[i].setImageDrawable(context.getDrawable(R.drawable.non_active_dot))
                    }

                    dots[position].setImageDrawable(context.getDrawable(R.drawable.active_dot))

                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })

            var txtAddCart: TextView = dialog.findViewById(R.id.txtAddCart)
            var linQtySetter: LinearLayout = dialog.findViewById(R.id.linQtySetter)
            var txtMinus: TextView = dialog.findViewById(R.id.txtMinus)
            var txtQty: TextView = dialog.findViewById(R.id.txtQty)
            var txtPlus: TextView = dialog.findViewById(R.id.txtPlus)
            var linCart: LinearLayout = dialog.findViewById(R.id.linCart)
            var txtPrice: TextView = dialog.findViewById(R.id.txtPrice)


            var txtProductName: TextView = dialog.findViewById(R.id.txtProductName)
            var txtDesc: TextView = dialog.findViewById(R.id.txtDesc)
            var txtBasePrice: TextView = dialog.findViewById(R.id.txtBasePrice)
            var txtDialogPrice: TextView = dialog.findViewById(R.id.txtPrice)
            var discountTxt: TextView = dialog.findViewById(R.id.saveTxt)

            txtProductName.text = data.title
            txtDesc.text = data.desc
            txtBasePrice.text = data.price
            txtDialogPrice.text = data.actualPrice
            discountTxt.text = data.discount



            txtAddCart.setOnClickListener {
                txtQty.text = "1"
                linQtySetter.visibility = View.VISIBLE
                linCart.visibility = View.GONE
            }
            txtPlus.setOnClickListener {
                if(data.stockQty > Integer.parseInt(txtQty.text.toString()))
                    txtQty.text = (Integer.parseInt(txtQty.text.toString()) + 1).toString()
                else
                    Util.mToast(activity,"Max Stock Reached..")
            }
            txtMinus.setOnClickListener {
                var tempQty = Integer.parseInt(txtQty.text.toString()) - 1
                if (tempQty == 0) {
                    txtQty.text = tempQty.toString()
                    linQtySetter.visibility = View.GONE
                    linCart.visibility = View.VISIBLE
                } else
                    txtQty.text = (Integer.parseInt(txtQty.text.toString()) - 1).toString()
            }

            var txtProceed: TextView = dialog.findViewById(R.id.txtProceed)



            txtProceed.setOnClickListener {
                if (txtQty.text.toString().equals("")||Integer.parseInt(txtQty.text.toString())<=0) {
                    Util.mToast(activity, "Quantity is empty")
                }
                else{
                    if (Util.isLogin(activity)) {
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                        val currentDateAndTime: String = sdf.format(Date())
                        Log.d("roomlist","about to call addCart")

                        try {

                            val totalPrice: Double = txtQty.text.toString().toDouble() *
                                    txtPrice.text.toString().substring(1).toDouble()
                            Log.d("roomlist","about to call addCart"+"pid: "+ data.pid+", Date: "+
                                    currentDateAndTime+", qty: "+ Integer.parseInt(txtQty.text.toString())+",price: "+totalPrice)
                            var cart = Cart(
                                data.pid,
                                data.title,
                                data.image,
                                currentDateAndTime,
                                Integer.parseInt(txtQty.text.toString()),
                                totalPrice,
                                data.actualPrice.substring(1).toDouble(),
                                data.discount.replace("% Off","").toDouble(),
                                txtPrice.text.toString().substring(1).toDouble()
                            )

                            addToCartInterface.addToCart(cart)
                        }
                        catch (e:Exception){
                            Log.e("roomlist","about to call addCart"+e.message)
                        }

                        dialog.dismiss()

                        var intent = Intent(activity, CartActivity::class.java)
                        activity.startActivity(intent)

                    } else {
                        var intent = Intent(activity, LoginActivity::class.java)
                        activity.startActivity(intent)
                        activity.finish()
                    }
                }
            }
            var txtContinue: TextView = dialog.findViewById(R.id.txtContinue)

            txtContinue.setOnClickListener {
                if (txtQty.text.toString()
                        .equals("") || Integer.parseInt(txtQty.text.toString()) <= 0
                ) {
                    var intent = Intent(activity, MainActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                } else {
                    if (Util.isLogin(activity)) {
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                        val currentDateAndTime: String = sdf.format(Date())
                        Log.d("roomlist", "about to call addCart")

                        try {

                            val totalPrice: Double = txtQty.text.toString().toDouble() *
                                    txtPrice.text.toString().substring(1).toDouble()
                            Log.d(
                                "roomlist",
                                "about to call addCart" + "pid: " + data.pid + ", Date: " +
                                        currentDateAndTime + ", qty: " + Integer.parseInt(txtQty.text.toString()) + ",price: " + totalPrice
                            )
                            var cart = Cart(
                                data.pid,
                                data.title,
                                data.image,
                                currentDateAndTime,
                                Integer.parseInt(txtQty.text.toString()),
                                totalPrice,
                                data.actualPrice.substring(1).toDouble(),
                                data.discount.replace("% Off","").toDouble(),
                                txtPrice.text.toString().substring(1).toDouble()
                            )

                            addToCartInterface.addToCart(cart)
                        } catch (e: Exception) {
                            Log.e("roomlist", "about to call addCart" + e.message)
                        }

                        dialog.dismiss()

                        var intent = Intent(activity, MainActivity::class.java)
                        activity.startActivity(intent)
                        activity.finish()

                    } else {
                        var intent = Intent(activity, LoginActivity::class.java)
                        activity.startActivity(intent)
                        activity.finish()
                    }
                }
            }
            dialog.show()
        }
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
