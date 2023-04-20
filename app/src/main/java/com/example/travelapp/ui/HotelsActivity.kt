package com.example.travelapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R

class HotelsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.pink))
            ) {
                Text(
                    text = stringResource(id = R.string.hotels),
                    modifier = Modifier.padding(start = 18.dp, top = 32.dp),
                    fontSize = 34.sp
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 18.dp, end = 18.dp, top = 121.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    hotels().forEach { hotel ->
                        val (city, info) = hotel
                        HotelGroupItem(city, info)
                    }
                }
            }
        }
    }
}

const val defaultInfo = "The Ideal Hotel at a Great Price"
const val defaultCity = "Moscow"

data class Hotel(val city: String, val info: String = defaultInfo)

private fun hotels(): MutableList<Hotel> {
    return mutableListOf(
        Hotel(city = "Moscow"),
        Hotel(city = "Novosibirsk"),
        Hotel(city = "Madrid")
    )
}

@Preview(showBackground = false)
@Composable
fun HotelGroupItem(city: String = defaultCity, info: String = defaultInfo) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .height(140.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "\n\n\n\n\n\n",
            modifier = Modifier
                .background(Color.Black)
                .width(120.dp)
                .padding(start = 8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 11.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = city, fontSize = 24.sp)
            Text(text = info, fontSize = 18.sp)
        }
    }
}
