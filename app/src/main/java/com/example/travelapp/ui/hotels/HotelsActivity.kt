package com.example.travelapp.ui.hotels

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.travelapp.R
import com.example.travelapp.db.Hotel
import com.google.gson.Gson

lateinit var viewModel: HotelsViewModel

class HotelsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HotelsViewModel::class.java)

        changeContent(this) { MainContent(this) }
    }

}


fun changeContent(
    activity: ComponentActivity,
    content: @Composable (activity: ComponentActivity?) -> Unit
) {

    activity.setContent { content(activity) }

}

@Preview(showBackground = false)
@Composable
private fun MainContent(activity: ComponentActivity? = null) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink)),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.hotels),
            modifier = Modifier
                .padding(start = 18.dp, top = 32.dp, bottom = 63.dp)
                .clickable {
                    activity?.let { changeContent(it) { AnotherMainContent(activity) } }
                },
            fontSize = 34.sp,
            fontFamily = FontFamily(Font(R.font.raleway_semibold))
        )
        HotelItems(viewModel = viewModel)
    }

}

@Composable
private fun HotelItems(viewModel: HotelsViewModel) {

    val hotels by viewModel.hotels.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        items(items = hotels) { hotel ->
            HotelItem(hotel)
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HotelItem(hotel: Hotel) {

    val mContext = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clickable {
                Intent(mContext, HotelActivity::class.java).also {
                    it.putExtra("hotel", Gson().toJson(hotel))
                    mContext.startActivity(it)
                }
            },
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
                GlideImage(
                    model = hotel.imageUrl,
                    modifier = Modifier.padding(end = 11.dp).size(120.dp, 86.dp),
                    contentDescription = "hotels icon",
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = hotel.name,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.raleway_regular)),
                        color = colorResource(id = R.color.hotel_city_card),
                        //fontStyle = FontStyle(R.font.raleway_regular)
                    )
                    Text(
                        text = hotel.city,
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
fun AnotherMainContent(activity: ComponentActivity? = null) {

    val hotels = hotels()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink)),
    ) {
        Text(
            text = stringResource(id = R.string.hotels),
            modifier = Modifier
                .padding(start = 18.dp, top = 32.dp, bottom = 63.dp)
                .clickable {
                    activity?.let { changeContent(it) { VerticalGridWithoutPaddings(activity) } }
                },
            fontSize = 34.sp,
            fontFamily = FontFamily(Font(R.font.raleway_semibold))
        )
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(35.dp),
            columns = GridCells.Adaptive(minSize = 150.dp),
            horizontalArrangement = Arrangement.spacedBy(35.dp)
        ) {
            items(items = hotels) { hotel ->
                AnotherHotelItem(hotel)
            }
        }
    }

}

class SampleHotelProvider : PreviewParameterProvider<Hotel> {
    override val values = sequenceOf(
        Hotel(city = "Toroni")
    )
}

@Preview(showBackground = false)
@Composable
fun AnotherHotelItem(@PreviewParameter(SampleHotelProvider::class) hotel: Hotel) {

    Card(
        modifier = Modifier
            .width(150.dp),
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
                    text = hotel.city,
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
                    text = hotel.name,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(17.dp),
                    color = colorResource(id = R.color.hotel_info_card)
                )
            }
        }
    }

}

@Preview(showBackground = false)
@Composable
fun VerticalGridWithoutPaddings(activity: ComponentActivity? = null) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink)),
    ) {
        Text(
            text = stringResource(id = R.string.hotels),
            modifier = Modifier
                .padding(start = 18.dp, top = 32.dp, bottom = 63.dp)
                .clickable {
                    activity?.let { changeContent(it) { MainContent(activity) } }
                },
            fontSize = 34.sp,
            fontFamily = FontFamily(Font(R.font.raleway_semibold))
        )
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 150.dp),
        ) {
            items(20) {
                Image(
                    painter = painterResource(id = R.drawable.hotels_img),
                    contentDescription = "hotels icon",
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

}

private fun hotels(): MutableList<Hotel> {

    return mutableListOf(
        Hotel(city = "Moscow"),
        Hotel(city = "Novosibirsk"),
        Hotel(city = "Ekaterinburg"),
        Hotel(city = "Madrid"),
        Hotel(city = "Toroni"),
        Hotel(city = "Burgas"),
        Hotel(city = "Poligiros"),
        Hotel(city = "Rybnita"),
        Hotel(city = "Chisinau"),
        Hotel(city = "Dubossary"),
        Hotel(city = "Brasov"),
        Hotel(city = "Sinaia"),
        Hotel(city = "Albena"),
        Hotel(city = "Sofia"),
    )

}
