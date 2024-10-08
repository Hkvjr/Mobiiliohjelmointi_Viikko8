package com.example.todo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.Todo
import com.example.todo.model.TodosApi
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    private val _todos = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>> get() = _todos //vain lukuoikeus

    init {
        getTodosList()
    }

    private fun getTodosList() {
        viewModelScope.launch {
            val todosApi = TodosApi.getInstance()
            try {
                val fetchedTodos = todosApi.getTodos()
                _todos.value = fetchedTodos // Päivitetään LiveData
            } catch (e: Exception) {
                Log.d("TODOVIEWMODEL", "Virhe API-kutsussa: ${e.message}")
            }
        }
    }
}