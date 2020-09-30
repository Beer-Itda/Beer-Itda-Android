package com.ddd4.synesthesia.beer.data.source.remote.service

import com.ddd4.synesthesia.beer.data.model.*
import retrofit2.http.*

interface BeerApi {

    /**
     * 맥주 리스트
     */
    @GET("api/beers")
    suspend fun getBeerList() : Result<Response>?

    /**
     * 맥주 상세
     */
    @GET("api/beer")
    suspend fun getBeer(
        @Query("beer_id") id : Int
    ) : Result<Response>?

    /**
     * 유저 정보
     */
    @GET("api/user")
    suspend fun getUserInfo() : Result<User>?

    /**
     * 유저 정보 업데이트
     */
    @FormUrlEncoded
    @POST("api/user/update")
    suspend fun postUserInfo(
        @Field("nickname") nickName : String?
    )

    /**
     * 리뷰 등록
     */
    @FormUrlEncoded
    @POST("api/review")
    suspend fun postReview(
        @Field("beer_id") id : Int,
        @Field("ratio") ratio : Float,
        @Field("content") content : String?
    )

}