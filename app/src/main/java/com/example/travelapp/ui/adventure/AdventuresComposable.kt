package com.example.travelapp.ui.adventure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import com.example.travelapp.R
import com.example.travelapp.db.Places

class AdventuresComposable : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

}

@Preview(showBackground = false)
@Composable
fun MainContent() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.adventures_background)),
    ) {
        Text(
            text = stringResource(id = R.string.recommended),
            modifier = Modifier
                .padding(start = 22.dp, top = 21.dp, bottom = 19.dp),
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.raleway_regular))
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier.padding(start = 22.dp)
        ) {
            items(items = places()) { place ->
                PlaceItem(place)
            }
        }
    }

}

class SamplePlaceProvider : PreviewParameterProvider<Places> {
    override val values = sequenceOf(
        Places(name = "Mountain", info = "Nepal", imageUrl = "")
    )
}

@Preview(showBackground = false)
@Composable
private fun PlaceItem(@PreviewParameter(SamplePlaceProvider::class) place: Places) {

    Card(
        shape = RoundedCornerShape(20.dp),
    ) {
        Box {
            Column(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 16.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Image(
                        modifier = Modifier.width(177.dp).height(183.dp),
                        painter = painterResource(id = R.drawable.splashscreen),
                        contentDescription = "place icon",
                        contentScale = ContentScale.FillWidth,
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 9.dp, top = 8.dp),
                ) {
                    Text(
                        text = place.name,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.raleway_regular)),
                        color = colorResource(id = R.color.hotel_city_card)
                    )
                    Text(
                        text = place.info,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.raleway_light)),
                        color = colorResource(id = R.color.hotel_info_card)
                    )
                }
            }
        }
    }

}

fun places(): List<Places> {

    return listOf(
        Places(name = "Mountain", info = "Nepal", imageUrl = ""),
        Places(name = "Lake", info = "India", imageUrl = "")
    )

}