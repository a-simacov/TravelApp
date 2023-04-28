package com.example.travelapp.ui.hotels

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.travelapp.R
import com.example.travelapp.db.Hotel
import com.google.gson.Gson

class HotelActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hotel = Gson().fromJson(
            intent.getStringExtra("hotel"), Hotel::class.java
        )

        setContent {
            MainContent(hotel)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MainContent(hotel: Hotel) {

    val mContext = LocalContext.current
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
            GlideImage(
                model = hotel.imageUrl,
                contentDescription = "hotels icon",
                modifier = Modifier
                    .size(width = 146.dp, height = 101.dp)
                    .clickable {
                        Toast.makeText(mContext, hotel.city, Toast.LENGTH_LONG).show()
                    },
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = hotel.name,
                fontSize = 34.sp,
                fontFamily = FontFamily(Font(R.font.raleway_semibold)),
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = hotel.description,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            fontFamily = FontFamily(Font(R.font.raleway_regular))
        )
    }
}