package com.app.vaccinenotifier.view.listing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.vaccinenotifier.data.executor.BaseSchedulerProvider
import com.app.vaccinenotifier.data.model.VaccineCenter
import com.app.vaccinenotifier.data.repository.VaccineRepository
import com.app.vaccinenotifier.view.common.State
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class VaccineCentersViewModel @Inject constructor(
    private val repository: VaccineRepository,
    private val scheduler: BaseSchedulerProvider
) : ViewModel() {
    val listingLiveData = MutableLiveData<State<List<VaccineCenter>>>()
    private val disposeBag = CompositeDisposable()

    fun loadVaccineCenters(vaccineDate: String) {
        listingLiveData.value = State.loading()

        repository.getVaccineCenters(vaccineDate, repository.getSavedCity()!!.id.toString())
            .map {
                val list = mutableListOf<VaccineCenter>()
                it.centers.forEach { center ->
                    center.sessions.forEach { session ->
                        list.add(center.apply {
                            sessions = listOf(session)
                        })
                    }
                }
                list
            }
            .observeOn(scheduler.ui())
            .subscribeOn(scheduler.io())
            .subscribe({
                listingLiveData.value = State.success(it)
            }, {
                listingLiveData.value = State.error("", it)
            }).run {
                disposeBag.add(this)
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}