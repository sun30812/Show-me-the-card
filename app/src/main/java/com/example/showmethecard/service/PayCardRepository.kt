package com.example.showmethecard.service

import com.example.showmethecard.model.PayCard

interface PayCardRepository {
    /**
     * 카드 목록을 가져오는 메서드
     */
    fun getCardList(): List<PayCard>

    /**
     * [cardID]를 통해 [PayCard.id]가 일치하는 카드를 찾아주는 메서드
     */
    fun searchCard(cardID: String): PayCard?
}