package com.nc.ncfreecell

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nc.ncfreecell.ui.theme.NCFreecellTheme


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

fun shuffleDeck(context : Context) : List<CardColumn> {
    val resources = context.getResources()

    val cardsShuffled = cardDeck.shuffled().map { Card(it, resources.getIdentifier(it, "drawable", context.getPackageName())) }

    val cardColumns = listOf(
        CardColumn(cardsShuffled.slice(0..6).toMutableList()),
        CardColumn(cardsShuffled.slice(7..13).toMutableList()),
        CardColumn(cardsShuffled.slice(14..20).toMutableList()),
        CardColumn(cardsShuffled.slice(21..27).toMutableList()),
        CardColumn(cardsShuffled.slice(28..33).toMutableList()),
        CardColumn(cardsShuffled.slice(34..39).toMutableList()),
        CardColumn(cardsShuffled.slice(40..45).toMutableList()),
        CardColumn(cardsShuffled.slice(46..51).toMutableList()),
    )

    return cardColumns
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NCFreecellTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameBox()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameBox() {

    val context = LocalContext.current

    var cardColumns = remember { mutableStateListOf<CardColumn>()}
    cardColumns.clear()
    cardColumns.addAll(shuffleDeck(context))

    //val goalListClubs = mutableListOf()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        modifier = Modifier.size(width = 50.dp, height = 35.dp)
                    ) {
                        Text("Score\n0", fontSize=12.sp, lineHeight = 14.sp, textAlign=TextAlign.Center)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.background,
                actions = {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = Color.Black),
                        onClick = {
                            cardColumns.clear()
                            cardColumns.addAll(shuffleDeck(context))
                        }) {Icon(Icons.TwoTone.Add, contentDescription = "New Game")}
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = Color.Black),
                        onClick = { /* do something */ }) {
                        Icon(Icons.TwoTone.Clear, contentDescription = "Undo")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = Color.Black),
                        onClick = { /* do something */ }) {
                        Icon(Icons.TwoTone.Settings, contentDescription = "Settings" )
                    }
                }
            )
        },
        content = { padding ->
            Column(verticalArrangement = Arrangement.spacedBy((10).dp),
                   modifier = Modifier
                       .padding(padding)
                       .background(MaterialTheme.colorScheme.primary)) {
                Row(modifier = Modifier.height(Card.height).background(MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.spacedBy(5.dp))
                {
                    CardBase(0).display()
                    CardBase(0).display()
                    CardBase(0).display()
                    CardBase(0).display()
                    CardBase(1).display()
                    CardBase(1).display()
                    CardBase(1).display()
                    CardBase(1).display()
                }
                Row(modifier = Modifier.background(MaterialTheme.colorScheme.primary).fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    for (column in cardColumns) {
                        //column.display()
                        Column (verticalArrangement = Arrangement.spacedBy((-40).dp)) {
                            for (card in column.cards) {
                                Card(onClick = {}){ card.display() }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GameBox()
}