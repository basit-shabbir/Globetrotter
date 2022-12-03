package hu.bme.aut.mobweb.fx1dv2.globetrotter

import android.content.ClipData
import android.net.Network
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.mobweb.fx1dv2.globetrotter.adapter.CountryAdapter
import hu.bme.aut.mobweb.fx1dv2.globetrotter.data.CountryData
import hu.bme.aut.mobweb.fx1dv2.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.fx1dv2.globetrotter.databinding.ActivityCountryListBinding
import hu.bme.aut.mobweb.fx1dv2.globetrotter.fragment.CountryDialog
import hu.bme.aut.mobweb.fx1dv2.globetrotter.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountryListActivity : AppCompatActivity(), CountryDialog.CountryHandler {

     lateinit var binding: ActivityCountryListBinding
    private lateinit var countryAdapter:CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //countryAdapter = CountryAdapter(this)
//        binding.recyclerCountry.adapter = countryAdapter

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        Snackbar.make(binding.root,intent.getStringExtra(MainActivity.KEY_NAME).toString(),Snackbar.LENGTH_LONG).show()
        binding.fab.setOnClickListener { CountryDialog().show(supportFragmentManager,"Dialog")

        }
        val t3 = Thread(
            Runnable {
                var  dbCountries = CountryDatabase.getInstance(this).countryDao().getAllCountries()
                runOnUiThread {   countryAdapter = CountryAdapter(this, dbCountries)
                    binding.recyclerCountry.adapter = countryAdapter }
            }
        )
        t3.start()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_country_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        Snackbar.make(binding.root,"Press LogOut",Snackbar.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.about){
            Snackbar.make(binding.root,getString(R.string.basit_shabir_fx1dv2_2022).toString(),Snackbar.LENGTH_LONG).show()
        }else if(item.itemId==R.id.map){
            Snackbar.make(binding.root,"map",Snackbar.LENGTH_LONG).show()

        }else if(item.itemId==R.id.logout){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun countryAdded(country: String) {
       NetworkManager.getCountryByName(country).enqueue(object : Callback<List<CountryData>?> {
           override fun onResponse(
               call: Call<List<CountryData>?>,
               response: Response<List<CountryData>?>
           ) {
               if (response.isSuccessful) {
                   countryAdapter.addCountry(response.body()!![0])
               } else {
                   Snackbar.make(binding.root, "Error: " + response.message(),
                   Snackbar.LENGTH_LONG).show()
               }
           }
           override fun onFailure(call: Call<List<CountryData>?>, t: Throwable) {
               t.printStackTrace()
               Snackbar.make(binding.root, "Network request error occured, check LOG",
                   Snackbar.LENGTH_LONG).show()
           }
       })
    }
}