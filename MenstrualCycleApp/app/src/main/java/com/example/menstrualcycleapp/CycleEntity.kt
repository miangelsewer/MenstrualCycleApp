package com.example.menstrualcycleapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycle_entries")
data class CycleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startDate: String,
    val cycleLength: Int,
    val flowLevel: String,
    val symptoms: String,
    val notes: String?
)
