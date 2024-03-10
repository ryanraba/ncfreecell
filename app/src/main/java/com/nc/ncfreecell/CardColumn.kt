package com.nc.ncfreecell

import android.util.Log
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

// types of columns:
// 0 = free card column
// 1 = home card column
// 2 = play card column
class CardColumn(val cards: MutableList<Card>, val type: Int) {

    // remove cards at or below this given card
    fun clipFrom(card: Card) : CardColumn {
        val cardIndex = cards.indexOf(card)
        val cutCards = cards.slice(cardIndex..cards.size-1)
        val remainingCards = cards.slice(0..cardIndex-1)
        cards.clear()
        cards.addAll(remainingCards)
        return CardColumn(cutCards.toMutableList(), type)
    }

    // can the given card column be placed in this column
    fun canPlace(column: CardColumn) : Boolean {

        if ((column.cards.size == 1)) {
            val card = column.cards.last()
            //Log.d("canPlace","clicked card " + card.number + " of " + card.suit)

            // can place a single card on an emtpy free column
            if ((type == 0) && (cards.isEmpty())) {
                //Log.d("canPlace","can place in free column")
                return true
            }
            // can place an ace in an empty home card column
            else if ((type == 1) && cards.isEmpty() && (card.number == 1)) {
                //Log.d("canPlace","can place in empty home column")
                return true
            }
            // can place any card in an empty play card column
            else if ((type == 2) && (cards.isEmpty())) {
                //Log.d("canPlace","can place in free column")
                return true
            }
            // can place a card on same suit of previous value in home card column
            else if ((type == 1) && cards.isNotEmpty() && (card.number == cards.last().number + 1) && (card.suit == cards.last().suit)) {
                //Log.d("canPlace","can place in home column")
                return true
            }
            // can place a card on opposite suit of subsequent value in play card column
            else if ((type == 2) && cards.isNotEmpty() && (card.number == cards.last().number - 1) && (card.color != cards.last().color)) {
                //Log.d("canPlace","can place in another column")
                return true
            }
        }
        //Log.d("canPlace","can't place card")
        return false
    }

    // add given column to this column
    fun addTo(column : CardColumn, force: Boolean = false) {
        if (canPlace(column) || force) {
            cards.addAll(column.cards)
        }
    }

}