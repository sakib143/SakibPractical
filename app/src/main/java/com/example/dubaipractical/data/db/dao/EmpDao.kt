package com.example.dubaipractical.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dubaipractical.data.db.table.EmpTable


@Dao
interface EmpListDao {

    @Query("SELECT * FROM emp_table")
    fun getEmpList(): EmpTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmpList(model: EmpTable) : Long

}