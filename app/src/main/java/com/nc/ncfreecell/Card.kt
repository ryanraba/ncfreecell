package com.nc.ncfreecell

import androidx.compose.ui.unit.dp

// name must match card resource naming scheme
// id is the resource id of the drawable
class Card(val name: String, val id: Int) {

    val suit = name[6]     // c, s, d, or h
    val color = if ((suit == 'c') || (suit == 's')) 'b' else 'r'
    val number = name.slice(8..9).toInt()  // 1 == ace to 13 == king

    companion object {
        val width = 45.dp
        val height = 65.dp
    }

}