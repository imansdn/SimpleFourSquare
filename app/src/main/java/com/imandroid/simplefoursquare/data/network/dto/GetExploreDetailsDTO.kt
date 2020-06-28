package com.imandroid.simplefoursquare.data.network.dto


import com.google.gson.annotations.SerializedName

data class GetExploreDetailsDTO(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("response")
    val response: Response
) {
    data class Meta(
        @SerializedName("code")
        val code: Int,
        @SerializedName("requestId")
        val requestId: String
    )

    data class Response(
        @SerializedName("venue")
        val venue: Venue
    ) {
        data class Venue(
            @SerializedName("allowMenuUrlEdit")
            val allowMenuUrlEdit: Boolean,
            @SerializedName("attributes")
            val attributes: Attributes,
            @SerializedName("beenHere")
            val beenHere: BeenHere,
            @SerializedName("bestPhoto")
            val bestPhoto: BestPhoto,
            @SerializedName("canonicalUrl")
            val canonicalUrl: String,
            @SerializedName("categories")
            val categories: List<Category>,
            @SerializedName("colors")
            val colors: Colors,
            @SerializedName("contact")
            val contact: Contact,
            @SerializedName("createdAt")
            val createdAt: Int,
            @SerializedName("defaultHours")
            val defaultHours: DefaultHours,
            @SerializedName("dislike")
            val dislike: Boolean,
            @SerializedName("hereNow")
            val hereNow: HereNow,
            @SerializedName("hierarchy")
            val hierarchy: List<Hierarchy>,
            @SerializedName("hours")
            val hours: Hours,
            @SerializedName("id")
            val id: String,
            @SerializedName("inbox")
            val inbox: Inbox,
            @SerializedName("likes")
            val likes: Likes,
            @SerializedName("listed")
            val listed: Listed,
            @SerializedName("location")
            val location: Location,
            @SerializedName("name")
            val name: String,
            @SerializedName("ok")
            val ok: Boolean,
            @SerializedName("pageUpdates")
            val pageUpdates: PageUpdates,
            @SerializedName("parent")
            val parent: Parent,
            @SerializedName("photos")
            val photos: Photos,
            @SerializedName("popular")
            val popular: Popular,
            @SerializedName("rating")
            val rating: Double,
            @SerializedName("ratingColor")
            val ratingColor: String,
            @SerializedName("ratingSignals")
            val ratingSignals: Int,
            @SerializedName("reasons")
            val reasons: Reasons,
            @SerializedName("seasonalHours")
            val seasonalHours: List<Any>,
            @SerializedName("shortUrl")
            val shortUrl: String,
            @SerializedName("specials")
            val specials: Specials,
            @SerializedName("stats")
            val stats: Stats,
            @SerializedName("timeZone")
            val timeZone: String,
            @SerializedName("tips")
            val tips: Tips,
            @SerializedName("verified")
            val verified: Boolean
        ) {
            data class Attributes(
                @SerializedName("groups")
                val groups: List<Group1>
            ) {
                data class Group1(
                    @SerializedName("count")
                    val count: Int,
                    @SerializedName("items")
                    val items: List<Item1>,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("summary")
                    val summary: String,
                    @SerializedName("type")
                    val type: String
                ) {
                    data class Item1(
                        @SerializedName("displayName")
                        val displayName: String,
                        @SerializedName("displayValue")
                        val displayValue: String
                    )
                }
            }

            data class BeenHere(
                @SerializedName("count")
                val count: Int,
                @SerializedName("lastCheckinExpiredAt")
                val lastCheckinExpiredAt: Int,
                @SerializedName("marked")
                val marked: Boolean,
                @SerializedName("unconfirmedCount")
                val unconfirmedCount: Int
            )

            data class BestPhoto(
                @SerializedName("createdAt")
                val createdAt: Int,
                @SerializedName("height")
                val height: Int,
                @SerializedName("id")
                val id: String,
                @SerializedName("prefix")
                val prefix: String,
                @SerializedName("source")
                val source: Source,
                @SerializedName("suffix")
                val suffix: String,
                @SerializedName("visibility")
                val visibility: String,
                @SerializedName("width")
                val width: Int
            ) {
                data class Source(
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("url")
                    val url: String
                )
            }

            data class Category(
                @SerializedName("icon")
                val icon: Icon,
                @SerializedName("id")
                val id: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("pluralName")
                val pluralName: String,
                @SerializedName("primary")
                val primary: Boolean,
                @SerializedName("shortName")
                val shortName: String
            ) {
                data class Icon(
                    @SerializedName("prefix")
                    val prefix: String,
                    @SerializedName("suffix")
                    val suffix: String
                )
            }

            data class Colors(
                @SerializedName("algoVersion")
                val algoVersion: Int,
                @SerializedName("highlightColor")
                val highlightColor: HighlightColor,
                @SerializedName("highlightTextColor")
                val highlightTextColor: HighlightTextColor
            ) {
                data class HighlightColor(
                    @SerializedName("photoId")
                    val photoId: String,
                    @SerializedName("value")
                    val value: Int
                )

                data class HighlightTextColor(
                    @SerializedName("photoId")
                    val photoId: String,
                    @SerializedName("value")
                    val value: Int
                )
            }

            class Contact(
            )

            data class DefaultHours(
                @SerializedName("dayData")
                val dayData: List<Any>,
                @SerializedName("isLocalHoliday")
                val isLocalHoliday: Boolean,
                @SerializedName("isOpen")
                val isOpen: Boolean,
                @SerializedName("richStatus")
                val richStatus: RichStatus,
                @SerializedName("status")
                val status: String,
                @SerializedName("timeframes")
                val timeframes: List<Timeframe>
            ) {
                data class RichStatus(
                    @SerializedName("entities")
                    val entities: List<Any>,
                    @SerializedName("text")
                    val text: String
                )

                data class Timeframe(
                    @SerializedName("days")
                    val days: String,
                    @SerializedName("includesToday")
                    val includesToday: Boolean,
                    @SerializedName("open")
                    val `open`: List<Open>,
                    @SerializedName("segments")
                    val segments: List<Any>
                ) {
                    data class Open(
                        @SerializedName("renderedTime")
                        val renderedTime: String
                    )
                }
            }

            data class HereNow(
                @SerializedName("count")
                val count: Int,
                @SerializedName("groups")
                val groups: List<Any>,
                @SerializedName("summary")
                val summary: String
            )

            data class Hierarchy(
                @SerializedName("canonicalUrl")
                val canonicalUrl: String,
                @SerializedName("id")
                val id: String,
                @SerializedName("lang")
                val lang: String,
                @SerializedName("name")
                val name: String
            )

            data class Hours(
                @SerializedName("dayData")
                val dayData: List<Any>,
                @SerializedName("isLocalHoliday")
                val isLocalHoliday: Boolean,
                @SerializedName("isOpen")
                val isOpen: Boolean,
                @SerializedName("richStatus")
                val richStatus: RichStatus,
                @SerializedName("status")
                val status: String,
                @SerializedName("timeframes")
                val timeframes: List<Timeframe>
            ) {
                data class RichStatus(
                    @SerializedName("entities")
                    val entities: List<Any>,
                    @SerializedName("text")
                    val text: String
                )

                data class Timeframe(
                    @SerializedName("days")
                    val days: String,
                    @SerializedName("includesToday")
                    val includesToday: Boolean,
                    @SerializedName("open")
                    val `open`: List<Open>,
                    @SerializedName("segments")
                    val segments: List<Any>
                ) {
                    data class Open(
                        @SerializedName("renderedTime")
                        val renderedTime: String
                    )
                }
            }

            data class Inbox(
                @SerializedName("count")
                val count: Int,
                @SerializedName("items")
                val items: List<Any>
            )

            data class Likes(
                @SerializedName("count")
                val count: Int,
                @SerializedName("groups")
                val groups: List<Group2>,
                @SerializedName("summary")
                val summary: String
            ) {
                data class Group2(
                    @SerializedName("count")
                    val count: Int,
                    @SerializedName("items")
                    val items: List<Any>,
                    @SerializedName("type")
                    val type: String
                )
            }

            data class Listed(
                @SerializedName("count")
                val count: Int,
                @SerializedName("groups")
                val groups: List<Group3>
            ) {
                data class Group3(
                    @SerializedName("count")
                    val count: Int,
                    @SerializedName("items")
                    val items: List<Item2>,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("type")
                    val type: String
                ) {
                    data class Item2(
                        @SerializedName("canonicalUrl")
                        val canonicalUrl: String,
                        @SerializedName("collaborative")
                        val collaborative: Boolean,
                        @SerializedName("createdAt")
                        val createdAt: Int,
                        @SerializedName("description")
                        val description: String,
                        @SerializedName("editable")
                        val editable: Boolean,
                        @SerializedName("followers")
                        val followers: Followers,
                        @SerializedName("id")
                        val id: String,
                        @SerializedName("listItems")
                        val listItems: ListItems,
                        @SerializedName("name")
                        val name: String,
                        @SerializedName("photo")
                        val photo: Photo,
                        @SerializedName("public")
                        val `public`: Boolean,
                        @SerializedName("type")
                        val type: String,
                        @SerializedName("updatedAt")
                        val updatedAt: Int,
                        @SerializedName("url")
                        val url: String,
                        @SerializedName("user")
                        val user: User
                    ) {
                        data class Followers(
                            @SerializedName("count")
                            val count: Int
                        )

                        data class ListItems(
                            @SerializedName("count")
                            val count: Int,
                            @SerializedName("items")
                            val items: List<Item3>
                        ) {
                            data class Item3(
                                @SerializedName("createdAt")
                                val createdAt: Int,
                                @SerializedName("id")
                                val id: String,
                                @SerializedName("photo")
                                val photo: Photo
                            ) {
                                data class Photo(
                                    @SerializedName("createdAt")
                                    val createdAt: Int,
                                    @SerializedName("height")
                                    val height: Int,
                                    @SerializedName("id")
                                    val id: String,
                                    @SerializedName("prefix")
                                    val prefix: String,
                                    @SerializedName("suffix")
                                    val suffix: String,
                                    @SerializedName("user")
                                    val user: User,
                                    @SerializedName("visibility")
                                    val visibility: String,
                                    @SerializedName("width")
                                    val width: Int
                                ) {
                                    data class User(
                                        @SerializedName("firstName")
                                        val firstName: String,
                                        @SerializedName("id")
                                        val id: String,
                                        @SerializedName("lastName")
                                        val lastName: String,
                                        @SerializedName("photo")
                                        val photo: Photo
                                    ) {
                                        data class Photo(
                                            @SerializedName("prefix")
                                            val prefix: String,
                                            @SerializedName("suffix")
                                            val suffix: String
                                        )
                                    }
                                }
                            }
                        }

                        data class Photo(
                            @SerializedName("createdAt")
                            val createdAt: Int,
                            @SerializedName("height")
                            val height: Int,
                            @SerializedName("id")
                            val id: String,
                            @SerializedName("prefix")
                            val prefix: String,
                            @SerializedName("suffix")
                            val suffix: String,
                            @SerializedName("user")
                            val user: User,
                            @SerializedName("visibility")
                            val visibility: String,
                            @SerializedName("width")
                            val width: Int
                        ) {
                            data class User(
                                @SerializedName("firstName")
                                val firstName: String,
                                @SerializedName("id")
                                val id: String,
                                @SerializedName("lastName")
                                val lastName: String,
                                @SerializedName("photo")
                                val photo: Photo
                            ) {
                                data class Photo(
                                    @SerializedName("prefix")
                                    val prefix: String,
                                    @SerializedName("suffix")
                                    val suffix: String
                                )
                            }
                        }

                        data class User(
                            @SerializedName("firstName")
                            val firstName: String,
                            @SerializedName("id")
                            val id: String,
                            @SerializedName("lastName")
                            val lastName: String,
                            @SerializedName("photo")
                            val photo: Photo
                        ) {
                            data class Photo(
                                @SerializedName("prefix")
                                val prefix: String,
                                @SerializedName("suffix")
                                val suffix: String
                            )
                        }
                    }
                }
            }

            data class Location(
                @SerializedName("address")
                val address: String,
                @SerializedName("cc")
                val cc: String,
                @SerializedName("city")
                val city: String,
                @SerializedName("country")
                val country: String,
                @SerializedName("formattedAddress")
                val formattedAddress: List<String>,
                @SerializedName("labeledLatLngs")
                val labeledLatLngs: List<LabeledLatLng>,
                @SerializedName("lat")
                val lat: Double,
                @SerializedName("lng")
                val lng: Double,
                @SerializedName("state")
                val state: String
            ) {
                data class LabeledLatLng(
                    @SerializedName("label")
                    val label: String,
                    @SerializedName("lat")
                    val lat: Double,
                    @SerializedName("lng")
                    val lng: Double
                )
            }

            data class PageUpdates(
                @SerializedName("count")
                val count: Int,
                @SerializedName("items")
                val items: List<Any>
            )

            data class Parent(
                @SerializedName("categories")
                val categories: List<Category>,
                @SerializedName("id")
                val id: String,
                @SerializedName("location")
                val location: Location,
                @SerializedName("name")
                val name: String
            ) {
                data class Category(
                    @SerializedName("icon")
                    val icon: Icon,
                    @SerializedName("id")
                    val id: String,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("pluralName")
                    val pluralName: String,
                    @SerializedName("primary")
                    val primary: Boolean,
                    @SerializedName("shortName")
                    val shortName: String
                ) {
                    data class Icon(
                        @SerializedName("prefix")
                        val prefix: String,
                        @SerializedName("suffix")
                        val suffix: String
                    )
                }

                data class Location(
                    @SerializedName("address")
                    val address: String,
                    @SerializedName("cc")
                    val cc: String,
                    @SerializedName("city")
                    val city: String,
                    @SerializedName("country")
                    val country: String,
                    @SerializedName("crossStreet")
                    val crossStreet: String,
                    @SerializedName("formattedAddress")
                    val formattedAddress: List<String>,
                    @SerializedName("labeledLatLngs")
                    val labeledLatLngs: List<LabeledLatLng>,
                    @SerializedName("lat")
                    val lat: Double,
                    @SerializedName("lng")
                    val lng: Double,
                    @SerializedName("state")
                    val state: String
                ) {
                    data class LabeledLatLng(
                        @SerializedName("label")
                        val label: String,
                        @SerializedName("lat")
                        val lat: Double,
                        @SerializedName("lng")
                        val lng: Double
                    )
                }
            }

            data class Photos(
                @SerializedName("count")
                val count: Int,
                @SerializedName("groups")
                val groups: List<Group4>
            ) {
                data class Group4(
                    @SerializedName("count")
                    val count: Int,
                    @SerializedName("items")
                    val items: List<Item4>,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("type")
                    val type: String
                ) {
                    data class Item4(
                        @SerializedName("createdAt")
                        val createdAt: Int,
                        @SerializedName("height")
                        val height: Int,
                        @SerializedName("id")
                        val id: String,
                        @SerializedName("prefix")
                        val prefix: String,
                        @SerializedName("source")
                        val source: Source,
                        @SerializedName("suffix")
                        val suffix: String,
                        @SerializedName("user")
                        val user: User,
                        @SerializedName("visibility")
                        val visibility: String,
                        @SerializedName("width")
                        val width: Int
                    ) {
                        data class Source(
                            @SerializedName("name")
                            val name: String,
                            @SerializedName("url")
                            val url: String
                        )

                        data class User(
                            @SerializedName("firstName")
                            val firstName: String,
                            @SerializedName("id")
                            val id: String,
                            @SerializedName("lastName")
                            val lastName: String,
                            @SerializedName("photo")
                            val photo: Photo
                        ) {
                            data class Photo(
                                @SerializedName("default")
                                val default: Boolean,
                                @SerializedName("prefix")
                                val prefix: String,
                                @SerializedName("suffix")
                                val suffix: String
                            )
                        }
                    }
                }
            }

            data class Popular(
                @SerializedName("isLocalHoliday")
                val isLocalHoliday: Boolean,
                @SerializedName("isOpen")
                val isOpen: Boolean,
                @SerializedName("richStatus")
                val richStatus: RichStatus,
                @SerializedName("status")
                val status: String,
                @SerializedName("timeframes")
                val timeframes: List<Timeframe>
            ) {
                data class RichStatus(
                    @SerializedName("entities")
                    val entities: List<Any>,
                    @SerializedName("text")
                    val text: String
                )

                data class Timeframe(
                    @SerializedName("days")
                    val days: String,
                    @SerializedName("includesToday")
                    val includesToday: Boolean,
                    @SerializedName("open")
                    val `open`: List<Open>,
                    @SerializedName("segments")
                    val segments: List<Any>
                ) {
                    data class Open(
                        @SerializedName("renderedTime")
                        val renderedTime: String
                    )
                }
            }

            data class Reasons(
                @SerializedName("count")
                val count: Int,
                @SerializedName("items")
                val items: List<Item5>
            ) {
                data class Item5(
                    @SerializedName("reasonName")
                    val reasonName: String,
                    @SerializedName("summary")
                    val summary: String,
                    @SerializedName("type")
                    val type: String
                )
            }

            data class Specials(
                @SerializedName("count")
                val count: Int,
                @SerializedName("items")
                val items: List<Any>
            )

            data class Stats(
                @SerializedName("tipCount")
                val tipCount: Int
            )

            data class Tips(
                @SerializedName("count")
                val count: Int,
                @SerializedName("groups")
                val groups: List<Group5>
            ) {
                data class Group5(
                    @SerializedName("count")
                    val count: Int,
                    @SerializedName("items")
                    val items: List<Item6>,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("type")
                    val type: String
                ) {
                    data class Item6(
                        @SerializedName("agreeCount")
                        val agreeCount: Int,
                        @SerializedName("canonicalUrl")
                        val canonicalUrl: String,
                        @SerializedName("createdAt")
                        val createdAt: Int,
                        @SerializedName("disagreeCount")
                        val disagreeCount: Int,
                        @SerializedName("id")
                        val id: String,
                        @SerializedName("lang")
                        val lang: String,
                        @SerializedName("likes")
                        val likes: Likes,
                        @SerializedName("logView")
                        val logView: Boolean,
                        @SerializedName("text")
                        val text: String,
                        @SerializedName("todo")
                        val todo: Todo,
                        @SerializedName("type")
                        val type: String,
                        @SerializedName("user")
                        val user: User
                    ) {
                        data class Likes(
                            @SerializedName("count")
                            val count: Int,
                            @SerializedName("groups")
                            val groups: List<Group6>,
                            @SerializedName("summary")
                            val summary: String
                        ) {
                            data class Group6(
                                @SerializedName("count")
                                val count: Int,
                                @SerializedName("items")
                                val items: List<Item7>,
                                @SerializedName("type")
                                val type: String
                            ) {
                                data class Item7(
                                    @SerializedName("firstName")
                                    val firstName: String,
                                    @SerializedName("id")
                                    val id: String,
                                    @SerializedName("lastName")
                                    val lastName: String,
                                    @SerializedName("photo")
                                    val photo: Photo
                                ) {
                                    data class Photo(
                                        @SerializedName("prefix")
                                        val prefix: String,
                                        @SerializedName("suffix")
                                        val suffix: String
                                    )
                                }
                            }
                        }

                        data class Todo(
                            @SerializedName("count")
                            val count: Int
                        )

                        data class User(
                            @SerializedName("firstName")
                            val firstName: String,
                            @SerializedName("id")
                            val id: String,
                            @SerializedName("lastName")
                            val lastName: String,
                            @SerializedName("photo")
                            val photo: Photo
                        ) {
                            data class Photo(
                                @SerializedName("prefix")
                                val prefix: String,
                                @SerializedName("suffix")
                                val suffix: String
                            )
                        }
                    }
                }
            }
        }
    }
}