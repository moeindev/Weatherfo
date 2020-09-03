package ir.moeindeveloper.weatherfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.moeindeveloper.weatherfo.data.model.City
import ir.moeindeveloper.weatherfo.databinding.ItemCityBinding
import ir.moeindeveloper.weatherfo.util.ui.CitySelectListener

class CityAdapter(private val listener: CitySelectListener) : RecyclerView.Adapter<CityAdapter.CityViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context),parent,false)


        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindItem(items[position],listener)
    }

    override fun getItemCount(): Int = items.size

    private var items: List<City> = listOf()

    fun updateData(cities: List<City>) {
        items = cities
        notifyDataSetChanged()
    }

    class CityViewHolder(private val binding: ItemCityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(city: City, listener: CitySelectListener){
            binding.itemCityName.text = city.name
            binding.root.setOnClickListener {
                listener.onCitySelected(city)
            }
        }
    }

}