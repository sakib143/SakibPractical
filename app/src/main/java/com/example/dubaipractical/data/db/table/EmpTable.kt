package com.example.dubaipractical.data.db.table

import androidx.annotation.Keep
import androidx.room.*

@Entity(
    tableName = "emp_table"
)

@Keep
data class EmpTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "data")
    val data: String,
)