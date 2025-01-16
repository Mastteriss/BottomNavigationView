package com.example.bottomnavigationview30

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomnavigationview30.databinding.ActivityMainBinding
import com.example.bottomnavigationview30.fragments.NotesFragment
import com.example.bottomnavigationview30.fragments.ProfileFragment
import com.example.bottomnavigationview30.fragments.WeatherFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .commit()
        }

        binding.bNav.setOnNavigationItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.profile -> ProfileFragment()
                R.id.notes -> NotesFragment()
                R.id.weather -> WeatherFragment()


                else -> null
            }

            selectedFragment?.let {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }

            true
        }
    }
}