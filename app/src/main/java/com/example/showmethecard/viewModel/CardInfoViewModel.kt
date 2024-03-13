package com.example.showmethecard.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.showmethecard.service.CardRepository

class CardInfoViewModel(
    repository: CardRepository = CardRepository(),
    cardId: String
) : ViewModel() {
    private var _card = repository.searchCard(cardId)
    val card get() = _card
    private var _usageExtraMoney = if ((_card != null) && (_card!!.extraPoints != null)) {
        _card!!.extraPoints!!.keys.associate {
            (it to mutableStateOf(""))
        }
    } else {
        mapOf()
    }
    val usageExtraMoney get() = _usageExtraMoney

    var usageMoney = mutableStateOf("")
    var giftCardMoney = mutableStateOf("")

    /**
     * 실적이 인정되는 금액을 계산해주는 메서드
     */
    fun calculateUsageMoney(): Int =
        (usageMoney.value.replace(",", "").toIntOrNull() ?: 0) + (giftCardMoney.value.replace(
            ",",
            ""
        ).toIntOrNull() ?: 0)

    /**
     *
     */
    fun calculateExtraMoney(): Int {
        if (_usageExtraMoney.keys.isEmpty()) {
            return 0
        }
        return _usageExtraMoney.values
            .map { it.value.toIntOrNull() ?: 0 }
            .reduce { acc, i -> acc + i }
    }

    fun calculateTotalMoney(): Int = calculateUsageMoney() + calculateExtraMoney()

}