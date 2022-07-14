package com.example.dubaipractical.ui.home.fragment

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dubaipractical.MyApplication
import com.example.dubaipractical.api.ApiExceptions
import com.example.dubaipractical.api.NoInternetException
import com.example.dubaipractical.data.db.table.EmpTable
import com.example.dubaipractical.data.repository.HomeRepository
import com.example.dubaipractical.utils.Coroutines
import com.example.dubaipractical.utils.GlobalMethods
import com.example.dubaipractical.utils.PrefUtils
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

    private val _empList: MutableLiveData<List<EmpTable>> =
        MutableLiveData<List<EmpTable>>()
    val empList: LiveData<List<EmpTable>>
        get() = _empList

    private val _lastSyncTime: MutableLiveData<String> =
        MutableLiveData<String>().apply { postValue("") }
    val lastSyncTime: LiveData<String>
        get() = _lastSyncTime

    init {
        callGetEmpList()
    }

    fun callGetEmpList() {
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
                        /**
                         * As of now we are adding all list to database but in product app, we will add only require data to database.
                         *  For example, if we have already 500 entries into database and after API call if we have receive 502 list.
                         *  then will add only new two entried into database.
                         */
                        for (i in apiResponse.indices) {
                            insetEmpListToDB(apiResponse.get(i))
                        }
                        getSyncTime()
                    }
                } catch (e: ApiExceptions) {
                    _isViewLoading.postValue(false)
                    _onMessageError.postValue(e.message)
                    getEmpFromDB()
                } catch (e: NoInternetException) {
                    _isViewLoading.postValue(false)
                    _onMessageError.postValue(e.message)
                    getEmpFromDB()
                }
            } else {
                getEmpFromDB()
            }
        }
    }

    /**
     * Save last sync time to share preference to display in home screen
     */
    fun getSyncTime() {
        _lastSyncTime.postValue(prefUtils.getLastSyncTime())
    }

    /**
     * This method is used to fetch data from local database in below conditions
     * If internet is not available then from this class
     * if Workmanager operation finished then refresh list from HomeFragment
     */
    suspend fun getEmpFromDB() {
        val empList: List<EmpTable> = repository.getEmpFromDB()
        _empList.postValue(empList)
        getSyncTime()
    }

    /**
     * Insert emp to Room database
     */
    suspend fun insetEmpListToDB(data: EmpTable) {
        Coroutines.io {
            repository.insertEmpToDB(data)
        }
    }
}
