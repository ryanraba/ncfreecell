package com.nc.ncfreecell

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nc.ncfreecell.ui.theme.NCFreecellTheme


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
    var gameBoard = GameBoard(context)
    var resetBoard by remember { mutableStateOf(0) }
    Log.d("GameBox","resetBoard: " + resetBoard)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("FreeCell", fontSize=24.sp, lineHeight = 28.sp, textAlign=TextAlign.Center)
                },
                actions = {
                    Spacer(modifier = Modifier.weight(1f))
                    gameBoard.DrawMoves()
                    Spacer(modifier = Modifier.weight(0.15f))
                    gameBoard.DrawScore()
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
                            gameBoard.resetGame()
                            resetBoard++
                        }) {
                        Icon(Icons.TwoTone.Add, contentDescription = "New Game")
                        Text("New Game")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = Color.Black),
                        onClick = { /* do something */ }) {
                        Icon(Icons.TwoTone.Refresh, contentDescription = "Undo")
                        Text("Undo")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = Color.Black),
                        onClick = { /* do something */ }) {
                        Icon(Icons.TwoTone.Settings, contentDescription = "Settings" )
                        Text("Settings")
                    }
                }
            )
        },
        content = { padding ->
            Column(verticalArrangement = Arrangement.spacedBy((10).dp),
                   modifier = Modifier
                       .padding(padding)
                       .background(MaterialTheme.colorScheme.primary)) {
                gameBoard.DrawBoard()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GameBox()
}