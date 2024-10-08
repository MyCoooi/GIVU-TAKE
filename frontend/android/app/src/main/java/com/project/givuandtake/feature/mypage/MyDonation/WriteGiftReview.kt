package com.project.givuandtake.feature.mypage.MyDonation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import com.project.givuandtake.R
import com.project.givuandtake.core.data.Gift.GiftData
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun WriteGiftReview(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    val gson = remember { Gson() }

    // 전달받은 gift JSON 데이터를 객체로 변환
    val giftJson = navBackStackEntry.arguments?.getString("gift")
    val gift: GiftData? = gson.fromJson(giftJson, GiftData::class.java)

    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
                    .weight(0.3f)
            )

            Spacer(modifier = Modifier.weight(0.7f))

            Text(
                text = "후기 쓰기",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                ,fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Box(
            modifier = Modifier
                .padding(15.dp)
                .border(2.dp,Color(0xFFDAEBFD), shape = RoundedCornerShape(8.dp))
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
                .background(Color(0xFFFBFAFF))
                .clip(RoundedCornerShape(20.dp))
                .width(280.dp)
                .height(120.dp)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = gift?.giftThumbnail),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Column() {
                    Text(
                        text = gift!!.giftName,
                        fontSize = 15.sp,
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "Icon",
                            tint = Color(0xFFA093DE),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(text = "지역", fontSize = 14.sp, color = Color(0xFF8368DC))
                    }
                    Text(
                        text = "${gift!!.price}",
                        fontSize = 15.sp,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Text(
                        text = gift!!.giftName,
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text ="수량 : ${gift?.amount}개",
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        Text("$gift")
    }
}