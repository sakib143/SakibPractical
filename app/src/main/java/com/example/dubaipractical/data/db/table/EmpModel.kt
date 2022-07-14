package com.example.dubaipractical.data.db.table


import com.google.gson.annotations.SerializedName
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emp_table")
data class EmpTable(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("employeeId")
    val employeeId: Int = 0,
    @ColumnInfo(name = "firstName")
    @SerializedName("firstName")
    val firstName: String = "",
    @ColumnInfo(name = "lastName")
    @SerializedName("lastName")
    val lastName: String = "",
    @ColumnInfo(name = "hireDate")
    @SerializedName("hireDate")
    val hireDate: String = "",
    @ColumnInfo(name = "email")
    @SerializedName("email")
    val email: String = ""
)