package com.learning.recipeapp


import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class mainviewmodel : ViewModel() {
    private val _categorieState = mutableStateOf(recipestate())
    val _categoriesState: State<recipestate> = _categorieState



    init {
        fetchcategories()
    }



    private fun fetchcategories() {
        viewModelScope.launch {
            try {
                val response = recipeservice.getcategories()
                _categorieState.value = _categorieState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _categorieState.value = _categorieState.value.copy(
                    loading = false,
                    error = " Error fetching Categories ${e.message}"
                )
            }
        }
    }

    data class recipestate(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null

    )
}