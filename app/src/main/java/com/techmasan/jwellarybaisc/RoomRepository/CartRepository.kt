package com.techmasan.jwellarybaisc.RoomRepository

import androidx.lifecycle.LiveData
import com.techmasan.jwellarybaisc.Entity.Cart
import com.techmasan.jwellarybaisc.RoomConiguration.CartDao

class CartRepository(private val cartDao: CartDao) {
    // on below line we are creating a variable for our list
    // and we are getting all the notes from our DAO class.
    val allNotes: LiveData<List<Cart>> = cartDao.getAllCart()

    // on below line we are creating an insert method
    // for adding the note to our database.
    suspend fun insert(cart: Cart) {
        cartDao.insert(cart)
    }

    // on below line we are creating a delete method
    // for deleting our note from database.
    suspend fun delete(cart: Cart){
        cartDao.delete(cart)
    }

    // on below line we are creating a update method for
    // updating our note from database.
    suspend fun update(cart: Cart){
        cartDao.update(cart)
    }
}