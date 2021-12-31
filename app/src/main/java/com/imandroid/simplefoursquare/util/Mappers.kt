package com.imandroid.simplefoursquare.util

import com.imandroid.simplefoursquare.data.db.table.CategoryEntity
import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import com.imandroid.simplefoursquare.data.db.table.TipEntity
import com.imandroid.simplefoursquare.data.network.dto.PlaceDetailsDTO
import com.imandroid.simplefoursquare.data.network.dto.PlaceSearchDTO
import com.imandroid.simplefoursquare.domain.CategoryModel
import com.imandroid.simplefoursquare.domain.ExploreModel
import com.imandroid.simplefoursquare.domain.TipModel

fun expEntityToExpModel(input: ExploreEntity):ExploreModel{

    return ExploreModel(
        name = input.name.toString(),
        explore_id = input.explore_id.toString(),
        lat = input.lat,
        lng = input.lng,
        address = input.address.toString(),
        city = input.city.toString(),
        state = input.state.toString(),
        country = input.country.toString(),
        categories = listXtoListY(input.categories,::categoryEntityToCategoryModel)!!,
        tips = listXtoListY(input.tips,::tipEntityToTipModel)!!,
        photos = input.photos,
        likes = input.likes.toString(),
        rating = input.rating.toString(),
        createdAt = input.createdAt.toString(),
        shortUrl = input.shortUrl.toString()
    )
}

fun categoryEntityToCategoryModel(categoryEntity: CategoryEntity):CategoryModel{

    return CategoryModel(
        icon_url = categoryEntity.icon_url,
        name = categoryEntity.name,
        pluralName = categoryEntity.pluralName
    )
}

fun tipEntityToTipModel(tipEntity: TipEntity):TipModel{

    return TipModel(
        firstName = tipEntity.firstName.toString(),
        lastName = tipEntity.lastName.toString() ,
        message = tipEntity.message.toString(),
        userAvatarUrl = tipEntity.userAvatarUrl.toString()
    )

}

fun expDtoToListExpEntity(exploresDTO: PlaceSearchDTO): List<ExploreEntity> {

    return if (exploresDTO.placeResults.isNotEmpty()) listXtoListY(exploresDTO.placeResults, ::placeResultToExploreEntity)!! else listOf()
}

fun placeDtoToListExpModel(exploresDTO: PlaceSearchDTO): List<ExploreModel> {
    return if (exploresDTO.placeResults.isNotEmpty()) listXtoListY(exploresDTO.placeResults, ::item1ToExploreModel)!! else listOf()
}

fun placeDetailsDtoToExpEntity(input: PlaceDetailsDTO):ExploreEntity{
    return ExploreEntity(
        explore_id = input.fsqId,
        name = input.name,
        lat = input.geocodes.main.latitude,
        lng=  input.geocodes.main.longitude,
        address = input.location.address,
        city = input.location.crossStreet,
        state= input.location.locality,
        country= input.location.country,
        categories= listXtoListY(input.categories,::categoryDetailsDtoCategoryEntity)!!,
        photos= listOf(),
        likes=  "",
        rating="",
        ratingColor="",
        createdAt= "",
        tips= listOf(),
        shortUrl=input.toString()

    )
}

fun placeResultToExploreEntity(placeResult: PlaceSearchDTO.PlaceResult):ExploreEntity{

    return ExploreEntity(
        explore_id = placeResult.fsqId,
        name = placeResult.name,
        lat = placeResult.geocodes.main.latitude,
        lng=  placeResult.geocodes.main.latitude,
        address = placeResult.location.address,
        city = placeResult.location.crossStreet,
        state= placeResult.location.locality,
        country= placeResult.location.country,
        categories= listXtoListY(placeResult.placeCategories,::categoryDtoCategoryEntity)!!,
        photos= listOf(),
        likes=  "",
        rating="",
        ratingColor="",
        createdAt="",
        tips= listOf(),
        shortUrl=""
    )

}

fun item1ToExploreModel(placeResult: PlaceSearchDTO.PlaceResult):ExploreModel{

    return ExploreModel(
        explore_id = placeResult.fsqId,
        name = placeResult.name,
        lat = placeResult.geocodes.main.latitude,
        lng=  placeResult.geocodes.main.longitude,
        address = placeResult.location.address,
        city = placeResult.location.crossStreet,
        state= placeResult.location.locality,
        country= placeResult.location.country,
        categories= listXtoListY(placeResult.placeCategories,::categoryDtoCategoryModel)!!,
        photos= listOf(),
        likes=  "",
        rating="",
        ratingColor="",
        createdAt="",
        tips= listOf(),
        shortUrl=""
    )

}

fun categoryDtoCategoryEntity(category: PlaceSearchDTO.PlaceResult.PlaceCategory):CategoryEntity{
    return CategoryEntity(name =category.name ,pluralName =category.name ,icon_url =category.icon.prefix.plus(
        QUALITY_500).plus(category.icon.suffix))

}

fun categoryDetailsDtoCategoryEntity(category: PlaceDetailsDTO.Category):CategoryEntity{

    return CategoryEntity(name =category.name ,pluralName =category.name ,icon_url =category.icon.prefix.plus(
        QUALITY_500).plus(category.icon.suffix))

}

//fun photosDetailsDtoString(input: GetExploreDetailsDTO.Response.Venue.Photos.Group4.Item4):String{
//
//    return input.prefix.plus(QUALITY_500).plus(input.suffix)
//
//}
//
//fun tipsDetailsDtoTipEntity(input: GetExploreDetailsDTO.Response.Venue.Tips.Group5.Item6):TipEntity{
//
//    val photoAvatar = input.user.photo
//    return TipEntity(message = input.text ,firstName = input.user.firstName,lastName = input.user.lastName
//        ,userAvatarUrl = photoAvatar.prefix.plus(QUALITY_500).plus(photoAvatar.suffix) )
//
//}

fun categoryDtoCategoryModel(category: PlaceSearchDTO.PlaceResult.PlaceCategory):CategoryModel{

    return CategoryModel(name =category.name ,pluralName =category.name ,icon_url =category.icon.prefix.plus(
        QUALITY_500).plus(category.icon.suffix))

}

fun <X, Y> listXtoListY(
    recipeDTOS: List<X>,
    functionXtoY: (x:X) -> Y
): List<Y>? {
    val models: MutableList<Y> = ArrayList()
    for (dto in recipeDTOS) {
        models.add(functionXtoY(dto))
    }
    return models
}


