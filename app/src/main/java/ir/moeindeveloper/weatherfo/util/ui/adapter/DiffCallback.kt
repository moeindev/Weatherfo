package ir.moeindeveloper.weatherfo.util.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class DiffCallback<T> : DiffUtil.ItemCallback<T>() where T : AdapterObject<*> {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.equals(newItem)
    }

}