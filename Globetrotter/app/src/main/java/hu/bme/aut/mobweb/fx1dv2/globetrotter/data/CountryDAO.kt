package hu.bme.aut.mobweb.fx1dv2.globetrotter.data

import androidx.room.*

@Dao
interface CountryDAO {
    @Query("SELECT * FROM countries")
    fun getAllCountries(): List<CountryData>

    @Insert
    fun insertCountry(countryData: CountryData)

    @Delete
    fun deleteCountry(countryData: CountryData)

    @Update
    fun updateCountry(countryData: CountryData)
}