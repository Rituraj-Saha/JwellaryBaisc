package com.techmasan.jwellarybaisc

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.material.appbar.AppBarLayout
import com.techmasan.jwellarybaisc.Entity.Cart
import com.techmasan.jwellarybaisc.RoomViewModel.CartViewModel
import com.techmasan.jwellarybaisc.adaptor.AddToCartInterface
import com.techmasan.jwellarybaisc.adaptor.GridRecyclHomeAdaptor
import com.techmasan.jwellarybaisc.adaptor.SliderAdapter
import com.techmasan.jwellarybaisc.databinding.ActivityMainBinding
import com.techmasan.jwellarybaisc.model.Grid1Home
import com.techmasan.jwellarybaisc.model.SliderItemModel



class MainActivity : AppCompatActivity() ,AddToCartInterface{
    private lateinit var binding: ActivityMainBinding
    private val sliderHandler = Handler()
    val run = object : Runnable {
        override fun run() {
            binding.viewPagerImageSlider.setCurrentItem(binding.viewPagerImageSlider.getCurrentItem() + 1);
        }
    }

    lateinit var viewModal: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewPager2Implement()
        gridImpementation(this.applicationContext,this)

       binding.bottomNav.add(MeowBottomNavigation.Model(1,R.drawable.baseline_history_24))
        binding.bottomNav.add(MeowBottomNavigation.Model(2,R.drawable.baseline_home_24))
        binding.bottomNav.add(MeowBottomNavigation.Model(3,R.drawable.baseline_person_24))
        binding.bottomNav.show(2)


        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CartViewModel::class.java)

        binding.imgCart.setOnClickListener {
            if(!binding.txtCountcard.text.toString().equals("0")) {
                var intent = Intent(this@MainActivity, CartActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Cart is Empty",Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgCartTbar.setOnClickListener {
            if(!binding.txtCountcardTbar.text.toString().equals("0")) {
                var intent = Intent(this@MainActivity, CartActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Cart is Empty",Toast.LENGTH_SHORT).show()
            }
        }

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

    fun gridImpementation(context: Context,activity:MainActivity){
        var grid1HomeList = ArrayList<Grid1Home>()
        var imageUri2 = (Uri.Builder())
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.jwel))
            .appendPath(resources.getResourceTypeName(R.drawable.jwel))
            .appendPath(resources.getResourceEntryName(R.drawable.jwel))
            .build()

        var imageList = ArrayList<String>()
        imageList.add(imageUri2.toString())
        imageList.add(imageUri2.toString())
        imageList.add(imageUri2.toString())

        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless",imageList))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless",imageList))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless",imageList))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless",imageList))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless",imageList))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless",imageList))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless",imageList))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless",imageList))
        grid1HomeList.add(Grid1Home(1,imageUri2.toString(),"\u20B9 100","0","Neckless",imageList))

        binding.recyclGrid.layoutManager = GridLayoutManager(context,2)
        binding.recyclGrid.adapter = GridRecyclHomeAdaptor(grid1HomeList,context,this,activity)
    }

    override fun addToCart(cart: Cart) {
        viewModal.addCart(cart)
        binding.txtCountcard.visibility = View.VISIBLE
        binding.txtCountcard.text = (Integer.parseInt( binding.txtCountcard.text.toString()) + cart.qty).toString()
        Log.d("roomlist","called")
        viewModal.allCart.observe(this, Observer { list ->
            list?.let {
                // on below line we are updating our list.
                for(i in it) {
                    Log.d("roomList", i.pid.toString())
                }
            }
        })
//        Util.mToast(this,""+binding.txtCountcard.text.toString())
    }
    fun oncollapseBehaviour(){
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            if (Math.abs(verticalOffset)  >= appBarLayout.totalScrollRange) { // collapse
                binding.rltCartTbar.visibility = View.VISIBLE
            } else if (verticalOffset == 0) { // fully expand
                binding.rltCartTbar.visibility = View.GONE
            } else { // scolling

            }
        })
    }
    override fun onStart() {
        super.onStart()
        cartCountSetter()
        oncollapseBehaviour()
    }

    override fun onResume() {
        super.onResume()
        cartCountSetter()
        oncollapseBehaviour()
    }


    fun cartCountSetter(){
        viewModal.allCart.observe(this, Observer { list ->
            list?.let {
                // on below line we are updating our list.
                if(it.size>0)
                {
                    binding.txtCountcard.visibility = View.VISIBLE
                    binding.txtCountcard.text = it.size.toString()
                    binding.txtCountcardTbar.visibility = View.VISIBLE
                    binding.txtCountcardTbar.text = it.size.toString()
                }
                else{
                    binding.txtCountcard.visibility = View.GONE
                    binding.txtCountcard.text = it.size.toString()
                    binding.txtCountcardTbar.visibility = View.GONE
                    binding.txtCountcardTbar.text = it.size.toString()
                }
            }
        })
    }
}