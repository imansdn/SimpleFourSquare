package com.imandroid.simplefoursquare.screen.exploresList

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.imandroid.simplefoursquare.R
import com.imandroid.simplefoursquare.data.ExploreRepository
import com.imandroid.simplefoursquare.data.db.DatabaseGenerator
import com.imandroid.simplefoursquare.data.db.ExploreDbDataImpl
import com.imandroid.simplefoursquare.data.network.ExploreApiDataImpl
import com.imandroid.simplefoursquare.data.sharedPref.SharedPrefHelper
import com.imandroid.simplefoursquare.databinding.ExploreListFragmentBinding
import com.imandroid.simplefoursquare.screen.exploreViewModel.ExploreSharedViewModel
import com.imandroid.simplefoursquare.screen.exploreViewModel.ExploreSharedViewModelFactory
import com.imandroid.simplefoursquare.service.TrackingService
import com.imandroid.simplefoursquare.util.*
import com.imandroid.simplefoursquare.util.extension.action
import com.imandroid.simplefoursquare.util.extension.snack
import com.imandroid.simplefoursquare.util.extension.toast
import timber.log.Timber
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class ExploreListFragment : Fragment() {
    private fun getFactory(): ExploreSharedViewModelFactory {
        sharedPrefHelper =  SharedPrefHelper.getInstance(requireContext())
        repository = ExploreRepository(
            api = ExploreApiDataImpl(),
            db = ExploreDbDataImpl(DatabaseGenerator.getInstance(requireContext()).exploreDao),
            sharedPrefHelper = sharedPrefHelper,
            errorListener = {errorHandling(it)}
        )
        return ExploreSharedViewModelFactory(repository)
    }
    private val sharedViewModel: ExploreSharedViewModel by activityViewModels {getFactory()}
    lateinit var binding: ExploreListFragmentBinding
    lateinit var exploresLayoutManager:LinearLayoutManager
    var latlong:String = ""
    val exploreAdapter by lazy {
         ExploreAdapter {
             try {
                 findNavController().navigate(ExploreListFragmentDirections.actionExploreListFragmentToExploreDetailsFragment(sharedViewModel.explores[it]))
             }catch (e: Exception){
                 Timber.e(e)
             }
         }
    }
    lateinit var repository: ExploreRepository
    lateinit var sharedPrefHelper: SharedPrefHelper
    private val broadCastNewMessage = object :BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BROADCAST_UPDATE_EXPLORE_LIST -> {
                    Timber.i("received BROADCAST_UPDATE_EXPLORE_LIST")
                    /** service notify fragment here when need to update explores  */
                    requireActivity().toast("update explores...")
                    sharedViewModel.explores.clear()
                    val isNeedClear = intent.extras?.getBoolean(IS_NEED_CLEAR, false)!!
                    latlong = intent.extras?.getString(LATLONG, "")!!
                    if (latlong.isNotEmpty()) {
                        sharedViewModel.getAllExplores(isNeedClear = isNeedClear,latlong = latlong,pageNumber = 0)
                    }
                }

                BROADCAST_UPDATE_DISTANCE ->{
                    Timber.i("received BROADCAST_UPDATE_DISTANCE")
                    var distance = intent.extras?.getDouble(DISTANCE, 0.0)!!
                    val symbols = DecimalFormatSymbols(Locale.US)
                    val df = DecimalFormat("#.##",symbols)
                    df.roundingMode = RoundingMode.CEILING
                    distance = df.format(distance).toDouble()
                    binding.txtDistance.text="distance with previous point is about $distance meter"

                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (this::binding.isInitialized){
            binding.exploreRecycler.layoutManager?.onSaveInstanceState()
                ?.let { outState.putParcelable(KEY_RECYCLER_STATE, it) }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.explore_list_fragment, container, false)

        /** register the broadcast receiver */
        requireActivity().registerReceiver(broadCastNewMessage, IntentFilter(BROADCAST_UPDATE_EXPLORE_LIST))
        requireActivity().registerReceiver(broadCastNewMessage, IntentFilter(BROADCAST_UPDATE_DISTANCE))




        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel.currentPage=0
        Timber.i("onActivityCreated")
        sharedPrefHelper =  SharedPrefHelper.getInstance(requireContext())
        repository = ExploreRepository(
            api = ExploreApiDataImpl(),
            db = ExploreDbDataImpl(DatabaseGenerator.getInstance(requireContext()).exploreDao),
            sharedPrefHelper = sharedPrefHelper,
            errorListener = {errorHandling(it)}
        )
        binding.txtLocation.text=sharedPrefHelper.read(SH_CITY_NAME,"looking for city...")

        setUpRecyclerView(savedInstanceState)


        sharedViewModel.allExplores.observe(viewLifecycleOwner, Observer {
            Timber.i("get explores in fragment isLoading = ${sharedViewModel.isLoading}")
            if (sharedViewModel.isLoading && it.isNotEmpty()){
                sharedViewModel.isLoading =false
                exploreAdapter.removeLoading()
                sharedViewModel.explores.addAll(it)
            }
            Timber.i("get explores in fragment , size --> ${it.size} and isLoading = ${sharedViewModel.isLoading}")
            exploreAdapter.submit(sharedViewModel.explores)
            if (sharedViewModel.currentPage==0 && it.isNotEmpty() ){
                binding.txtLocation.text=it[0].city
                sharedPrefHelper.write(SH_CITY_NAME,it[0].city)
            }

        })

        sharedViewModel.event.observe(viewLifecycleOwner, Observer {

                if (it==0){
                    //error during get data
                    sharedViewModel.isLoading=false
                    exploreAdapter.removeLoading()
                    showSnack(getString(R.string.error_occured_during_request_data),getString(R.string.try_again)) {
                        if (latlong.isNotEmpty()){
                            sharedViewModel.getAllExplores(latlong=latlong)
                        }

                    }
                    showSnackRecursive(getString(R.string.error_occured_during_request_data),getString(R.string.try_again)) {
                        val visibleItemCount: Int =
                            exploresLayoutManager.findLastVisibleItemPosition() - exploresLayoutManager.findFirstVisibleItemPosition()
                        Timber.i("visibleItemCount = $visibleItemCount")
                        return@showSnackRecursive visibleItemCount == 0
                    }

                }


        })


    }
    private fun setUpRecyclerView(savedInstanceState: Bundle?) {

        binding.exploreRecycler.itemAnimator = DefaultItemAnimator()
        binding.exploreRecycler.adapter = exploreAdapter
        exploresLayoutManager = LinearLayoutManager(requireContext())
        binding.exploreRecycler.layoutManager = exploresLayoutManager
        if (exploreAdapter.currentList.size==0){
            exploreAdapter.addLoading()
        }

        binding.exploreRecycler.addOnScrollListener(object : PaginationListener(exploresLayoutManager) {
            override val isLastPage: Boolean
                get() = sharedPrefHelper.read(SH_IS_LAST_PAGE,false)
            override val isLoading: Boolean
                get() = sharedViewModel.isLoading


            override fun loadMoreItems() {
                Timber.i("🍭triggered OnScrollListener... load more? ")

//                if (viewModel.isMoreDataState()) {

                /** the only time that we set isLoading to true  */
                sharedViewModel.isLoading = true

                /** the only time that we increment the page number is here */
                sharedViewModel.currentPage++
                Timber.i("🍭current page = ${sharedViewModel.currentPage} ")

                /**add an item at the end of list*/
                exploreAdapter.addLoading()

                /** ask to request more data from api */
                if (latlong.isNotEmpty()){
                    sharedViewModel.getAllExplores(latlong=latlong)

                }

//                }
            }


        })

        binding.exploreRecycler.post {
            savedInstanceState?.let {
                val listState = it.getParcelable<Parcelable>(KEY_RECYCLER_STATE)
                binding.exploreRecycler.layoutManager?.onRestoreInstanceState(listState)
            }
        }

    }


    private fun errorHandling(message:String){
        if (sharedViewModel.isLoading){
            sharedViewModel.isLoading =false
            exploreAdapter.removeLoading()
        }
        showSnack(getString(R.string.error_occured_during_request_data),getString(R.string.try_again)) {
            if (latlong.isNotEmpty()) {
                sharedViewModel.getAllExplores(latlong=latlong)
            }

        }
    }


    private fun showSnack(message: String, actionMessage: String, action:()->Unit){
       try {

           requireActivity().findViewById<View>(android.R.id.content).snack(message){
               action(actionMessage) {
                   action()
               }
           }
       }catch (exception:Exception){
           Timber.e("error ->${exception.message}")
       }

    }
    private fun showSnackRecursive(message: String, actionMessage: String, action:()->Boolean){
       try {
           requireActivity().findViewById<View>(android.R.id.content).snack(message){
               action(actionMessage) {
                   if (action()){
                       if (latlong.isNotEmpty()) {
                           sharedViewModel.getAllExplores(latlong=latlong)
                       }
                       showSnackRecursive(message, actionMessage, action)
                   }

               }
           }
       }catch (exception:Exception){
           Timber.e("error ->${exception.message}")
       }
    }


 /** https://developer.android.com/reference/androidx/navigation/fragment/NavHostFragment#onDestroyView()*/
    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(broadCastNewMessage)
        binding.exploreRecycler.adapter=null
 }







}
