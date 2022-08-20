package com.realluck.whether_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.realluck.wheater_app.databinding.FragmentMainBinding


class MainFragment : Fragment()
{
    private lateinit var biniding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        biniding = FragmentMainBinding.inflate(inflater, container, false)
        return biniding.root
    }

    companion object
    {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
