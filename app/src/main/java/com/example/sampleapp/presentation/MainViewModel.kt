package com.example.sampleapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.common.State
import com.example.sampleapp.data.local.CityEntity
import com.example.sampleapp.data.local.CountryEntity
import com.example.sampleapp.data.local.HumanEntity
import com.example.sampleapp.domain.usecases.GetCitiesByCountriesUseCase
import com.example.sampleapp.domain.usecases.GetCitiesFromDbUseCase
import com.example.sampleapp.domain.usecases.GetCountriesFromDbUseCase
import com.example.sampleapp.domain.usecases.GetHumansFromDbUseCase
import com.example.sampleapp.domain.usecases.GetHumansByCitiesUseCase
import com.example.sampleapp.domain.usecases.GetHumansByCountriesUseCase
import com.example.sampleapp.domain.usecases.RefreshDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCountriesFromDbUseCase: GetCountriesFromDbUseCase,
    private val getCitiesByCountriesUseCase: GetCitiesByCountriesUseCase,
    private val getCitiesFromDbUseCase: GetCitiesFromDbUseCase,
    private val getHumansFromDbUseCase: GetHumansFromDbUseCase,
    private val getHumansByCountriesUseCase: GetHumansByCountriesUseCase,
    private val getHumansByCitiesUseCase: GetHumansByCitiesUseCase,
    private val refreshDataUseCase: RefreshDataUseCase
) : ViewModel() {

    var countries = mutableSetOf<CountryEntity>()
    var cities = mutableSetOf<CityEntity>()

    private val _allHumansResponse = MutableSharedFlow<State<List<HumanEntity>>>()
    val allHumansResponse = _allHumansResponse.asSharedFlow()

    private val _allCountries = MutableSharedFlow<List<CountryEntity>>()
    val allCountries = _allCountries.asSharedFlow()

    private val _allCities = MutableSharedFlow<List<CityEntity>>()
    val allCities = _allCities.asSharedFlow()


    init {
        getAllHumans()
    }

    fun getAllHumans(isRefreshed: Boolean = false) {
        viewModelScope.launch {
            _allHumansResponse.emit(State.Loading)
            val allPeople = getHumansFromDbUseCase()
            if (allPeople.isEmpty() || isRefreshed ) {
                refreshDataUseCase()
                    .collectLatest {
                        when (it) {
                            is State.Loading -> {}
                            is State.Success -> { _allHumansResponse.emit(State.Success(it.data)) }
                            is State.Error -> { _allHumansResponse.emit(State.Error) }
                        }
                    }
            } else {
                _allHumansResponse.emit(State.Success(allPeople))
            }
        }
    }


    fun getAllCountries() {
        viewModelScope.launch {
            val countriesFromDb = getCountriesFromDbUseCase()
            countries.addAll(countriesFromDb)
            _allCountries.emit(countriesFromDb)

        }
    }

    fun getAllCities() {
        viewModelScope.launch {
            val citiesFromDb = if (countries.isEmpty()) getCitiesFromDbUseCase()
            else getCitiesByCountriesUseCase(countries.toList())

            cities.addAll(citiesFromDb)
            _allCities.emit(citiesFromDb)
        }
    }

    fun getHumansByCities() {
        viewModelScope.launch {
            val people = getHumansByCitiesUseCase(cities.toList())
            _allHumansResponse.emit(State.Success(people))
        }
    }

    fun getHumansByCountries() {
        viewModelScope.launch {
            val people = getHumansByCountriesUseCase(countries.toList())
            _allHumansResponse.emit(State.Success(people))
        }
    }
}