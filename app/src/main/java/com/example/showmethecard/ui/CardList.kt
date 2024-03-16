package com.example.showmethecard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.showmethecard.viewModel.CardListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardList(
    viewModel: CardListViewModel = viewModel(),
    onNavigateButtonClick: (String) -> Unit
) {
    val cardList by viewModel.cardList.collectAsState()
    LazyColumn {
        items(cardList.size) {
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                onClick = { onNavigateButtonClick("cards/${cardList[it].id}") }) {
                Column(Modifier.padding(12.dp)) {
                    Text(text = cardList[it].name, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = cardList[it].description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

