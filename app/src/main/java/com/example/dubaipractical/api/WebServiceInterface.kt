package com.example.dubaipractical.api

import com.example.dubaipractical.data.model.EmpModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * REST API access points
 */
interface WebServiceInterface {

    @GET("employees")
    suspend fun callEmpList(): Response<EmpModel>

}