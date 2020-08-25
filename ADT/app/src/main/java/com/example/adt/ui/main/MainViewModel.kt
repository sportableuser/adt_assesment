package com.example.adt.ui.main

import androidx.lifecycle.*
import com.example.adt.modal.Article
import com.example.adt.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class StateResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : StateResponse<T>(data)
    class Loading<T>(data: T? = null) : StateResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : StateResponse<T>(data, message)
}
class MainViewModel : ViewModel() {
    private val repo = NewsRepository();
    private val _stateResponse = MutableLiveData<StateResponse<List<Article>>>()
    val stateResponse = _stateResponse

    init {
        getArticles()
    }

    private fun getArticles() {
        _stateResponse.postValue(StateResponse.Loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repo.getArticles()
                if(response.isSuccessful && response.body() != null) {
                    val data = response.body()?.articles ?: emptyList()
                    _stateResponse.postValue(StateResponse.Success(data))
                } else {
                    _stateResponse.postValue(StateResponse.Error(response.message()))
                }
            }
        }
    }

    fun refresh() {
        getArticles()
    }
}