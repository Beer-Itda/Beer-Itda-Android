package com.hjiee.data.api

import com.hjiee.data.response.common.PageResponse
import com.hjiee.data.response.v2.AromaListResponse
import com.hjiee.data.response.v2.BeerDetailResponse
import com.hjiee.data.response.v2.BeerResponse
import com.hjiee.data.response.v2.LevelGuideResponse
import com.hjiee.data.response.v2.LoginResponse
import com.hjiee.data.response.v2.MyReviewItemResponse
import com.hjiee.data.response.v2.ReviewResponse
import com.hjiee.data.response.v2.StyleListResponse
import com.hjiee.data.response.v2.UserResponse
import com.hjiee.domain.entity.request.RequestSelectedAroma
import com.hjiee.domain.entity.request.RequestSelectedStyle
import com.hjiee.domain.repository.ApiServiceConstants.DEFAULT_FIRST_PAGE
import com.hjiee.domain.repository.ApiServiceConstants.DEFAULT_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
     * 회원 탈퇴
     */
    @POST("api/v1/user/withdraw")
    suspend fun accountWithdraw(): Response<Unit>

    /**
     * refresh token 갱신
     */
    @FormUrlEncoded
    @POST("api/v1/user/token")
    suspend fun refreshToken(
        @Field("refresh_token") token: String
    ): LoginResponse?

    /**
     * 맥주 상세
     */
    @GET("api/v1/beer/detail/{beerId}")
    suspend fun getBeerDetail(
        @Path("beerId") id: Int
    ): BeerDetailResponse?

    /**
     * 내가 선택한 스타일의 맥주
     */
    @GET("api/v1/beer/style")
    suspend fun getSelectedStyleBeer(
        @Query("page") page: Int = DEFAULT_FIRST_PAGE,
        @Query("size") size: Int = DEFAULT_PAGE_SIZE
    ): PageResponse<BeerResponse>

    /**
     * 내가 선택한 향의 맥주
     */
    @GET("api/v1/beer/aroma")
    suspend fun getSelectedAromaBeer(
        @Query("page") page: Int = DEFAULT_FIRST_PAGE,
        @Query("size") size: Int = DEFAULT_PAGE_SIZE
    ): PageResponse<BeerResponse>

    /**
     * 랜덤 추천 맥주
     */
    @GET("api/v1/beer/random")
    suspend fun getRandomRecommendBeer(
        @Query("page") page: Int = DEFAULT_FIRST_PAGE,
        @Query("size") size: Int = DEFAULT_PAGE_SIZE
    ): PageResponse<BeerResponse>


    /**
     * 유저 정보
     */
    @GET("api/v1/user/info")
    suspend fun getUserInfo(): UserResponse?

    /**
     * 내가 작성한 리뷰
     */
    @GET("api/v1/review")
    suspend fun getMyReview(
        @Query("page") page: Int = DEFAULT_FIRST_PAGE,
        @Query("size") size: Int = DEFAULT_PAGE_SIZE
    ): PageResponse<MyReviewItemResponse>?

    /**
     * 맥주에 등록된 리뷰 리스트
     */
    @GET("api/v1/review/{beerId}")
    suspend fun getReviewList(
        @Path("beerId") beerId: Int,
        @Query("page") page: Int = DEFAULT_FIRST_PAGE,
        @Query("size") size: Int = DEFAULT_PAGE_SIZE
    ): PageResponse<ReviewResponse>?

    /**
     * 리뷰 등록
     */
    @FormUrlEncoded
    @POST("api/v1/review/{beerId}")
    suspend fun postReview(
        @Path("beerId") beerId: Int,
        @Field("star") starScore: Float,
        @Field("content") content: String
    )

    /**
     * 월간 인기 맥주
     */
    @GET("api/v1/beer/monthly")
    suspend fun getBeerAward(): BeerResponse?

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
    @GET("api/v1/information/aroma")
    suspend fun getAromaInfo(): AromaListResponse

    /**
     * 맥주 스타일 정보 불러오기
     */
    @GET("api/v1/information/style")
    suspend fun getStyleInfo(): StyleListResponse

    /**
     * 관심있는 향 선택하기
     */
    @POST("api/v1/select/aroma")
    suspend fun postSelectedAroma(
        @Body aromaIdList: RequestSelectedAroma
    )

    /**
     * 관심있는 스타일 선택하기
     */
    @POST("api/v1/select/style")
    suspend fun postSelectedStyle(
        @Body styleIdList: RequestSelectedStyle
    )


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
    suspend fun getMyFavorite(
        @Query("page") page: Int = DEFAULT_FIRST_PAGE,
        @Query("size") size: Int = DEFAULT_PAGE_SIZE
    ): PageResponse<BeerResponse>?

    /**
     * 검색
     */
    @GET("api/v1/information/search")
    suspend fun search(
        @Query("word") word: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): PageResponse<BeerResponse>?

    /**
     * 등급가이드 불러오기
     */
    @GET("api/v1/level")
    suspend fun getLevelGuide(): LevelGuideResponse


    /**
     * 리뷰 삭제
     */
    @DELETE("api/v1/review/{beerId}")
    suspend fun deleteReview(
        @Path("beerId") beerId: Int
    )


    /**
     * 리뷰 수정
     */
    @FormUrlEncoded
    @PATCH("api/v1/review/{beerId}")
    suspend fun updateReview(
        @Path("beerId") beerId: Int,
        @Field("star") starScore: Float,
        @Field("content") content: String
    )

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