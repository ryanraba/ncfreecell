package com.nc.ncfreecell

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

class CardColumn(val cards: MutableList<Card>) {

    // remove cards at or below this given card
    fun clipFrom(card: Card) : CardColumn {
        val cardIndex = cards.indexOf(card)
        val cutCards = cards.slice(cardIndex..cards.size)
        val remainingCards = cards.slice(0..cardIndex-1)
        cards.clear()
        cards.addAll(remainingCards)
        return CardColumn(cutCards.toMutableList())
    }

    // add given column to this column
    fun addTo(column : CardColumn) {
        if (cards.last().CanPlace(column.cards.first())) {
            cards.addAll(column.cards)
        }
    }

    /*@Composable
    fun display() {
        Column (verticalArrangement = Arrangement.spacedBy((-40).dp)) {
            for (card in cards) {
                card.display()
            }
        }
    } */
}