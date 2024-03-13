package com.example.showmethecard

import androidx.annotation.StringRes

sealed class Routes(val route: String, val isCanBack: Boolean, @StringRes val descriptionResourceId: Int) {
    data object Cards: Routes("cards", false, R.string.cards)
    data object CardDetail: Routes("cards/{cardId}", true, R.string.card_detail)

}