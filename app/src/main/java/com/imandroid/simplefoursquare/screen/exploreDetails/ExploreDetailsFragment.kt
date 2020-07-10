package com.imandroid.simplefoursquare.screen.exploreDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.imandroid.simplefoursquare.R
import com.imandroid.simplefoursquare.data.ExploreRepository
import com.imandroid.simplefoursquare.data.db.DatabaseGenerator
import com.imandroid.simplefoursquare.data.db.ExploreDbDataImpl
import com.imandroid.simplefoursquare.data.network.ExploreApiDataImpl
import com.imandroid.simplefoursquare.data.sharedPref.SharedPrefHelper
import com.imandroid.simplefoursquare.databinding.ExploreDetailsFragmentBinding
import com.imandroid.simplefoursquare.domain.ExploreModel
import com.imandroid.simplefoursquare.screen.exploreViewModel.ExploreSharedViewModel
import com.imandroid.simplefoursquare.screen.exploreViewModel.ExploreSharedViewModelFactory
import com.imandroid.simplefoursquare.util.ResourceProvider
import com.imandroid.simplefoursquare.util.extension.action
import com.imandroid.simplefoursquare.util.extension.snack
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import timber.log.Timber


class ExploreDetailsFragment : Fragment() {
    private fun getFactory(): ExploreSharedViewModelFactory {

        return ExploreSharedViewModelFactory(
            repository = ExploreRepository(
                api = ExploreApiDataImpl()
                ,
                db = ExploreDbDataImpl(DatabaseGenerator.getInstance(requireContext()).exploreDao)
                ,
                sharedPrefHelper = SharedPrefHelper.getInstance(requireContext())
                , errorListener = { errorHandling(it) }
            )
        )
    }
    private val sharedViewModel: ExploreSharedViewModel by activityViewModels {getFactory()}
    lateinit var exploreModel: ExploreModel

    lateinit var binding: ExploreDetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.explore_details_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        exploreModel = ExploreDetailsFragmentArgs.fromBundle(arguments!!).explore
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setExploreData(exploreModel)

        sharedViewModel.getExploreById(explore_id = exploreModel.explore_id)

        sharedViewModel.explore.observe(viewLifecycleOwner, Observer {
            Timber.i("received explore details in fragment observer... likes -> ${it.likes}")
            if(it.explore_id==exploreModel.explore_id){
                //set fresh data
                setExploreData(it)
            }

        })

    }

    @SuppressLint("SetTextI18n")
    private fun setExploreData(exploreModel: ExploreModel) {
        binding.txtTitle.text = exploreModel.seperateName()
        binding.txtLocation.text = exploreModel.city
        binding.txtSubTitle.text = exploreModel.address
        binding.txtLike.text = exploreModel.likes
        binding.txtRate.text = exploreModel.rating

            binding.txtLink.setOnClickListener {
                if (exploreModel.shortUrl?.isNotEmpty()!!){
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(exploreModel.shortUrl)))
                }else{
                    errorHandling("")
                }
            }
            binding.txtAllTips.setOnClickListener {
                if (exploreModel.shortUrl?.isNotEmpty()!!){
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(exploreModel.shortUrl)))
                }else{
                    errorHandling("")
                }
            }



        binding.txtOpenMap.setOnClickListener {

            val gmmIntentUri = Uri.parse("geo:${exploreModel.lat},${exploreModel.lng}}?q=${exploreModel.name}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(requireActivity().packageManager)?.let {
                startActivity(mapIntent)
            }
        }

        if (exploreModel.photos?.isNotEmpty()!!) {
            Timber.i("😈 ${exploreModel.photos}")
            Picasso.get().load(exploreModel.photos!![0]).placeholder(R.drawable.placeholder)
                .into(binding.imgHeader)
        }
        if( exploreModel.categories.isNotEmpty()){
            binding.txtCategory.text =exploreModel.categories[0].name
        }
        if( exploreModel.tips.isNotEmpty()){
            val firstTip = exploreModel.tips[0]
            binding.txtTip.text = firstTip.message
            binding.txtNameTip.text= firstTip.fullName

            Picasso.get()
                .load(firstTip.userAvatarUrl)
                .transform(CropCircleTransformation())
                .placeholder(R.drawable.ic_profile)
                .into(binding.imgUserTip)
        }
    }

    private fun errorHandling(message:String){
        Timber.e(message)
        requireActivity().findViewById<View>(android.R.id.content).snack(R.string.error_occured_during_request_data){
            action(R.string.try_again) { sharedViewModel.getExploreById(explore_id = exploreModel.explore_id) }
        }
    }

}