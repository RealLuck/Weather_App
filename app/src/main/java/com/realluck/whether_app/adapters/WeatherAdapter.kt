package com.realluck.whether_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.realluck.wheater_app.R
import com.realluck.wheater_app.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class WeatherAdapter(val listener: Listener?) : ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    class Holder(view: View, val listener: Listener?) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)
        lateinit var temporal: WeatherModel
        fun init(item: WeatherModel) = with(binding) {
            temporal = item
            textItemDate.text = item.time
            textItemWeather.text = item.weather
            textItemTemp.text = item.tempCurrent.ifEmpty { "${item.tempMax}°C/${item.tempMin}" } + "°C"
            Picasso.get().load("https:" + item.imageWeather).into(imageItem)
        }
        init {
            itemView.setOnClickListener {
                listener?.onClick(temporal)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<WeatherModel>() {
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.init(getItem(position))
    }

    interface Listener {
        fun onClick(item: WeatherModel)
    }
}
