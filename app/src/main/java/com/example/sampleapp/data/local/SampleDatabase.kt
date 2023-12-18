package com.example.sampleapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityEntity::class, CountryEntity::class, HumanEntity::class], version = 1)
abstract class SampleDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao
}