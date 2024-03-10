package com.nc.ncfreecell

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class GameBoard(context : Context) {
    val vPad = (-40).dp
    val hPad = 5.dp

    val cardDeck = listOf(
        "cards_c_01", "cards_d_01", "cards_h_01", "cards_s_01",
        "cards_c_02", "cards_d_02", "cards_h_02", "cards_s_02",
        "cards_c_03", "cards_d_03", "cards_h_03", "cards_s_03",
        "cards_c_04", "cards_d_04", "cards_h_04", "cards_s_04",
        "cards_c_05", "cards_d_05", "cards_h_05", "cards_s_05",
        "cards_c_06", "cards_d_06", "cards_h_06", "cards_s_06",
        "cards_c_07", "cards_d_07", "cards_h_07", "cards_s_07",
        "cards_c_08", "cards_d_08", "cards_h_08", "cards_s_08",
        "cards_c_09", "cards_d_09", "cards_h_09", "cards_s_09",
        "cards_c_10", "cards_d_10", "cards_h_10", "cards_s_10",
        "cards_c_11", "cards_d_11", "cards_h_11", "cards_s_11",
        "cards_c_12", "cards_d_12", "cards_h_12", "cards_s_12",
        "cards_c_13", "cards_d_13", "cards_h_13", "cards_s_13"
    )

    val resources = context.getResources()
    val cardsShuffled = cardDeck.shuffled().map { Card(it, resources.getIdentifier(it, "drawable", context.getPackageName())) }

    val cardColumns = listOf(
        CardColumn(cardsShuffled.slice(0..6).toMutableList(), 2),
        CardColumn(cardsShuffled.slice(7..13).toMutableList(), 2),
        CardColumn(cardsShuffled.slice(14..20).toMutableList(), 2),
        CardColumn(cardsShuffled.slice(21..27).toMutableList(), 2),
        CardColumn(cardsShuffled.slice(28..33).toMutableList(), 2),
        CardColumn(cardsShuffled.slice(34..39).toMutableList(), 2),
        CardColumn(cardsShuffled.slice(40..45).toMutableList(), 2),
        CardColumn(cardsShuffled.slice(46..51).toMutableList(), 2),
    )

    val cardFreeSpots = listOf(
        CardColumn(mutableListOf<Card>(), 0),
        CardColumn(mutableListOf<Card>(), 0),
        CardColumn(mutableListOf<Card>(), 0),
        CardColumn(mutableListOf<Card>(), 0)
    )

    val cardBases = listOf(
        CardColumn(mutableListOf<Card>(), 1),
        CardColumn(mutableListOf<Card>(), 1),
        CardColumn(mutableListOf<Card>(), 1),
        CardColumn(mutableListOf<Card>(), 1),
    )

    fun moveCard(card : Card) {
        val fromCol = (cardColumns + cardFreeSpots + cardBases).find { it.cards.contains(card) } ?: return
        val cardCol = fromCol.clipFrom(card)
        val destBases = cardBases.filter { it.canPlace(cardCol) }
        val destCols = cardColumns.filter { (it != fromCol) && it.canPlace(cardCol) }
        val destFree = cardFreeSpots.filter { (it != fromCol) && it.canPlace(cardCol) }

        if (destBases.isNotEmpty()) {
            destBases.first().addTo(cardCol)
        }
        else if (destCols.isNotEmpty()) {
            destCols.first().addTo(cardCol)
        } else if (destFree.isNotEmpty()) {
            destFree.first().addTo(cardCol)
        } else {  // put it back where it came from
            Log.d("moveCard","putting card back")
            fromCol.addTo(cardCol, true)
        }
    }

    @Composable
    fun drawBoard() {
        //var offsetX by remember { mutableStateOf(0f) }
        //var offsetY by remember { mutableStateOf(0f) }
        var cardClicked by rememberSaveable { mutableStateOf<Card?>(null) }

        // when a card is clicked, move it before redrawing the board
        if (cardClicked != null) {
            Log.d("drawboard","clicked card " + cardClicked!!.number + " of " + cardClicked!!.suit)
            moveCard(cardClicked!!)
            cardClicked = null
        }

        Row(modifier = Modifier.height(Card.height).background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.spacedBy(hPad))
        {
            cardFreeSpots.forEach {it ->
                Column(verticalArrangement = Arrangement.spacedBy(vPad)) {
                    if (it.cards.isEmpty()) {
                        OutlinedCard(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier.size(Card.width, Card.height)
                        ) {
                            Text(text = "O", textAlign = TextAlign.Center)
                        }
                    }
                    else {
                        Card(Modifier.clickable { cardClicked = it.cards.last() }) {
                            Image(
                                painter = painterResource(it.cards.last().id),
                                contentDescription = "card",
                                modifier = Modifier.size(Card.width, Card.height),
                            )
                        }
                    }
                }
            }

            cardBases.forEach { it ->
                Column(verticalArrangement = Arrangement.spacedBy(vPad)) {
                    if (it.cards.isEmpty()) {
                        OutlinedCard(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier.size(Card.width, Card.height)
                        ) {
                            Text(text = "A", textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
                        }
                    }
                    else {
                        Card(Modifier.clickable { cardClicked = it.cards.last() }) {
                            Image(
                                painter = painterResource(it.cards.last().id),
                                contentDescription = "card",
                                modifier = Modifier.size(Card.width, Card.height),
                            )
                        }
                    }
                }
            }
        }

        Row(modifier = Modifier.background(MaterialTheme.colorScheme.primary).fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(hPad),
        ) {
            cardColumns.forEach { it ->
                Column(verticalArrangement = Arrangement.spacedBy(vPad)) {
                    if (it.cards.isEmpty()) {
                        OutlinedCard(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier.size(Card.width, Card.height)
                        ) {
                            Text(text = "", textAlign = TextAlign.Center)
                        }
                    } else {
                        it.cards.forEach { cc ->
                            //Card(Modifier.offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                            //    .pointerInput(Unit) {
                            //        detectDragGestures { change, dragAmount ->
                            //            change.consume()
                            //            offsetX += dragAmount.x
                            //            offsetY += dragAmount.y
                            //        }
                            //    }) {
                            Card(Modifier.clickable { cardClicked = cc }) {
                                Image(
                                    painter = painterResource(cc.id),
                                    contentDescription = "card",
                                    modifier = Modifier.size(Card.width, Card.height),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}