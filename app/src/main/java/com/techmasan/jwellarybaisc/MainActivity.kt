package com.techmasan.jwellarybaisc

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.techmasan.jwellarybaisc.adaptor.GridRecyclHomeAdaptor
import com.techmasan.jwellarybaisc.adaptor.SliderAdapter
import com.techmasan.jwellarybaisc.databinding.ActivityMainBinding
import com.techmasan.jwellarybaisc.model.Grid1Home
import com.techmasan.jwellarybaisc.model.SliderItemModel



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sliderHandler = Handler()
    val run = object : Runnable {
        override fun run() {
            binding.viewPagerImageSlider.setCurrentItem(binding.viewPagerImageSlider.getCurrentItem() + 1);
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewPager2Implement()
        gridImpementation(this.applicationContext)

       binding.bottomNav.add(MeowBottomNavigation.Model(1,R.drawable.baseline_history_24))
        binding.bottomNav.add(MeowBottomNavigation.Model(2,R.drawable.baseline_home_24))
        binding.bottomNav.add(MeowBottomNavigation.Model(3,R.drawable.ic_cart))
        binding.bottomNav.show(2)

    }

    fun viewPager2Implement(){
        val sliderItems = ArrayList<SliderItemModel>()
        var imageUri = (Uri.Builder())
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.banner))
            .appendPath(resources.getResourceTypeName(R.drawable.banner))
            .appendPath(resources.getResourceEntryName(R.drawable.banner))
            .build()


        var imageUri2 = (Uri.Builder())
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.jwel))
            .appendPath(resources.getResourceTypeName(R.drawable.jwel))
            .appendPath(resources.getResourceEntryName(R.drawable.jwel))
            .build()

        sliderItems.add(SliderItemModel(imageUri.toString()))
        sliderItems.add(SliderItemModel(imageUri2.toString()))
        sliderItems.add(SliderItemModel(imageUri.toString()))
        sliderItems.add(SliderItemModel(imageUri2.toString()))
        sliderItems.add(SliderItemModel(imageUri.toString()))
        binding.viewPagerImageSlider.adapter = SliderAdapter(sliderItems,binding.viewPagerImageSlider)

        binding.viewPagerImageSlider.setClipToPadding(false);
        binding.viewPagerImageSlider.setClipChildren(false);
        binding.viewPagerImageSlider.setOffscreenPageLimit(3);

        binding.viewPagerImageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        var compositePageTransformer: CompositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer( MarginPageTransformer(10));
        compositePageTransformer.addTransformer(ViewPager2.PageTransformer { page, position ->
            var r = 1 - Math.abs(position)
            page.setScaleY(0.85f + r * 0.15f);
            page.setScaleX(0.85f + r * 0.28f);
        })

        binding.viewPagerImageSlider.setPageTransformer(compositePageTransformer);


        binding.viewPagerImageSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                super.onPageSelected(position)
                sliderHandler.removeCallbacks(run);
                sliderHandler.postDelayed(run, 4000); // slide duration 2 seconds
            }
        });
        binding.viewPagerImageSlider.setCurrentItem(1)


    }

    fun gridImpementation(context: Context){
        var grid1HomeList = ArrayList<Grid1Home>()
        var imageUri2 = (Uri.Builder())
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.jwel))
            .appendPath(resources.getResourceTypeName(R.drawable.jwel))
            .appendPath(resources.getResourceEntryName(R.drawable.jwel))
            .build()
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless"))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless"))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless"))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless"))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless"))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless"))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless"))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless"))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless"))

        binding.recyclGrid.layoutManager = GridLayoutManager(context,2)
        binding.recyclGrid.adapter = GridRecyclHomeAdaptor(grid1HomeList,context,this)
    }
}