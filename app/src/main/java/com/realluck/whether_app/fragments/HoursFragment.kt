package com.realluck.whether_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.realluck.wheater_app.databinding.FragmentHoursBinding
import com.realluck.whether_app.MainViewModel
import com.realluck.whether_app.adapters.WeatherAdapter
import com.realluck.whether_app.adapters.WeatherModel
import org.json.JSONArray
import org.json.JSONObject

class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

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
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            adapter.submitList(getWeatherByHours(it))
        }
    }

    private fun initRecyclerView() = with(binding) {
        recyclerHours.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        recyclerHours.adapter = adapter
    }

    private fun getWeatherByHours(item: WeatherModel): List<WeatherModel> {
        val hoursList = JSONArray(item.hours)
        val list = ArrayList<WeatherModel>()

        for (i in 0 until hoursList.length()) {
            val wItem = WeatherModel(
                item.city,
                (hoursList[i] as JSONObject).getString("time"),
                (hoursList[i] as JSONObject).getJSONObject("condition").getString("text"),
                (hoursList[i] as JSONObject).getString("temp_c"),
                item.tempMax,
                item.tempMin,
                (hoursList[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )
            list.add(wItem)
        }
        return list
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}
