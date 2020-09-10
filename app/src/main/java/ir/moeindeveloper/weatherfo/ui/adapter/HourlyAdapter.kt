package ir.moeindeveloper.weatherfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.moeindeveloper.weatherfo.data.model.Hourly
import ir.moeindeveloper.weatherfo.databinding.ItemHourlyBinding
import ir.moeindeveloper.weatherfo.util.date.toDate
import ir.moeindeveloper.weatherfo.util.date.toHour
import ir.moeindeveloper.weatherfo.util.ui.adapter.DiffCallback
import ir.moeindeveloper.weatherfo.util.weather.getWeatherIcon
import ir.moeindeveloper.weatherfo.util.weather.toStringTemp

class HourlyAdapter: RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val binding = ItemHourlyBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bindItem(items.currentList[position])
    }

    override fun getItemCount(): Int = items.currentList.size


    private var items: AsyncListDiffer<Hourly> = AsyncListDiffer(this, DiffCallback<Hourly>())

    fun updateData(hours: List<Hourly>) = items.submitList(hours)

    class HourlyViewHolder(private val binding: ItemHourlyBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Hourly) {
            binding.itemHourlyTemp.text = item.temp.toStringTemp()
            binding.itemHourlyTime.text = item.dt.toHour()
            if (item.weather.isNotEmpty()) {
                Glide.with(binding.itemHourlyIcon.context).load(item.weather[0].icon.getWeatherIcon(item.dt.toDate())).into(binding.itemHourlyIcon)
            }
        }
    }
}