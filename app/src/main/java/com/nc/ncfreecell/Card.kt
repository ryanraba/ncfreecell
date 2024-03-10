package com.nc.ncfreecell

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

// id 0 = empty card space
// id 1 = card home base
// other ids are drawable resource ids
class Card(val name : String, val id : Int) {

    val suit = name[6]     // c, s, d, or h
    val number = name.slice(8..9).toInt()  // 1 == ace to 13 == king
    val parent = null   // card attached above this card
    val child = null   // card attached underneath this card

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
        //var offsetX by remember { mutableStateOf(0f) }
        //var offsetY by remember { mutableStateOf(0f) }
        var cardClicked by rememberSaveable { mutableStateOf<Int?>(null) }

        // create base locations for free spots and home spots
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
            //Card(Modifier.offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            //    .pointerInput(Unit) {
            //        detectDragGestures { change, dragAmount ->
            //            change.consume()
            //            offsetX += dragAmount.x
            //            offsetY += dragAmount.y
            //        }
            //    }) {
            Card(Modifier.clickable { cardClicked = 1 }) {
                Image(
                    painter = painterResource(id),
                    contentDescription = "card",
                    modifier = Modifier.size(width, height),
                )
            }

        }
    }

}