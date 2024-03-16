package com.example.showmethecard.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.showmethecard.model.PayCard
import com.example.showmethecard.service.SamplePayCardRepository

class CardPreviewParameterProvider : PreviewParameterProvider<PayCard> {
    private val cards = SamplePayCardRepository().getCardList()
    override val values: Sequence<PayCard>
        get() = cards.asSequence()

}