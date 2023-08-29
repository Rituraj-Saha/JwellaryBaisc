package com.techmasan.jwellarybaisc.RoomViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.techmasan.jwellarybaisc.Entity.Cart
import com.techmasan.jwellarybaisc.RoomConiguration.CartDatabase
import com.techmasan.jwellarybaisc.RoomRepository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application):AndroidViewModel(application) {
    val allCart:LiveData<List<Cart>>
    val repository:CartRepository

    init {
        val dao = CartDatabase.getDatabase(application).getCartDao()
        repository = CartRepository(dao)
        allCart = repository.allNotes
    }
    // on below line we are creating a new method for deleting a note. In this we are
    // calling a delete method from our repository to delete our note.
    fun deleteCart (cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(cart)
    }

    // on below line we are creating a new method for updating a note. In this we are
    // calling a update method from our repository to update our note.
    fun updateCart(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(cart)
    }


    // on below line we are creating a new method for adding a new note to our database
    // we are calling a method from our repository to add a new note.
    fun addCart(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cart)
    }
}