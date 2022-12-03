package hu.bme.aut.mobweb.fx1dv2.globetrotter.network

import hu.bme.aut.mobweb.fx1dv2.globetrotter.data.CountryData
import hu.bme.aut.mobweb.fx1dv2.globetrotter.data.Name
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkManager {
    val retrofit: Retrofit
    val countryApi: CountryApi


    private const val SERVICE_URL = "https://restcountries.com/"

init {
    retrofit = Retrofit.Builder()
              .baseUrl(SERVICE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .client(OkHttpClient.Builder().build())
              .build()

    countryApi = retrofit.create(CountryApi::class.java)
}
    fun getCountryByName(name: String): Call<List<CountryData>?> {
        return countryApi.getCountryByName(name)
    }

}