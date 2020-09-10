package ir.moeindeveloper.weatherfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.moeindeveloper.weatherfo.data.model.Daily
import ir.moeindeveloper.weatherfo.databinding.ItemDailyBinding
import ir.moeindeveloper.weatherfo.util.date.toDayName
import ir.moeindeveloper.weatherfo.util.ui.OnDailyForecastListener
import ir.moeindeveloper.weatherfo.util.ui.adapter.DiffCallback
import ir.moeindeveloper.weatherfo.util.weather.getCurrentTemp
import ir.moeindeveloper.weatherfo.util.weather.getWeatherIcon

class DailyAdapter(private val listener: OnDailyForecastListener): RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val binding = ItemDailyBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bindItem(items.currentList[position])
    }

    override fun getItemCount(): Int = items.currentList.size


    private var items: AsyncListDiffer<Daily> = AsyncListDiffer(this, DiffCallback<Daily>())

    fun updateData(dayList: List<Daily>) = items.submitList(dayList)


    inner class DailyViewHolder(private val binding: ItemDailyBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                binding.root.setOnClickListener {
                    listener.onSelected(items.currentList[adapterPosition])
                }
            }
        }

        fun bindItem(item: Daily) {
            binding.itemDailyDay.text = item.dt.toDayName()
            binding.itemDailyTemp.text = getCurrentTemp(item.temp,item.feelsLike)
            if (item.weather.isNotEmpty()){
                Glide.with(binding.itemDailyIcon.context).load(item.weather[0].icon.getWeatherIcon()).into(binding.itemDailyIcon)
            }
        }
    }

}