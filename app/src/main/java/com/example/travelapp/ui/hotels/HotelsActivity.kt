package com.example.travelapp.ui.hotels

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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

@Composable
private fun MainContent(activity: ComponentActivity? = null) {

    var showPicts by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink)),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
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
            Row(
                modifier = Modifier
                    .padding(top = 32.dp, end = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "show pictures")
                Switch(
                    checked = showPicts,
                    onCheckedChange = { showPicts = it }
                )
            }
        }
        HotelItems(viewModel = viewModel, showPicts)
    }

}

@Composable
private fun HotelItems(viewModel: HotelsViewModel, showPicts: Boolean) {

    val hotels by viewModel.hotels.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        items(items = hotels) { hotel ->
            HotelItem(hotel, showPicts)
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HotelItem(hotel: Hotel, showPicts: Boolean) {

    val mContext = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clickable { openHotelActivity(mContext, hotel) },
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
                if (showPicts)
                    GlideImage(
                        model = hotel.imageUrl,
                        modifier = Modifier
                            .padding(end = 11.dp)
                            .size(120.dp, 86.dp),
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

private fun openHotelActivity(context: Context, hotel: Hotel) {

    Intent(context, HotelActivity::class.java).also {
        it.putExtra("hotel", Gson().toJson(hotel))
        context.startActivity(it)
    }

}

@Preview(showBackground = false)
@Composable
fun AnotherMainContent(activity: ComponentActivity? = null) {

    val hotels by viewModel.hotels.collectAsState()
    var showPicts by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
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
            Row(
                modifier = Modifier
                    .padding(top = 32.dp, end = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "show pictures")
                Switch(
                    checked = showPicts,
                    onCheckedChange = { showPicts = it }
                )
            }
        }
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(35.dp),
            columns = GridCells.Adaptive(minSize = 150.dp),
            horizontalArrangement = Arrangement.spacedBy(35.dp)
        ) {
            items(items = hotels) { hotel ->
                AnotherHotelItem(hotel, showPicts)
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnotherHotelItem(
    @PreviewParameter(SampleHotelProvider::class) hotel: Hotel,
    showPicts: Boolean
) {

    val mContext = LocalContext.current

    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { openHotelActivity(mContext, hotel) },
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
                if (showPicts)
                    GlideImage(
                        model = hotel.imageUrl,
                        modifier = Modifier
                            .padding(top = 17.dp)
                            .size(120.dp, 86.dp),
                        contentDescription = "hotels icon",
                        contentScale = ContentScale.Crop
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

class SampleHotelProvider : PreviewParameterProvider<Hotel> {
    override val values = sequenceOf(
        Hotel(city = "Toroni")
    )
}
