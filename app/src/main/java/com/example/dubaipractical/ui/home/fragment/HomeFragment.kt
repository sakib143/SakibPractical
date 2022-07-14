package com.example.dubaipractical.ui.home.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.dubaipractical.R
import com.example.dubaipractical.base.BaseBindingFragment
import com.example.dubaipractical.databinding.FragmentHomeBinding
import com.example.dubaipractical.utils.Constant
import com.example.dubaipractical.utils.Coroutines
import com.example.dubaipractical.utils.toast
import com.example.dubaipractical.worker.FetchEmpWorker
import javax.inject.Inject

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var viewmodel: HomeViewModel

    override fun layoutId(): Int = R.layout.fragment_home

    override fun initializeBinding(binding: FragmentHomeBinding) {
        binding.lifecycleOwner = this
        binding.viewmodel = viewmodel
        binding.listner = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.onMessageError.observe(requireActivity(),onMessageErrorObserver)
    }

    /**
     * If any error encounter during API call then show toast message
     */
    private val onMessageErrorObserver = Observer<Any> {
        requireActivity().toast(it.toString())
    }

    /**
     * Start API call to get data from server after press sync button.
     * Here we have used Workmanager to fetch data from server even app will get killed.
     */
    fun startWorkmanager() {
       if( globalMethods.isInternetAvailable(requireActivity())) {
           val workManager = WorkManager.getInstance(requireActivity())
           var workRequest: WorkRequest = OneTimeWorkRequest.Builder(FetchEmpWorker::class.java).build()
           workManager.enqueue(workRequest)

           workManager.getWorkInfoByIdLiveData(workRequest.id).observe(requireActivity(), Observer {
               if (it?.state == null)
                   return@Observer
               when (it.state) {
                   WorkInfo.State.SUCCEEDED -> {
                       val successOutputData = it.outputData
                       Coroutines.main {
                           val hasNewDataFound: Boolean = successOutputData.getBoolean(Constant.IS_API_CALLED,false)
                           if(hasNewDataFound) {
                               viewmodel.getDataFromDB()
                           }
                           workManager.cancelWorkById(workRequest.getId());
                       }
                   }
                   WorkInfo.State.FAILED -> {
                   }
               }
           })
        } else {
            requireActivity().toast(Constant.CHECK_INTERNET)
        }
    }
}