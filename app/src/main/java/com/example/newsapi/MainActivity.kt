package com.example.newsapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsapi.databinding.ActivityMainBinding
import com.example.newsapi.fragments.*
import com.example.newsapi.util.NetworkObserver

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var networkObserver: NetworkObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        networkObserver = NetworkObserver(application)
        setContentView(binding.root)
        setFragment(NewsFragment())
        binding.myconnection.layoutConnection.visibility = View.GONE
        noInternetButton()
        chipNav()
        networkConnectionObserver()

    }

    private fun noInternetButton(){
        binding.myconnection.apply {
            btnTryAgain.setOnClickListener {
                val intent =intent
                finish()
                startActivity(intent)
            }
            btnSetNetwork.setOnClickListener {
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intent)
            }
        }
    }

    private fun chipNav(){
        binding.chipNavigation.setItemSelected(R.id.newsFragment,true)
        binding.chipNavigation.setOnItemSelectedListener {
            when(it){
                R.id.newsFragment -> setFragment(NewsFragment())
                R.id.businessFragment -> setFragment(BusinessFragment())
                R.id.entertainmentFragment -> setFragment(EntertainmentFragment())
                R.id.musicFragment -> setFragment(BitcoinFragment())
                R.id.sportFragment -> setFragment(SportFragment())
                R.id.healthFragment -> setFragment(HealthFragment())
                R.id.technologyFragment -> setFragment(TechnologyFragment())
                R.id.searchFragment ->  setFragment(SearchFragment())
            }
        }
    }

    private fun networkConnectionObserver(){
        networkObserver.observe(this){ isConnected ->
            if(isConnected){
                binding.myconnection.layoutConnection.visibility = View.GONE
                binding.fragmentContainerView.visibility = View.VISIBLE
            }else{
                Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show()
                binding.myconnection.layoutConnection.visibility = View.VISIBLE
                binding.fragmentContainerView.visibility = View.GONE
            }
        }
    }

    private fun setFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView,fragment)
            .commit()
    }

}