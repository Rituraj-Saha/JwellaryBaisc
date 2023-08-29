package com.techmasan.jwellarybaisc.RoomConiguration

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.techmasan.jwellarybaisc.Entity.Cart

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cart :Cart)

    // below is the delete method
    // for deleting our note.
    @Delete
    suspend fun delete(cart: Cart)

    // below is the method to read all the notes
    // from our database we have specified the query for it.
    // inside the query we are arranging it in ascending
    // order on below line and we are specifying
    // the table name from which
    // we have to get the data.
    @Query("Select * from cartTable order by id ASC")
    fun getAllCart(): LiveData<List<Cart>>

    // below method is use to update the note.
    @Update
    suspend fun update(cart: Cart)
}