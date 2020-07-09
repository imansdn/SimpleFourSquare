package com.imandroid.simplefoursquare.screen.exploreViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imandroid.simplefoursquare.data.ExploreRepository
import com.imandroid.simplefoursquare.domain.ExploreModel
import com.imandroid.simplefoursquare.util.ResourceProvider
import com.imandroid.simplefoursquare.util.SAMPLE_LAT_LNG
import com.imandroid.simplefoursquare.util.extension.disposedBy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception

class ExploreSharedViewModel(private val repository: ExploreRepository) : ViewModel() {
    var bag = CompositeDisposable()

     var explores = mutableListOf<ExploreModel>()


    private val _allExplores = MutableLiveData<List<ExploreModel>>()
    val allExplores: LiveData<List<ExploreModel>>
        get() = _allExplores

    private val _explore = MutableLiveData<ExploreModel>()
    val explore: LiveData<ExploreModel>
        get() = _explore

    private val _event = MutableLiveData<Int>()
    val event: LiveData<Int>
        get() = _event

    var currentPage = 0
    var isLoading = true


     fun getAllExplores(isNeedClear:Boolean=false, latlong:String, pageNumber:Int=currentPage){
        isLoading = true
         repository.getAllExplores(isNeedClear = isNeedClear,latlong = latlong,pageNumber =pageNumber.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ success ->
            Timber.i("received success getAllExplores -> ${success.size}")
            _allExplores.postValue(success)

        },{error ->
            Timber.i("received error getAllPatient -> ${error.message}")
            _event.postValue(0)

        }).disposedBy(bag)
    }

    fun getExploreById(explore_id:String){
        repository.getExploreById(explore_id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ freshExploreModel ->
                Timber.i("received success getExploreDetails -> ${freshExploreModel.name} and likes -> ${freshExploreModel.likes}" )
                _explore.postValue(freshExploreModel)
                try {
                    Timber.i("explores size = ${explores.size}")
                    explores.mapIndexed { index, exploreModel ->
                        if (exploreModel.explore_id==freshExploreModel.explore_id){
                            explores[index] = freshExploreModel
                        }
                    }

                }catch (e:Exception){
                    Timber.e("received error getExploreDetails -> "+e.message)
                    _event.postValue(0)

                }

            },{error ->
                Timber.e("received error getExploreDetails -> "+error.message)
                _event.postValue(50)

            }).disposedBy(bag)
    }


    override fun onCleared() {
        super.onCleared()
        Timber.i("onCleared")
        bag.dispose()
        bag.clear()
        repository.clear()
    }
}