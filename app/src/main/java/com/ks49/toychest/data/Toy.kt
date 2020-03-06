package com.ks49.toychest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

@Entity(tableName = "toys")
data class Toy(
    @PrimaryKey @ColumnInfo(name = "id") val toyId: String,
    val name: String,
    val description: String,
    val toyRating: Int,
    val toyUseZoneNumber: Int,
    val useInterval: Int = 7, // How often the toy should be used, in days
    val imageUrl: String = ""
) {
    /**
     * Determines if the toy should be used. Returns true if [since]'s date > date of last
     * use + use interval; false otherwise.
     */
    fun shouldBeUsed(since: Calendar, lastUseDate: Calendar) =
        since > lastUseDate.apply { add(DAY_OF_YEAR, useInterval) }

    override fun toString() = name
}