package com.example.dubaipractical.data.repository

import com.example.dubaipractical.api.SafeAPIRequest
import com.example.dubaipractical.api.WebServiceInterface
import com.example.dubaipractical.data.db.dao.EmpListDao
import com.example.dubaipractical.data.db.table.EmpTable
import com.example.dubaipractical.data.model.EmpModel
import javax.inject.Inject

class HomeRepository@Inject constructor(
    private val webServiceInterface: WebServiceInterface,
    private val dao: EmpListDao,
) : SafeAPIRequest() {

    suspend fun callEmpList(): EmpModel {
        return apiRequest {
            webServiceInterface.callEmpList()
        }
    }

    suspend fun getItemListOffline(): EmpTable {
        val model = dao.getEmpList()
        return model
    }

    suspend fun insertItemToDB(data: EmpTable) : Long {
        return dao.insertEmpList(data)
    }
}