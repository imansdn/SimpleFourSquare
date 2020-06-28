package com.imandroid.simplefoursquare.util

import com.imandroid.simplefoursquare.data.db.table.CategoryEntity
import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import com.imandroid.simplefoursquare.data.db.table.TipEntity
import com.imandroid.simplefoursquare.data.network.dto.GetAllExploresDTO
import com.imandroid.simplefoursquare.domain.CategoryModel
import com.imandroid.simplefoursquare.domain.ExploreModel
import com.imandroid.simplefoursquare.domain.TipModel
import java.util.ArrayList

fun expEntityToExpModel(input: ExploreEntity):ExploreModel{
    return ExploreModel(
        name = input.name,
        explore_id = input.explore_id,
        lat = input.lat,
        lng = input.lng,
        address = input.address,
        city = input.city,
        state = input.state,
        country = input.country,
        categories = listXtoListY(input.categories,::categoryEntityToCategoryModel)!!,
        tips = listXtoListY(input.tips,::tipEntityToTipModel)!!,
        photos = input.photos
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
        firstName = tipEntity.firstName ,
        lastName = tipEntity.lastName ,
        message = tipEntity.message,
        userAvatarUrl = tipEntity.userAvatarUrl
    )

}

fun expDtoToListExpEntity(exploresDTO: GetAllExploresDTO): List<ExploreEntity> {

    return listXtoListY(exploresDTO.response.groups[0].items, ::item1ToExploreEntity)!!
}

fun expDtoToListExpModel(exploresDTO: GetAllExploresDTO): List<ExploreModel> {

    return listXtoListY(exploresDTO.response.groups[0].items, ::item1ToExploreModel)!!
}

fun item1ToExploreEntity(item1: GetAllExploresDTO.Response.Group.Item1):ExploreEntity{

    return ExploreEntity(
        explore_id = item1.venue.id ,
        name = item1.venue.name,
        lat = item1.venue.location.lat,
        lng=  item1.venue.location.lng,
        address =  item1.venue.location.address,
        city = item1.venue.location.city ,
        state=  item1.venue.location.state,
        country=  item1.venue.location.country,
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
        explore_id = item1.venue.id ,
        name = item1.venue.name,
        lat = item1.venue.location.lat,
        lng=  item1.venue.location.lng,
        address =  item1.venue.location.address,
        city = item1.venue.location.city ,
        state=  item1.venue.location.state,
        country=  item1.venue.location.country,
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

    return CategoryEntity(name =category.name ,pluralName =category.pluralName ,icon_url =category.icon.prefix.plus(category.icon.suffix))

}

fun categoryDtoCategoryModel(category: GetAllExploresDTO.Response.Group.Item1.Venue.Category):CategoryModel{

    return CategoryModel(name =category.name ,pluralName =category.pluralName ,icon_url =category.icon.prefix.plus(category.icon.suffix))

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


