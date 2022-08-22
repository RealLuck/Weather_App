package com.realluck.whether_app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.realluck.whether_app.adapters.WeatherModel

class MainViewModel : ViewModel() {
    val liveDataCurrent = MutableLiveData<WeatherModel>()
    val liveDataList = MutableLiveData<List<WeatherModel>>()
}

