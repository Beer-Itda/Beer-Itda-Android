package com.hjiee.data.api

import com.hjiee.data.response.common.EntityResponse
import com.hjiee.data.response.v2.*
import retrofit2.http.*

interface BeerApi {

    /**
     * 카카오 로그인
     */
    @FormUrlEncoded
    @POST("api/v1/user/login/kakao")
    suspend fun kakaoLogin(
        @Field("kakao_token") token: String
    ): LoginResponse?

    /**
     * refresh token 갱신
     */
    @FormUrlEncoded
    @POST("api/v1/user/token")
    suspend fun refreshToken(
        @Field("refresh_token") token: String
    ): LoginResponse?

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
    @GET("api/v1/beer/detail/{beerId}")
    suspend fun getBeerDetail(
        @Path("beerId") id: Int
    ): EntityResponse<BeerDetailResponse>?

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
        @Query("cursor") cursor: Int = 1
    ): EntityResponse<AromasResponse>?

    /**
     * 유저 정보
     */
    @GET("api/v1/user/info")
    suspend fun getUserInfo(): EntityResponse<UserResponse>?

    /**
     * 내가 작성한 리뷰
     */
    @GET("api/v1/review")
    suspend fun getMyReview(): MyReviewListResponse?

    /**
     * 내가 작성한 리뷰
     */
    @FormUrlEncoded
    @POST("api/v1/review/{id}")
    suspend fun postReview(
        @Path("id") beerId: Int,
        @Field("star") starScore: Float,
        @Field("content") content: String
    )

    /**
     * 월간 인기 맥주
     */
    @GET("api/v1/beer/monthly")
    suspend fun getBeerAward(): EntityResponse<AwardResponse>?

    /**
     * 닉네임 변경
     */
    @FormUrlEncoded
    @PATCH("api/v1/user/nickname")
    suspend fun changeNickName(
        @Field("nickname") nickName: String
    )

    /**
     * 맥주 향 정보 불러오기
     */
    @GET("api/v1/infomation/aroma")
    suspend fun getAromaInfo()

    /**
     * 맥주 스타일 정보 불러오기
     */
    @GET("api/v1/infomation/style")
    suspend fun getStyleInfo()

    /**
     * 찜하기
     */
    @FormUrlEncoded
    @POST("api/v1/heart")
    suspend fun postFavorite(
        @Field("beer_id") beerId: Int
    )

    /**
     * 찜하기
     */
    @GET("api/v1/heart")
    suspend fun getMyFavorite(): EntityResponse<BeersResponse>?

    /**
     * 검색
     */
    @GET("api/v1/information/search")
    suspend fun search(
        @Query("word") word: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : SearchResponse?

//    /**
//     * App Config
//     */
//    @GET("api/app-config")
//    suspend fun getAppConfig(): Result<AppConfig>

//    /**
//     * 맥주 검색
//     */
//    @GET("api/beers")
//    suspend fun getSearchBeer(
//        @Query("name") name: String,
//        @Query("cursor") nextCursor: Int?
//    ): Result<Response>?

//    /**
//     * 유저 정보 업데이트
//     */
//    @FormUrlEncoded
//    @POST("api/user/update")
//    suspend fun postUserInfo(
//        @Field("nickname") nickName: String?
//    )
//
//    /**
//     * 리뷰 등록
//     */
//    @FormUrlEncoded
//    @POST("api/review")
//    suspend fun postReview(
//        @Field("beer_id") id: Int,
//        @Field("ratio") ratio: Float,
//        @Field("content") content: String?
//    )

//    /**
//     * 리뷰
//     */
//    @GET("api/review")
//    suspend fun getReview(): Results<Review>

//    /**
//     * 즐겨찾기 등록/삭제
//     */
//    @FormUrlEncoded
//    @POST("api/favorite")
//    suspend fun postFavorite(
//        @Field("beer_id") id: Int,
//        @Field("flag") flag: Boolean
//    )

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