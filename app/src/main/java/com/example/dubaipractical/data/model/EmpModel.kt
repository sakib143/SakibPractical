package com.example.dubaipractical.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

class EmpModel : ArrayList<EmpModel.EmployesModelItem>(){
    @Keep
    data class EmployesModelItem(
        @SerializedName("email")
        val email: String = "",
        @SerializedName("employeeId")
        val employeeId: Int = 0,
        @SerializedName("firstName")
        val firstName: String = "",
        @SerializedName("hireDate")
        val hireDate: String = "",
        @SerializedName("lastName")
        val lastName: String = ""
    )
}