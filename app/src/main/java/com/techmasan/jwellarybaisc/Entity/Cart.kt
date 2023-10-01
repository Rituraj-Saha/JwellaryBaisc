package com.techmasan.jwellarybaisc.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "cartTable")
class Cart(@ColumnInfo(name="pid") val pid:Long,
           @ColumnInfo(name="pname") val pname:String,
           @ColumnInfo(name="image") val image:String,
           @ColumnInfo(name="date") val date: String,
           @ColumnInfo(name="qty")val qty:Int,
           @ColumnInfo(name="price") val price:Double,
           @ColumnInfo(name="basePrice")
           var basePrice:Double,
           @ColumnInfo(name="discount")
           var discount:Double,
           @ColumnInfo(name="sellPrice")
           var sellPrice:Double,
           ) {
    @PrimaryKey(autoGenerate = true) var id = 0
}