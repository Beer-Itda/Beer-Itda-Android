package com.hjiee.data.api

import com.hjiee.data.response.common.Result
import com.hjiee.data.response.v2.AromasResponse
import com.hjiee.data.response.v2.BeerResponse
import com.hjiee.data.response.v2.BeersResponse
import com.hjiee.data.response.common.EntityResponse
import com.hjiee.data.response.v2.LoginResponse
import com.kakao.sdk.user.model.User
import retrofit2.http.*

interface BeerApi {

    /**
     * 카카오
     */
    @FormUrlEncoded
    @POST("api/v1/user/login/kakao")
    suspend fun kakaoLogin(
        @Field("kakao_token") token: String
    ): LoginResponse?

//    /**
//     * App Config
//     */
//    @GET("api/app-config")
//    suspend fun getAppConfig(): Result<AppConfig>

    /**
     * 맥주 리스트
     */
    @GET("api/v1/beer")
    suspend fun getBeerList(
        @Query("cursor") nextCursor: Int? = 0
    ): EntityResponse<BeersResponse>?


    /**
     * 맥주 상세
     */
    @GET("api/v1/beer/detail")
    suspend fun getBeer(
        @Query("id") id: Int
    ): EntityResponse<BeerResponse>?

    /**
     * 내가 선택한 스타일의 맥주
     */
    @GET("api/v1/beer/style")
    suspend fun getSelectedStyleBeer(
        @Body userId: Int
    ): EntityResponse<BeersResponse>?

    /**
     * 내가 선택한 향의 맥주
     */
    @GET("api/v1/beer/aroma")
    suspend fun getSelectedAromaBeer(
        @Query("user_id") userId: Int
    ): EntityResponse<AromasResponse>?


//    /**
//     * 맥주 검색
//     */
//    @GET("api/beers")
//    suspend fun getSearchBeer(
//        @Query("name") name: String,
//        @Query("cursor") nextCursor: Int?
//    ): Result<Response>?

    /**
     * 유저 정보
     */
    @GET("api/user")
    suspend fun getUserInfo(): Result<User>?

    /**
     * 유저 정보 업데이트
     */
    @FormUrlEncoded
    @POST("api/user/update")
    suspend fun postUserInfo(
        @Field("nickname") nickName: String?
    )

    /**
     * 리뷰 등록
     */
    @FormUrlEncoded
    @POST("api/review")
    suspend fun postReview(
        @Field("beer_id") id: Int,
        @Field("ratio") ratio: Float,
        @Field("content") content: String?
    )

//    /**
//     * 리뷰
//     */
//    @GET("api/review")
//    suspend fun getReview(): Results<Review>

    /**
     * 즐겨찾기 등록/삭제
     */
    @FormUrlEncoded
    @POST("api/favorite")
    suspend fun postFavorite(
        @Field("beer_id") id: Int,
        @Field("flag") flag: Boolean
    )

//    /**
//     * 내가 즐겨찾기한 맥주 리스트
//     */
//    @GET("api/favorite")
//    suspend fun getFavorite(): Results<Response>
//
//    /**
//     * 내가 즐겨찾기한 맥주 리스트
//     */
//    @GET("api/popular-beers")
//    suspend fun getPopularBeer(): Result<Response>


}