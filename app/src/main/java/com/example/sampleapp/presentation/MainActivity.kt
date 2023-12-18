package com.example.sampleapp.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.common.State
import com.example.sampleapp.data.local.CityEntity
import com.example.sampleapp.data.local.CountryEntity
import com.example.sampleapp.databinding.ActivityMainBinding
import com.example.sampleapp.presentation.city.CityAdapter
import com.example.sampleapp.presentation.country.CountryAdapter
import com.example.sampleapp.presentation.humans.HumansAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    CountryAdapter.CountryRecyclerViewClickListener,
    CityAdapter.CityRecyclerViewClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val humansAdapter: HumansAdapter by lazy { HumansAdapter() }
    private val countryAdapter: CountryAdapter by lazy { CountryAdapter(this) }
    private val cityAdapter: CityAdapter by lazy { CityAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupPeopleAdapter()
        subscribeToObservers()
        clickListeners()
    }

    private fun subscribeToObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.allHumansResponse.collectLatest {
                    when (it) {
                        is State.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is State.Success -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            binding.progressBar.visibility = View.GONE
                            humansAdapter.submitList(it.data)
                        }

                        is State.Error -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.allCountries.collectLatest {
                    showFilterDialog(COUNTRY)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.allCities.collectLatest {
                    showFilterDialog(CITY)
                }
            }
        }
    }

    private fun showFilterDialog(id: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)

        val searchButton: Button = dialog.findViewById(R.id.applyFilterButton)
        val recyclerView: RecyclerView = dialog.findViewById(R.id.countryRecyclerView)

        when (id) {
            COUNTRY -> { setupCountryAdapter(recyclerView) }
            CITY -> { setupCityAdapter(recyclerView) }
        }

        searchButton.setOnClickListener {
            when (id) {
                COUNTRY -> { viewModel.getHumansByCountries() }
                CITY -> { viewModel.getHumansByCities() }
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setupCountryAdapter(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = countryAdapter
        countryAdapter.submitList(viewModel.countries.toList())
    }

    private fun setupCityAdapter(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cityAdapter
        cityAdapter.submitList(viewModel.cities.toList())
    }

    private fun setupPeopleAdapter() {
        binding.peopleRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.peopleRecyclerView.adapter = humansAdapter
    }

    override fun ClickOnCityCheckbox(country: CityEntity, isChecked: Boolean) {
        if (isChecked) {
            viewModel.cities.add(country)
        } else {
            viewModel.cities.remove(country)
        }
    }

    override fun clickOnCountryCheckbox(country: CountryEntity, isChecked: Boolean) {
        if (isChecked) {
            viewModel.countries.add(country)
        } else {
            viewModel.countries.remove(country)
        }
    }

    private fun clickListeners() {
        binding.sortByCountry.setOnClickListener { viewModel.getAllCountries() }
        binding.sortByCity.setOnClickListener { viewModel.getAllCities() }
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.getAllHumans(true) }
    }

    companion object {
        const val COUNTRY = 0
        const val CITY = 1
    }
}