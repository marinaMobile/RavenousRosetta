package com.superking.parchisi

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET


interface ApiInterface {
    @GET("json/?key=LbwKKoO9eF4GLMz")
    suspend fun getData(): Response<CountryCodeJS>
}

interface HostInterface {
    @GET("hyperion.json")
    suspend fun getDataDev(): Response<GeoDev>
}

class CountryRepo(private val countryApi: ApiInterface) {
    suspend fun getDat() = countryApi.getData()
}
class DevRepo(private val devData: HostInterface) {
    suspend fun getDataDev() = devData.getDataDev()
}

@Keep
data class CountryCodeJS(
    @SerializedName("countryCode")
    val cou: String,
)


@Keep
data class GeoDev(
    @SerializedName("hui")
    val geo: String,
    @SerializedName("pizda")
    val view: String,
    @SerializedName("djigurda")
    val appsChecker: String,
)