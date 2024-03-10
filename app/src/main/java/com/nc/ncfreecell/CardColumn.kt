package com.nc.ncfreecell

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// type 0 = free card space, can only be length 1
// type 1 = card home base
// type >1 = regular column
class CardColumn(val cards: MutableList<Card>, val type : Int) {

    // remove cards at or below this given card
    fun clipFrom(card: Card) : CardColumn {
        val cardIndex = cards.indexOf(card)
        val cutCards = cards.slice(cardIndex..cards.size)
        val remainingCards = cards.slice(0..cardIndex-1)
        cards.clear()
        cards.addAll(remainingCards)
        return CardColumn(cutCards.toMutableList(),2)
    }

    fun canPlace(card: Card) : Boolean {
        if (cards.isEmpty()) {
            return true
        }
        return cards.last().CanPlace(card)
    }

    // add given column to this column
    fun addTo(column : CardColumn) {
        if (cards.last().CanPlace(column.cards.first())) {
            cards.addAll(column.cards)
        }
    }

    @Composable
    fun display() {
        Column (verticalArrangement = Arrangement.spacedBy((-40).dp)) {
            for (card in cards) {
                 card.display()
            }
        }
    }
}