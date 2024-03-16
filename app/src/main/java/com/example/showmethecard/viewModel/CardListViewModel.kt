package com.example.showmethecard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.showmethecard.model.PayCard
import com.example.showmethecard.service.SamplePayCardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CardListViewModel(
    private val repository: SamplePayCardRepository = SamplePayCardRepository(),
    private val navController: NavController
) : ViewModel() {
private val _cardList: MutableStateFlow<List<PayCard>> = MutableStateFlow(
    listOf()
)
    val cardList get() = _cardList

    init {
        viewModelScope.launch {
            _cardList.value = repository.getCardList()
        }
    }


    fun onCardClick(cardId: String) {
        navController.navigate("cards/$cardId")
    }

}