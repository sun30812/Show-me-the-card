package com.example.showmethecard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

@Suppress("UNCHECKED_CAST")
class ViewModelForNavigationFactory(private val navController: NavController, private val cardId: String? = null) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(CardListViewModel::class.java) -> {
                return CardListViewModel(navController = navController) as T
            }
            modelClass.isAssignableFrom(CardInfoViewModel::class.java) -> {
                if (cardId == null) {
                    throw IllegalArgumentException("Card Info View Model needs cardId.")
                }
                return CardInfoViewModel(cardId = cardId) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}