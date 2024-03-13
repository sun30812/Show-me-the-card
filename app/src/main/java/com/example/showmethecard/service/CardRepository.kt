package com.example.showmethecard.service

import com.example.showmethecard.model.CardType
import com.example.showmethecard.model.PayCard

class CardRepository {
    fun getCardList() =
        listOf(
            PayCard(
                id = "dkfmk32-343",
                name = "현대카드 M Boost",
                isAbleGiftCard = true,
                isDiscountGiftCard = false,
                limit = 0,
                loyalty = 0,
                point = 0.5,
                extraPoints = mapOf(
                    "온라인 간편결제" to Pair(5.0, 10000),
                    "커피(이디야, 폴바셋, 스타벅스), 영화(CGV, 롯데시네마, 메가박스)" to Pair(3.0, 0),
                    "대중교통, 베이커리" to Pair(2.0, 0),
                    "편의점, 학원" to Pair(1.0, 0),
                    "외식, 통신" to Pair(0.7, 0)
                ),
                bonus = Pair(10000, 1000000),
                boost = mapOf(0 to 0.0, 50 to 1.0, 100 to 1.5),
                isBoostApplyForAll = true,
                type = CardType.Reward,
                description =
                "어디서나 한도 없는 최대 3% X 1.5배 적립\n페이·해외결제 5% 적립\n당월 이용금액 100만원 이상 시 1만 M포인트 추가 적립"
            ),
            PayCard(
                id = "dkfmk32-343",
                name = "현대카드 M3 Boost",
                isAbleGiftCard = true,
                isDiscountGiftCard = false,
                limit = 0,
                loyalty = 0,
                point = 0.5,
                extraPoints = mapOf(
                    "온라인 간편결제" to Pair(5.0, 10000),
                    "커피(이디야, 폴바셋, 스타벅스), 영화(CGV, 롯데시네마, 메가박스)" to Pair(3.0, 0),
                    "대중교통, 베이커리" to Pair(2.0, 0),
                    "편의점, 학원" to Pair(1.0, 0),
                    "외식, 통신" to Pair(0.7, 0)
                ),
                bonus = Pair(10000, 1000000),
                boost = mapOf(0 to 0.0, 50 to 1.0, 100 to 1.5, 200 to 2.0),
                isBoostApplyForAll = true,
                type = CardType.Reward,
                description =
                "어디서나 한도 없는 최대 3% X 1.5배 적립\n페이·해외결제 5% 적립\n당월 이용금액 100만원 이상 시 1만 M포인트 추가 적립"
            ),
            PayCard(
                id = "dkfmk32-100",
                name = "SC Zero Ed2 포인트형",
                isAbleGiftCard = false,
                isDiscountGiftCard = false,
                limit = 0,
                loyalty = 0,
                point = 1.0,
                extraPoints = mapOf(
                    "온라인 간편결제" to Pair(2.5, 0),
                    "커피(이디야, 폴바셋, 스타벅스)" to Pair(2.5, 0),
                    "음식점" to Pair(2.5, 0),
                    "대형마트 할인점" to Pair(2.5, 0),
                    "편의점" to Pair(2.5, 0)
                ),
                type = CardType.Reward,
                description =
                "어디서나 한도 없는 최대 3% X 1.5배 적립\n페이·해외결제 5% 적립\n당월 이용금액 100만원 이상 시 1만 M포인트 추가 적립"
            ),
            PayCard(
                id = "dkfmk32-113",
                name = "현대 Zero Ed3 할인형",
                isAbleGiftCard = false,
                isDiscountGiftCard = false,
                limit = 0,
                loyalty = 0,
                point = 0.8,
                type = CardType.Discount,
                description =
                "실적, 한도 제한 없는 카드"
            )


        )

    fun searchCard(cardId: String) = getCardList().find {
        it.id == cardId
    }

}