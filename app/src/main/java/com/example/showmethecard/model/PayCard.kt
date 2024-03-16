package com.example.showmethecard.model


enum class CardType {
    /// 할인형 카드
    Discount,

    /// 포인트 적립형 카드
    Reward,

    /// 복합형 카드(포인트 적립 + 할인)
    Multiple;

    override fun toString(): String = when (this) {
        Discount -> "할인"
        Reward -> "적립"
        Multiple -> "할인 및 적립"
    }
}


/**
 * 카드에 대한 정보를 담고 있는 객체
 *
 * 카드의 실적이나 혜택에 대한 정보를 담고 있어 예상 혜택을 계산할 수 있다.
 * @param id 카드 식별을 위한 ID
 * @param name 카드 이름
 * @param isAbleGiftCard 상품권 실적 인정 가능 여부
 * @param isDiscountGiftCard 상품권 할인 및 적립 대상 여부
 * @param limit 모든 영역 통합 할인 및 적립 한도(0=무제한 혹은 없음)
 * @param loyalty 실적 필요 금액(0=무실적, 만원 단위)
 * @param point 전 가맹점 적립 포인트 혹은 할인 비율
 * @param extraPoints 추가 적립 및 할인 혜택 모음
 * @param bonus 보너스 리워드 (보너스 금액, 보너스 사용 실적) 튜플 형태
 * @param boost 당월 실적 충족에 따른 보너스 적립률
 * @param isBoostApplyForAll 모든 가맹점에 [boost] 적용 여부
 * @param type 카드 분류
 * @param description 카드 설명
 */
 class PayCard(
     val id: String,
     val name: String,
     val isAbleGiftCard: Boolean,
     val isDiscountGiftCard: Boolean,
     val limit: Int,
     val loyalty: Int,
     val point: Double,
     val extraPoints: Map<String, Pair<Double, Int>>? = null,
     val bonus: Pair<Int, Int>? = null,
     val boost: Map<Int, Double>? = null,
     val isBoostApplyForAll: Boolean? = null,
     val type: CardType,
     val description: String
 ) {

    /**
     * 카드 포인트를 계산하는 메서드
     *
     * [usageMoney]에 사움권 구매 실적을 제외한 실적이 인정되는 사용 금액을 입력하고, [giftCardMoney]에
     * 상품권 구매 실적 인정 금액을 기입한다.
     * 전월 실적 계산을 위해 [prevUsageMoney]에 전월 실적 인정 금액을 입력한 뒤 [usageExtraMoney]에 (혜택 영역 이름 to 사용 금액)
     * 형태로 전달하면 된다.
     */
    fun calculatePoint(usageMoney: Int, giftCardMoney: Int, prevUsageMoney: Int,
                        usageExtraMoney: Map<String, Int>?): Int {
         var boostList = boost?.keys?.toList() ?: listOf()
         boostList = boostList.sorted()
         val target = boostList.findLast {
             val extraMoney = usageExtraMoney?.values?.reduce { acc, i -> acc+i } ?: 0
             it <= (usageMoney + (if (isAbleGiftCard) giftCardMoney else 0) + extraMoney) / 10000
         }
        var result = (usageMoney + ((if (isDiscountGiftCard) giftCardMoney else 0))) *
                 (point * (((if ((target != null) && (isBoostApplyForAll == true)) boost?.get(target)
                     ?: 1 else 1))).toDouble() / 100)
         if (usageExtraMoney != null) {
             for (extra in usageExtraMoney.entries) {
                 val extraResult =
                     extra.value * (extraPoints!![extra.key]!!.first * ((if (target != null) boost!![target] else 1.0)!!) / 100)
                 val extraLimit = extraPoints[extra.key]!!.second
                 result += if (extraLimit != 0) {
                     if (extraResult > extraLimit) extraLimit.toDouble() else extraResult
                 } else {
                     extraResult
                 }
             }
         }
        if (limit == 0) {
            return result.toInt()
        }
         return if (result > limit) limit else result.toInt()
     }
 }
