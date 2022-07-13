package com.example.dubaipractical.ui.home.fragment

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dubaipractical.MyApplication
import com.example.dubaipractical.api.ApiExceptions
import com.example.dubaipractical.api.NoInternetException
import com.example.dubaipractical.data.db.table.EmpTable
import com.example.dubaipractical.data.model.EmpModel
import com.example.dubaipractical.data.repository.HomeRepository
import com.example.dubaipractical.utils.Coroutines
import com.example.dubaipractical.utils.GlobalMethods
import com.example.dubaipractical.utils.PrefUtils
import com.google.gson.Gson
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val application: MyApplication,
    private val repository: HomeRepository,
    private val globalMethods: GlobalMethods,
    private val prefUtils: PrefUtils
) : AndroidViewModel(application) {

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> get() = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> get() = _onMessageError

    private val _empList: MutableLiveData<List<EmpModel.EmployesModelItem>> =
        MutableLiveData<List<EmpModel.EmployesModelItem>>()
    val empList: LiveData<List<EmpModel.EmployesModelItem>>
        get() = _empList

    private val _lastSyncTime: MutableLiveData<String> =
        MutableLiveData<String>()
    val lastSyncTime: LiveData<String>
        get() = _lastSyncTime

    init {
        callMainCategory()
    }

    fun callMainCategory() {
        Coroutines.main {
            if (globalMethods.isInternetAvailable(application)) {
                try {
                    _isViewLoading.postValue(true)
                    val apiResponse = repository.callEmpList()
                    _isViewLoading.postValue(false)
                    _empList.postValue(apiResponse)
                    //Save to database.
                    if(apiResponse.size > 0) {
                        prefUtils.saveLastSyncTime(globalMethods.getCurrentDateAndTime())
                        Coroutines.io {
                            val gson = Gson()
                            val json = gson.toJson(apiResponse)
                            insetEmpListToDB(EmpTable(null, json.toString()))
                            getSyncTime()
                        }
                    }
                } catch (e: ApiExceptions) {
                    _isViewLoading.postValue(false)
                    _onMessageError.postValue(e.message)
                    getDataFromDB()
                } catch (e: NoInternetException) {
                    _isViewLoading.postValue(false)
                    _onMessageError.postValue(e.message)
                    getDataFromDB()
                }
            } else {
                getDataFromDB()
            }
        }
    }

    fun getSyncTime() {
        _lastSyncTime.postValue(prefUtils.getLastSyncTime())
    }

    suspend fun getDataFromDB() {
        val data = repository.getItemListOffline()
        if (data != null) {
            val gson = Gson()
            val testModel: EmpModel = gson.fromJson(data.data, EmpModel::class.java)
            _empList.postValue(testModel)
            getSyncTime()
        }
    }
    suspend fun insetEmpListToDB(data: EmpTable) {
        Coroutines.io {
            repository.insertItemToDB(data)
        }
    }
}
