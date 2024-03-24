package com.cs4520.assignment5.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cs4520.assignment5.database.dao.ProductDao
import com.cs4520.assignment5.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var inst: ProductDatabase? = null

        fun init(context: Context): ProductDatabase {
            if (inst == null) {
                inst =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "product_database",
                    ).build()
            }

            return inst!!
        }

        fun getInstance(): ProductDatabase {
            if (inst == null) {
                throw IllegalStateException("ProductDatabase must be initialized")
            }
            return inst!!
        }
    }
}
