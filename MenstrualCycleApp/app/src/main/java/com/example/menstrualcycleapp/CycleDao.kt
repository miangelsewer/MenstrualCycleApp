package com.example.menstrualcycleapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CycleDao {
    @Insert
    suspend fun insert(entry: CycleEntity)

    @Query("SELECT * FROM cycle_entries ORDER BY startDate DESC")
    fun getAllEntries(): LiveData<List<CycleEntity>>
}