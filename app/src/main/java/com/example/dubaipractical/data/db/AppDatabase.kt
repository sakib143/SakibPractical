package com.example.dubaipractical.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dubaipractical.data.db.dao.EmpListDao
import com.example.dubaipractical.data.db.table.EmpTable


@Database(
    entities = arrayOf(EmpTable::class),
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getAllEmp(): EmpListDao
}