package com.example.dubaipractical.data.repository

import com.example.dubaipractical.api.SafeAPIRequest
import com.example.dubaipractical.api.WebServiceInterface
import com.example.dubaipractical.data.db.dao.EmpListDao
import com.example.dubaipractical.data.db.table.EmpTable
import javax.inject.Inject

class HomeRepository@Inject constructor(
    private val webServiceInterface: WebServiceInterface,
    private val dao: EmpListDao,
) : SafeAPIRequest() {

    suspend fun callEmpList(): List<EmpTable> {
        return apiRequest {
            webServiceInterface.callEmpList()
        }
    }

    suspend fun getEmpFromDB(): List<EmpTable> {
        val empList: List<EmpTable> = dao.getEmpList()
        return empList
    }

    suspend fun insertEmpToDB(data: EmpTable) : Long {
        return dao.insertEmpList(data)
    }
}