package com.nc.ncfreecell

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class GameBoard(context : Context) {

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
        CardColumn(mutableListOf(Card("cards_s_01", 0)), 0),
        CardColumn(mutableListOf(Card("cards_s_01", 0)), 0),
        CardColumn(mutableListOf(Card("cards_s_01", 0)), 0),
        CardColumn(mutableListOf(Card("cards_s_01", 0)), 0),
    )

    val cardBases = listOf(
        CardColumn(mutableListOf(Card("cards_s_01", 1)), 1),
        CardColumn(mutableListOf(Card("cards_s_01", 1)), 1),
        CardColumn(mutableListOf(Card("cards_s_01", 1)), 1),
        CardColumn(mutableListOf(Card("cards_s_01", 1)), 1),
    )

    fun moveCard(card : Card) {
        val fromCol = cardColumns.find { it.cards.contains(card) } ?: return
        val destBases = cardBases.filter { it.canPlace(card) }
        val destCols = cardColumns.filter { (it != fromCol) && it.canPlace(card) }
        val destFree = cardFreeSpots.filter { (it != fromCol) && it.canPlace(card) }

        if (destBases.isNotEmpty()) {
            destBases.first().addTo(fromCol.clipFrom(card))
        } else if (destCols.isNotEmpty()) {
            destCols.first().addTo(fromCol.clipFrom(card))
        } else if (destFree.isNotEmpty()) {
            destFree.first().addTo(fromCol.clipFrom(card))
        }
    }

    @Composable
    fun drawBoard() {
        Row(modifier = Modifier.height(Card.height).background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.spacedBy(5.dp))
        {
            cardFreeSpots.forEach{it -> it.display()}
            cardBases.forEach{it -> it.display()}
        }
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.primary).fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            cardColumns.forEach{it -> it.display()}
        }
    }
}