package ir.moeindeveloper.weatherfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ir.moeindeveloper.weatherfo.data.model.City
import ir.moeindeveloper.weatherfo.databinding.ItemCityBinding
import ir.moeindeveloper.weatherfo.util.ui.CitySelectListener
import ir.moeindeveloper.weatherfo.util.ui.adapter.DiffCallback
import java.util.*

class CityAdapter(private val listener: CitySelectListener,private val locale: String) : RecyclerView.Adapter<CityAdapter.CityViewHolder>(), Filterable{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context),parent,false)


        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindItem(filteredItems.currentList[position])
    }

    override fun getItemCount(): Int = filteredItems.currentList.size

    private var items: List<City> = listOf()

    private var filteredItems: AsyncListDiffer<City> = AsyncListDiffer(this,DiffCallback<City>())

    fun updateData(cities: List<City>) {
        items = cities
        filteredItems.submitList(items)
    }

    inner class CityViewHolder(private val binding: ItemCityBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    listener.onCitySelected(filteredItems.currentList[adapterPosition])
                }
            }
        }

        fun bindItem(city: City){
            binding.itemCityName.text = when(locale){
                "en" -> city.name
                "fa" -> city.faName
                else -> city.name
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()

                val filtered = if (charString.isEmpty()) {
                    items
                } else {
                    when(locale) {
                        "en" -> {
                            items.filter { city -> city.name.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT)) }
                        }

                        "fa" -> {
                            items.filter { city -> city.faName.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT)) }
                        }

                        else -> items.filter { city -> city.name.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT)) }
                    }
                }

                val results = FilterResults()
                results.values = filtered

                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1?.values is List<*>) filteredItems.submitList(p1.values as List<City>)
            }
        }
    }
}