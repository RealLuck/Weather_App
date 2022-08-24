package com.realluck.whether_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.realluck.wheater_app.databinding.FragmentHoursBinding
import com.realluck.whether_app.adapters.WeatherAdapter
import com.realluck.whether_app.adapters.WeatherModel

class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding) {
        recyclerHours.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        recyclerHours.adapter = adapter
        // test data list
        val list = listOf(
            WeatherModel(time = "22:20", tempCurrent = "25'C", weather = "Sunny", tempMax = "", tempMin = "", city = "", imageWeather = "", hours = ""),
            WeatherModel(time = "23:20", tempCurrent = "23'C", weather = "Rain", tempMax = "", tempMin = "", city = "", imageWeather = "", hours = ""),
            WeatherModel(time = "08:20", tempCurrent = "19'C", weather = "Rain", tempMax = "", tempMin = "", city = "", imageWeather = "", hours = "")
        )
        adapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}
