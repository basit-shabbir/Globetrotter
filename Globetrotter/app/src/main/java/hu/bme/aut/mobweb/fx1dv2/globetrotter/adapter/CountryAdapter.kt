package hu.bme.aut.mobweb.fx1dv2.globetrotter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.PointerIcon.load
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.PointerIconCompat.load
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.mobweb.fx1dv2.globetrotter.CountryListActivity
import hu.bme.aut.mobweb.fx1dv2.globetrotter.data.CountryData
import hu.bme.aut.mobweb.fx1dv2.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.fx1dv2.globetrotter.databinding.CountryItemBinding
import java.lang.Exception
import java.lang.System.load
import kotlin.concurrent.thread

class CountryAdapter (context: Context, dbCountries: List<CountryData>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    var countries = mutableListOf<CountryData>()
    private var context: Context

    init {
        this.context = context
        countries.addAll(dbCountries)
    }

    inner class ViewHolder(val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCountry = countries[position]

        holder.binding.tvCountryName.text = currentCountry.name.common
        holder.binding.tvCountryCode.text = currentCountry.cca3
        // holder.binding.ivFlag.tooltipText = currentCountry.flags.png
        Glide.with(context)
             .load(currentCountry.flags.png).into(holder.binding.ivFlag)

        holder.binding.btnDelete.setOnClickListener{
            deleteCountry(holder.adapterPosition)
        }
    }

    private fun deleteCountry(position: Int) {

                val t1 = Thread(
                    Runnable{

                    CountryDatabase.getInstance(context).countryDao().deleteCountry(countries.get(position))
                    (context as CountryListActivity).runOnUiThread { countries.removeAt(position)
                    notifyItemRemoved(position)
                    //notifyItemRangeChanged(position, itemCount - position)
                    }
                })
                t1.start()

    }

    fun addCountry(country: CountryData) {
        val t2 = Thread(Runnable {
            try {
                CountryDatabase.getInstance(context).countryDao().insertCountry(country)
                (context as CountryListActivity).runOnUiThread {
                    countries.add(country)
                    notifyItemInserted(countries.lastIndex)
                }

            } catch (e: Exception) {
              Snackbar.make((context as CountryListActivity).binding.root, e.localizedMessage, Snackbar.LENGTH_LONG).show()
            }
        })
        t2.start()
    }
}