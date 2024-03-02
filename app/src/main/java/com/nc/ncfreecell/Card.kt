package com.nc.ncfreecell

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// id 0 = empty card space
// id 1 = card home base
// other ids are drawable resource ids
class Card(val name : String, val id : Int) {

    val suit = name[6]     // c, s, d, or h
    val number = name.slice(8..9).toInt()  // 1 == ace to 13 == king

    companion object {
        val width = 45.dp
        val height = 65.dp
    }

    // check if this card can be placed on another
    fun CanPlace(other : Card) : Boolean {
        if ((other.id == 0) || ((other.id == 1) && (number == 1))) {
            return true
        } else if (other.id == 1) {
            return false
        } else if ( ((suit == 'c') || (suit == 's')) && ((other.suit == 'c') || (other.suit == 's')) ) {
            return false
        } else if ( ((suit == 'd') || (suit == 'h')) && ((other.suit == 'd') || (other.suit == 'h')) ) {
            return false
        }

        return (other.number - number) == 1
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun display()  {
        if ((id == 0) || (id == 1)) {
            OutlinedCard(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.size(width, height)
            ) {
                if (id == 0) {
                    Text(
                        text = "O",
                        textAlign = TextAlign.Center,
                    )
                } else {
                    Text(
                        text = "A",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        } else {
            Card {
                Image(
                    painter = painterResource(id),
                    contentDescription = "card",
                    modifier = Modifier.size(width, height),
                )
            }
        }
    }

}