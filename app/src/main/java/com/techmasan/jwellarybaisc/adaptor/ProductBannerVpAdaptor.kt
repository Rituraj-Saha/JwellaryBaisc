package com.techmasan.jwellarybaisc.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import com.techmasan.jwellarybaisc.R


class ProductBannerVpAdaptor(var context: Context,var images:List<String>): PagerAdapter() {

    lateinit var layoutInflatter:LayoutInflater



    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflatter = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view:View = layoutInflatter.inflate(R.layout.item_banner_view_pagger,null)
        var vpimageView = view.findViewById<ImageView>(R.id.vpimageView)
//        var imageUri = (Uri.Builder())
//            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
//            .authority(context.resources.getResourcePackageName(R.drawable.banner))
//            .appendPath(context.resources.getResourceTypeName(R.drawable.banner))
//            .appendPath(context.resources.getResourceEntryName(R.drawable.banner))
//            .build()
        Picasso.get().load(images[position]).into(vpimageView);
        var vp = container as ViewPager
        vp.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}