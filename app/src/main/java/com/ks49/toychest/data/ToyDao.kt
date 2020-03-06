package com.ks49.toychest.data

import android.media.Rating
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the [Toy] class.
 */
@Dao
interface ToyDao {
    @Query( "SELECT * FROM toys ORDER BY name")
    fun getToys(): LiveData<List<Toy>>

    @Query("SELECT * FROM toys WHERE toyUseZoneNumber = :toyUseZoneNumber ORDER BY name")
    fun getToysWithUseZoneNumber(toyUseZoneNumber: Int): LiveData<List<Toy>>

    @Query("SELECT * FROM toys WHERE toyRating = :toyRating ORDER BY name")
    fun getToysWithRating(toyRating: Int): LiveData<List<Toy>>

    @Query("SELECT * FROM toys WHERE id = :toyId")
    fun getToy(toyId: String): LiveData<Toy>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(toys: List<Toy>)
}