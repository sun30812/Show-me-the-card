package com.example.showmethecard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.showmethecard.model.PayCard
import com.example.showmethecard.sample.CardPreviewParameterProvider
import com.example.showmethecard.viewModel.CardInfoViewModel
import com.example.showmethecard.viewModel.ViewModelForNavigationFactory


@Composable
fun CardInfo(
    navController: NavController = rememberNavController(),
    cardId: String,
    viewModel: CardInfoViewModel = viewModel(
        factory = ViewModelForNavigationFactory(
            navController = navController, cardId = cardId
        )
    )
) {
    val card = viewModel.card ?: return Column {
        Icon(Icons.Outlined.Warning, "Not Found Card")
        Text(text = "카드를 찾을 수 없습니다.")
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            CardInformation(card)
            CardCalculator(
                card = card,
                giftCardMoney = viewModel.giftCardMoney,
                usageMoney = viewModel.usageMoney,
                usageExtraMoney = viewModel.usageExtraMoney
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
        ExpectedPoint(card, 80.dp, viewModel::calculateTotalMoney, viewModel::calculateTotalBenefit)
    }
}

@Composable
private fun CardInformation(
    card: PayCard
) {
    val dataList = card.extraPoints?.keys?.toList() ?: listOf()
    Column(Modifier.padding(8.0.dp)) {
        Text("카드 정보", style = MaterialTheme.typography.titleLarge)
        Text(text = card.description)
        Card(Modifier.padding(8.0.dp)) {
            Text(
                text = "전 가맹점: ${card.point}% ${card.type}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(8.0.dp)
                    .fillMaxWidth()
            )
        }
        Column {
            dataList.forEach {
                Card(Modifier.padding(8.0.dp)) {
                    Text(
                        text = "$it: ${card.extraPoints?.get(it)?.first ?: ""}% ${card.type}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(8.0.dp)
                            .fillMaxWidth()
                    )
                }

            }
        }
    }
}

@Composable
fun CardCalculator(
    card: PayCard,
    usageMoney: MutableState<String>,
    giftCardMoney: MutableState<String>,
    usageExtraMoney: Map<String, MutableState<String>>
) {
    val dataList = card.extraPoints?.keys?.toList() ?: listOf()

    Column(Modifier.padding(8.0.dp)) {
        Text("혜택 계산기", style = MaterialTheme.typography.titleLarge)
        Column {

            dataList.forEach { item ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "$item 영역 사용 금액: ",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(8.0.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextField(
                            value = usageExtraMoney[item]?.value ?: "", onValueChange = {
                                usageExtraMoney[item]?.value = it
                            },
                            Modifier.padding(8.0.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                        Text(text = "원")
                    }
                }

            }
            if (card.isAbleGiftCard) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "상품권 총 구매 금액: ",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(8.0.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextField(
                            value = giftCardMoney.value, onValueChange = {
                                giftCardMoney.value = it
                            },
                            Modifier.padding(8.0.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                        Text(text = "원")
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "기타 실적 반영 가맹점: ",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(8.0.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = usageMoney.value, onValueChange = {
                            usageMoney.value = it
                        },
                        Modifier.padding(8.0.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                    Text(text = "원")
                }
            }
        }
    }

}

@Composable
private fun ExpectedPoint(
    card: PayCard, height: Dp, calculateTotalMoney: () -> Int,
    calculateTotalBenefit: () -> Int
) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            .shadow(0.5.dp)
            .height(height)
    ) {
        var totalUsageMoney by remember {
            mutableIntStateOf(0)
        }
        var totalBenefit by remember {
            mutableIntStateOf(0)
        }
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.0.dp)
            ) {

                Text(text = "실적 반영 금액: ${totalUsageMoney}원")
                Text(text = "예상 혜택: $totalBenefit ${card.type}")

            }
            Button(modifier = Modifier.padding(8.0.dp), onClick = {
                totalUsageMoney = calculateTotalMoney()
                totalBenefit = calculateTotalBenefit()
            }) {
                Text(text = "계산하기")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardInformationPreview(
    @PreviewParameter(CardPreviewParameterProvider::class) card: PayCard
) {
    CardInformation(card = card)
}

@Preview(showBackground = true)
@Composable
fun CardCalculatorPreview(
    @PreviewParameter(CardPreviewParameterProvider::class) card: PayCard,
) {

    val giftCardMoney = remember {
        mutableStateOf("")
    }
    val usageMoney = remember {
        mutableStateOf("")
    }

    CardCalculator(
        card = card,
        giftCardMoney = giftCardMoney,
        usageMoney = usageMoney,
        usageExtraMoney = mapOf()
    )
}