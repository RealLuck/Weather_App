package com.realluck.whether_app.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import com.realluck.wheater_app.databinding.FragmentMainBinding
import com.realluck.whether_app.MainViewModel
import com.realluck.whether_app.adapters.ViewPagerAdapter
import com.realluck.whether_app.adapters.WeatherModel
import com.squareup.picasso.Picasso
import org.json.JSONObject

const val API_KEY = "2bb95ee6c7f14c32b5f163052222008"

class MainFragment : Fragment() {
    private val fragmentList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tabList = listOf(
        "Hours",
        "Days"
    )
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        updateCurrentData()
        requestWeather("London")
    }

    private fun init() = with(binding) {
        val adapter = ViewPagerAdapter(activity as FragmentActivity, fragmentList)
        viewPager.adapter = adapter
        TabLayoutMediator(tabs, viewPager) {
            tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun updateCurrentData() = with(binding) {
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            textData.text = it.time
            textCity.text = it.city
            textTemp.text = it.tempCurrent.ifEmpty { "${it.tempMax}°C/${it.tempMin}" } + "°C"
            textWeather.text = it.weather
            textMaxMinTemp.text = if (it.tempCurrent.isEmpty()) "" else "${it.tempMax}°C/${it.tempMin}°C"
            Picasso.get().load("https:" + it.imageWeather).into(imageWeather)
        }
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            Toast.makeText(activity, "Permission = $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun requestWeather(city: String) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
            API_KEY +
            "&q=" +
            city +
            "&days=" +
            "3" +
            "&aqi=no"

        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                response ->
                parseWeatherData(response)
                Log.d("Test", "Result : $response")
            },
            {
                error ->
                Log.d("Test", "Error : $error")
            }
        )
        queue.add(request)
    }

    private fun parseWeatherData(response: String) {
        val dataObject = JSONObject(response)
        val list = parseDaysData(dataObject)
        parseCurrentData(dataObject, list.first())
    }

    private fun parseCurrentData(dataObject: JSONObject, weatherItem: WeatherModel) {
        val item = WeatherModel(
            dataObject.getJSONObject("location")
                .getString("name"),
            dataObject.getJSONObject("current")
                .getString("last_updated"),
            dataObject.getJSONObject("current")
                .getJSONObject("condition")
                .getString("text"),
            dataObject.getJSONObject("current")
                .getString("temp_c").toFloat().toInt().toString(),
            weatherItem.tempMax,
            weatherItem.tempMin,
            dataObject.getJSONObject("current")
                .getJSONObject("condition")
                .getString("icon"),
            weatherItem.hours
        )
        model.liveDataCurrent.value = item
    }

    private fun parseDaysData(dataObject: JSONObject): List<WeatherModel> {
        val listOfDays = ArrayList<WeatherModel>()
        val daysArray = dataObject.getJSONObject("forecast")
            .getJSONArray("forecastday")
        val name = dataObject.getJSONObject("location")
            .getString("name")
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("text"),
                "",
                day.getJSONObject("day")
                    .getString("maxtemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day")
                    .getString("mintemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("icon"),
                day.getJSONArray("hour").toString()
            )
            listOfDays.add(item)
        }
        model.liveDataList.value = listOfDays
        return listOfDays
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
