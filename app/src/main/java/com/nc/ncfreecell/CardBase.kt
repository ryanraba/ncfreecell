package com.nc.ncfreecell

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier

class CardBase(val type : Int) {

    var card = Card("cards_s_01", 0)

    init {
        if (type == 1) {
            card = Card("cards_s_01", 1)
        }
    }

    @Composable
    fun display() {
        card.display()
    }
}