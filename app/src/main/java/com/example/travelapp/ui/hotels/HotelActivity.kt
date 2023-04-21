package com.example.travelapp.ui.hotels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R

class HotelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainContent() {
    val text = "The rooms are equipped with air conditioning, central heating, a kitchen and a bathroom. Separate bedrooms can be booked upon request. In addition, a safety deposit box is at your disposal. Guests who prefer to cook on their own will appreciate the fully equipped kitchenette with refrigerator, mini-fridge, microwave and tea/coffee maker. Internet access, telephone, TV, radio and WiFi guarantee a modern level of comfort. There is a bathroom with a shower. Non-smoking rooms are available"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink)),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 45.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.hotels_img),
                contentDescription = "hotels icon",
                contentScale = ContentScale.Fit
            )
            Text(
                text = "Test hotel",
                fontSize = 34.sp,
                fontFamily = FontFamily(Font(R.font.raleway_semibold)),
                textAlign = TextAlign.End
            )
        }
        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
            fontFamily = FontFamily(Font(R.font.raleway_regular))
        )
    }
}