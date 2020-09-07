package ir.moeindeveloper.weatherfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ir.moeindeveloper.weatherfo.data.model.City
import ir.moeindeveloper.weatherfo.databinding.ItemCityBinding
import ir.moeindeveloper.weatherfo.util.ui.CitySelectListener
import ir.moeindeveloper.weatherfo.util.ui.adapter.DiffCallback

class CityAdapter(private val listener: CitySelectListener) : RecyclerView.Adapter<CityAdapter.CityViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context),parent,false)


        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindItem(items.currentList[position])
    }

    override fun getItemCount(): Int = items.currentList.size

    private var items: AsyncListDiffer<City> = AsyncListDiffer(this, DiffCallback<City>())

    fun updateData(cities: List<City>) = items.submitList(cities)

    inner class CityViewHolder(private val binding: ItemCityBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    listener.onCitySelected(items.currentList[adapterPosition])
                }
            }
        }

        fun bindItem(city: City){
            binding.itemCityName.text = city.name
        }
    }

}