package com.example.bottomnavigationview30.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bottomnavigationview30.R
import com.example.bottomnavigationview30.databinding.FragmentWeatherBinding

import com.example.bottomnavigationviewdz.utils.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        GlobalScope.launch {
            val response = try {
                RetrofitInstance.api.getCurrentWeather(
                    "Moscow",
                    "metric",
                    requireContext().getString(R.string.API_KEY)
                )
            } catch (e: IOException) {
                showToast("app error ${e.message}")
                return@launch
            } catch (e: HttpException) {
                showToast("http error ${e.message}")
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()
                    binding.cityTV.text = data!!.name
                    binding.temperatureTV.text = "${data.main.temp}°C"
                    binding.windDegreeTV.text = "${data.wind.deg}°"
                    binding.windSpeedTV.text = "${data.wind.speed}m/sec"
                    val iconId = data.weather[0].icon
                    val imageUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                    Picasso.get().load(imageUrl).into(binding.weatherIV)
                    val convertPressure = (data.main.pressure / 1.33).toInt()
                    binding.pressureTV.text = "$convertPressure mm Hg"
                }
            }
        }
    }

    private fun showToast(message: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}