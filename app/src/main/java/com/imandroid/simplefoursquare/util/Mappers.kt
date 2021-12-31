package com.imandroid.simplefoursquare.util

import com.imandroid.simplefoursquare.data.db.table.CategoryEntity
import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import com.imandroid.simplefoursquare.data.db.table.TipEntity
import com.imandroid.simplefoursquare.data.network.dto.GetAllExploresDTO
import com.imandroid.simplefoursquare.data.network.dto.GetExploreDetailsDTO
import com.imandroid.simplefoursquare.domain.CategoryModel
import com.imandroid.simplefoursquare.domain.ExploreModel
import com.imandroid.simplefoursquare.domain.TipModel
import java.util.ArrayList

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

fun expDtoToListExpEntity(exploresDTO: GetAllExploresDTO): List<ExploreEntity> {

    return if (exploresDTO.response.groups.isNotEmpty()) listXtoListY(exploresDTO.response.groups[0].items, ::item1ToExploreEntity)!! else listOf()
}

fun expDtoToListExpModel(exploresDTO: GetAllExploresDTO): List<ExploreModel> {
    return if (exploresDTO.response.groups.isNotEmpty()) listXtoListY(exploresDTO.response.groups[0].items, ::item1ToExploreModel)!! else listOf()
}

fun exploreDetailsDtoToExpEntity(input:GetExploreDetailsDTO):ExploreEntity{
    val photosList= if (input.response.venue.photos?.groups?.isNotEmpty()==true) listXtoListY(input.response.venue.photos.groups[0].items,::photosDetailsDtoString) else listOf()
    val tipsList= if (input.response.venue.tips?.groups?.isNotEmpty()== true) listXtoListY(input.response.venue.tips.groups[0].items,::tipsDetailsDtoTipEntity) else listOf()
    return ExploreEntity(
        explore_id = input.response.venue.id.toString() ,
        name = input.response.venue.name.toString(),
        lat = input.response.venue.location.lat,
        lng=  input.response.venue.location.lng,
        address =  input.response.venue.location.address.toString(),
        city = input.response.venue.location.city.toString() ,
        state=  input.response.venue.location.state.toString(),
        country=  input.response.venue.location.country.toString(),
        categories= listXtoListY(input.response.venue.categories,::categoryDetailsDtoCategoryEntity)!!,
        photos= photosList,
        likes=  input.response.venue.likes.count.toString(),
        rating=input.response.venue.rating.toString(),
        ratingColor=input.response.venue.ratingColor.toString(),
        createdAt=input.response.venue.createdAt.toString(),
        tips=tipsList!!,
        shortUrl=input.response.venue.shortUrl.toString()

    )
}

fun item1ToExploreEntity(item1: GetAllExploresDTO.Response.Group.Item1):ExploreEntity{

    return ExploreEntity(
        explore_id = item1.venue.id.toString() ,
        name = item1.venue.name.toString(),
        lat = item1.venue.location.lat,
        lng=  item1.venue.location.lng,
        address =  item1.venue.location.address.toString(),
        city = item1.venue.location.city.toString() ,
        state=  item1.venue.location.state.toString(),
        country=  item1.venue.location.country.toString(),
        categories= listXtoListY(item1.venue.categories,::categoryDtoCategoryEntity)!!,
        photos= listOf(),
        likes=  "",
        rating="",
        ratingColor="",
        createdAt="",
        tips= listOf(),
        shortUrl=""
    )

}

fun item1ToExploreModel(item1: GetAllExploresDTO.Response.Group.Item1):ExploreModel{

    return ExploreModel(
        explore_id = item1.venue.id.toString() ,
        name = item1.venue.name.toString(),
        lat = item1.venue.location.lat,
        lng=  item1.venue.location.lng,
        address =  item1.venue.location.address.toString(),
        city = item1.venue.location.city.toString() ,
        state=  item1.venue.location.state.toString(),
        country=  item1.venue.location.country.toString(),
        categories= listXtoListY(item1.venue.categories,::categoryDtoCategoryModel)!!,
        photos= listOf(),
        likes=  "",
        rating="",
        ratingColor="",
        createdAt="",
        tips= listOf(),
        shortUrl=""
    )

}

fun categoryDtoCategoryEntity(category: GetAllExploresDTO.Response.Group.Item1.Venue.Category):CategoryEntity{

    return CategoryEntity(name =category.name ,pluralName =category.pluralName ,icon_url =category.icon.prefix.plus(
        QUALITY_500).plus(category.icon.suffix))

}

fun categoryDetailsDtoCategoryEntity(category: GetExploreDetailsDTO.Response.Venue.Category):CategoryEntity{

    return CategoryEntity(name =category.name ,pluralName =category.pluralName ,icon_url =category.icon.prefix.plus(
        QUALITY_500).plus(category.icon.suffix))

}

fun photosDetailsDtoString(input: GetExploreDetailsDTO.Response.Venue.Photos.Group4.Item4):String{

    return input.prefix.plus(QUALITY_500).plus(input.suffix)

}

fun tipsDetailsDtoTipEntity(input: GetExploreDetailsDTO.Response.Venue.Tips.Group5.Item6):TipEntity{

    val photoAvatar = input.user.photo
    return TipEntity(message = input.text ,firstName = input.user.firstName,lastName = input.user.lastName
        ,userAvatarUrl = photoAvatar.prefix.plus(QUALITY_500).plus(photoAvatar.suffix) )

}

fun categoryDtoCategoryModel(category: GetAllExploresDTO.Response.Group.Item1.Venue.Category):CategoryModel{

    return CategoryModel(name =category.name ,pluralName =category.pluralName ,icon_url =category.icon.prefix.plus(
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


