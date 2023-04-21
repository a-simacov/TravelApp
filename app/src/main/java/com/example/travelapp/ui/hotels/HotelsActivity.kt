package com.example.travelapp.ui.hotels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R

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

class HotelsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
            //AnotherMainContent()
        }
    }

}
//@Preview(showBackground = false)
@Composable
private fun MainContent() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink)),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.hotels),
            modifier = Modifier.padding(start = 18.dp, top = 32.dp, bottom = 63.dp),
            fontSize = 34.sp,
            fontFamily = FontFamily(Font(R.font.raleway_semibold))
        )
        hotels().forEach { (city, info) -> HotelGroupItem(city, info) }
    }

}

//@Preview(showBackground = false)
@Composable
private fun HotelGroupItem(city: String = defaultCity, info: String = defaultInfo) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        shape = RectangleShape,
        elevation = 7.dp
    ) {
        Box(
            modifier = Modifier
                .padding(start = 6.dp, top = 30.dp, bottom = 24.dp, end = 30.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.padding(end = 11.dp),
                    painter = painterResource(id = R.drawable.hotels_img),
                    contentDescription = "hotels icon",
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = city,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.raleway_regular)),
                        color = colorResource(id = R.color.hotel_city_card)
                    )
                    Text(
                        text = info,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.raleway_regular)),
                        color = colorResource(id = R.color.hotel_info_card)
                    )
                }
            }

        }
    }

}


@Preview(showBackground = false)
@Composable
fun AnotherMainContent() {

    val hotels = hotels()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink)),
    ) {
        Text(
            text = stringResource(id = R.string.hotels),
            modifier = Modifier.padding(start = 18.dp, top = 32.dp),
            fontSize = 34.sp
        )
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(35.dp)
        ) {
            repeat(3) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(35.dp),
                ) {
                    hotels.subList(0, 2).forEach {
                        (city, info) -> AnotherHotelGroupItem(city, info)
                    }
                }
            }
        }
    }

}

@Preview(showBackground = false)
@Composable
fun AnotherHotelGroupItem(city: String = defaultCity, info: String = defaultInfo) {

    Card(
        modifier = Modifier.width(150.dp),
        shape = RectangleShape,
        elevation = 7.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = city,
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.hotel_city_card)
                )
                Image(
                    modifier = Modifier.padding(top = 17.dp),
                    painter = painterResource(id = R.drawable.hotels_img),
                    contentDescription = "hotels icon",
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = info,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(17.dp),
                    color = colorResource(id = R.color.hotel_info_card)
                )
            }
        }
    }

}
