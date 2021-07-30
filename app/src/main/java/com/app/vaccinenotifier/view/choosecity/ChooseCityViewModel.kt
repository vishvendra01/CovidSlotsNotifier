package com.app.vaccinenotifier.view.choosecity

import androidx.lifecycle.ViewModel
import com.app.vaccinenotifier.data.executor.BaseSchedulerProvider
import com.app.vaccinenotifier.data.model.District
import com.app.vaccinenotifier.data.model.State
import com.app.vaccinenotifier.data.repository.VaccineRepository
import com.app.vaccinenotifier.view.common.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import javax.inject.Inject

class ChooseCityViewModel @Inject constructor(
    private val repository: VaccineRepository,
    private val scheduler: BaseSchedulerProvider
) : ViewModel() {
    val stateLiveData = SingleLiveEvent<List<State>>()
    val cityLiveData = SingleLiveEvent<List<District>>()
    private var navigator: WeakReference<ChooseCityNavigator>? = null
    private val disposeBag = CompositeDisposable()

    fun setNavigator(navigator: ChooseCityNavigator) {
        this.navigator = WeakReference(navigator)
    }

    fun getStates() {
        repository.getStates()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({
                stateLiveData.value = it.states
            }, {

            }).run {
                disposeBag.add(this)
            }
    }

    fun getCities(state: State) {
        repository.getCities(state.id.toString())
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({
                cityLiveData.value = it.districts
            }, {

            }).run {
                disposeBag.add(this)
            }
    }

    fun confirmCity(district: District?) {
        if (district != null) {
            repository.saveCity(district)
            navigator?.get()?.navigateToListing()
        }
    }
}