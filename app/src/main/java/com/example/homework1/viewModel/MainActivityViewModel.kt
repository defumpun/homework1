package com.example.homework1.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.homework1.screen.START_ELEMENTS_COUNT

class MainActivityViewModel : ViewModel() {
    var state = mutableStateOf(START_ELEMENTS_COUNT)
    var animatedStates = mutableListOf<Boolean>()

    fun addItem(){
        state.value += 1
    }

    fun addAnimationFlag(){
        animatedStates.add(false)
    }
}
