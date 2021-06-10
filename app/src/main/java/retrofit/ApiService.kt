package retrofit

import Models.Ads
import Models.Cat
import Models.Detail
import Models.myResult
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("allads.php")
    fun getAllAds(): retrofit2.Call<List<Ads>>

    @GET("cat.php")
    fun getCats(): retrofit2.Call<List<Cat>>

    @GET("uniqecat.php")
    fun getUniqeCat(@Query("id") id: String): retrofit2.Call<List<Ads>>

    @GET("uniqeads.php")
    fun getUniqeAds(@Query("id") id: String): retrofit2.Call<Detail>

    @FormUrlEncoded
    @POST("signup.php")
    //کلید
    //متغیری از جنش استرینگ
    fun checkSignup(
        @Field("username") username: String,
        @Field("pass") pass: String
    ): retrofit2.Call<myResult>

    @GET("cat.php")
    fun getAllCat(): Call<List<Cat>>

    //فرستادن اطلاعات سمت سرور
    @FormUrlEncoded
    @POST("add.php")
    fun addRecord(
        //فیلدهای که میخواهیم سرور بگیره
        @Field("title") title: String,
        @Field("city") city: String,
        @Field("price") price: String,
        @Field("desc") desc: String,
        @Field("phone") phone: String,
        @Field("cat") cat: String
    ): retrofit2.Call<myResult>

//درخواست به سرور برای سرچ کردن که درخوات بصورت رشته هست
    @GET("search.php")
    fun getSearch(@Query("search") search: String):Call<List<Ads>>

}