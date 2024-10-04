package com.project.givuandtake.feature.mypage.MyActivities

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.project.givuandtake.R
import com.project.givuandtake.core.apis.Address.AddressApi
import com.project.givuandtake.core.apis.Address.AddressDeleteApi
import com.project.givuandtake.core.apis.Address.AddressUpdateApi
import com.project.givuandtake.core.apis.Card.CardApi
import com.project.givuandtake.core.data.Address.AddressData
import com.project.givuandtake.core.data.Address.AddressPostData
import com.project.givuandtake.core.data.Address.AddressUpdateData
import com.project.givuandtake.core.data.Address.UserAddress
import com.project.givuandtake.core.data.Card.CardData
import com.project.givuandtake.core.data.Card.UserCard
import com.project.givuandtake.core.datastore.TokenManager
import kotlinx.coroutines.launch
import retrofit2.Response

class CardUpdateViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    private val _cards = mutableStateOf<List<UserCard>>(emptyList())
    val cards: State<List<UserCard>> = _cards

    fun getCardData(token: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                Log.d("CardViewModel", "토큰: $token") // 토큰 로그

                val response: Response<CardData> = CardApi.api.getCardData(token)

                if (response.isSuccessful) {
                    val cards = response.body()?.data
                    Log.d("CardViewModel", "응답 성공: $cards") // 응답 데이터 로그
                    cards?.let {
                        val sortedCards = it.sortedByDescending { card -> card.representative }
                        _cards.value = sortedCards
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("CardViewModel", "응답 실패: ${response.code()}, 에러: $errorBody") // 에러 로그
                    errorMessage = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("CardViewModel", "오류 발생: ${e.message}") // 예외 로그
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

//    fun updateAddress(token: String, addressIdx: Int, addressData: AddressUpdateData, onUpdateSuccess: () -> Unit, onError: () -> Unit) {
//        viewModelScope.launch {
//            try {
//                val response = AddressUpdateApi.api.updateAddressData(token, addressIdx, addressData)
//                if (response.isSuccessful) {
//                    Log.d("AddressUpdate", "주소 수정 성공")
//                    onUpdateSuccess()
//                } else {
//                    Log.e("AddressUpdate", "주소 수정 실패: ${response.code()}")
//                    onError()
//                }
//            } catch (e: Exception) {
//                Log.e("AddressUpdate", "예외 발생: ${e.message}", e)
//                onError()
//            }
//        }
//    }
//    fun deleteAddress(token: String, addressIdx: Int, onDeleteSuccess: () -> Unit, onError: () -> Unit) {
//        viewModelScope.launch {
//            try {
//                val response = AddressDeleteApi.api.deleteAddress(token = "$token", addressIdx = addressIdx)
//                if (response.isSuccessful) {
//                    Log.d("AddressDelete", "주소 삭제 성공")
//                    // 삭제된 아이템을 리스트에서 제거
//                    _addresses.value = _addresses.value.filter { it.addressIdx != addressIdx }
//                    onDeleteSuccess()
//                } else {
//                    Log.e("AddressDelete", "주소 삭제 실패: ${response.code()}")
//                    onError()
//                }
//            } catch (e: Exception) {
//                Log.e("AddressDelete", "예외 발생: ${e.message}", e)
//                onError()
//            }
//        }
//    }
}



val bankUpdateList = listOf(
    CardBank("IBK기업은행", R.drawable.ibkbank),
    CardBank("수협은행", R.drawable.seabank),
    CardBank("NH농협", R.drawable.nhbank),
    CardBank("국민은행", R.drawable.kbbank),
    CardBank("신한은행", R.drawable.shinhanbank),
    CardBank("우리은행", R.drawable.webank),
    CardBank("하나은행", R.drawable.onebank),
    CardBank("부산은행", R.drawable.busanbank),
    CardBank("경남은행", R.drawable.gyeongnambank),
    CardBank("대구은행", R.drawable.daegubank),
    CardBank("광주은행", R.drawable.gwangjubank),
    CardBank("전북은행", R.drawable.junbukbank),
    CardBank("제주은행", R.drawable.jejubank),
    CardBank("SC제일은행", R.drawable.scbank),
    CardBank("씨티은행", R.drawable.citybank)
)

@Composable
fun CardUpdateList(cards: List<UserCard>, bankList: List<CardBank>) {
    LazyColumn {
        items(cards) { card ->
            val bank = bankList.firstOrNull { it.name == card.cardCompany } // 은행 찾기
            CardUpdateItem(card, bank)
        }
    }
}

@Composable
fun CardUpdateItem(card: UserCard, bank: CardBank?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 은행 로고
        if (bank != null) {
            Image(
                painter = painterResource(id = bank.imageRes),
                contentDescription = "${bank.name} logo",
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Transparent)
                    .padding(10.dp)
                    .weight(1f)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column (
            modifier = Modifier.weight(3f)
        ){
            // 은행 이름
            Text(
                text = card.cardCompany,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            // 카드 번호 (마스킹 처리)
            Text(
                text = maskCardUpdateNumber(card.cardNumber),
                fontSize = 14.sp
            )
        }
        if( card.representative ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(
                        Color(0xFFFF6F6F),
                        shape = RoundedCornerShape(50)
                    ) // 빨간색 배경과 둥근 모서리
                    .padding(horizontal = 20.dp, vertical = 2.dp)
                    .weight(1.6f)
            ) {
                Text(
                    text = "대표 카드",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1.6f)
            ) {
                Text(
                    text = "",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        if ( !card.representative ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(50)
                    ) // 빨간색 배경과 둥근 모서리
                    .padding(horizontal = 20.dp, vertical = 2.dp)
                    .weight(1.6f)
                    .border(BorderStroke(1.dp, Color(0xFFA093DE))),
            ) {
                Text(
                    text = "삭제",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
            OutlinedButton(
                onClick = {
//                    val addressData = AddressUpdateData(
//                        isRepresentative = true
//                    )
//                    viewModel.updateAddress(
//                        token = accessToken,
//                        addressIdx = address.addressIdx,
//                        addressData = addressData,
//                        onUpdateSuccess = {
//                            Log.d("AddressUpdateItem", "수정 성공")
//                            viewModel.fetchUserAddresses(accessToken)
//                        },
//                        onError = {
//                            Log.e("AddressUpdateItem", "수정 실패")
//                        }
//                    )
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(vertical = 0.dp),
                border = BorderStroke(1.dp, Color(0xFFA093DE)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFA093DE))
            ) {
                Text(
                    text = "대표 주소지로 설정",
                    modifier = Modifier.padding(vertical = 0.dp, horizontal = 10.dp).padding(0.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        OutlinedButton(
            onClick = {
//                viewModel.deleteAddress(
//                    token = accessToken,
//                    addressIdx = address.addressIdx,
//                    onDeleteSuccess = {
//                        Log.d("AddressUpdateItem", "삭제 성공")
//                    },
//                    onError = {
//                        Log.e("AddressUpdateItem", "삭제 실패")
//                    }
//                )
            },
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, Color(0xFFFF6F6F)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF6F6F))
        ) {
            Text(
                text = "삭제",
            )
        }

    }
}

fun maskCardUpdateNumber(cardNumber: String): String {
    return cardNumber.replaceRange(6, 12, "***-**")
}

@Composable
fun CardBookUpdate(navController: NavController) {
    val context = LocalContext.current
    val accessToken = "Bearer ${TokenManager.getAccessToken(context)}"

    val viewModel: CardUpdateViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.getCardData(accessToken)
    }

    val cards by viewModel.cards

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
                text = "주소 편집",
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        if (cards.isNotEmpty()) {
            CardUpdateList(cards = cards, bankList = bankList)
        } else {
            Text(text = "", modifier = Modifier.padding(16.dp))
        }


//        if (addresses.isNotEmpty()) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                items(addresses) { address ->
//                    AddressUpdateItem(address = address, viewModel = viewModel, accessToken = accessToken)
//                }
//            }
//        } else {
//            Text(text = "등록된 주소가 없습니다.", modifier = Modifier.padding(16.dp))
//        }
    }

}

//@Composable
//fun CardUpdateItem(address: UserAddress, viewModel: AddressUpdateViewModel, accessToken: String) {
//    Column() {
//        Row(
//            modifier = Modifier
//                .padding(start = 15.dp, end = 15.dp, top = 8.dp, bottom = 0.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            if (address.addressName == "우리집") {
//                Icon(
//                    painter = painterResource(id = R.drawable.house),
//                    contentDescription = "Icon",
//                    tint = Color(0xFFA093DE),
//                    modifier = Modifier.size(24.dp)
//                )
//            } else if (address.addressName == "회사" ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.company),
//                    contentDescription = "Icon",
//                    tint = Color(0xFFA093DE),
//                    modifier = Modifier.size(24.dp)
//                )
//            } else {
//                Icon(
//                    painter = painterResource(id = R.drawable.human),
//                    contentDescription = "Icon",
//                    tint = Color(0xFFA093DE),
//                    modifier = Modifier.size(24.dp)
//                )
//            }
//            Spacer(modifier = Modifier.width(15.dp))
//            Column() {
//                Row() {
//                    Text(text = address.addressName, fontSize = 16.sp)
//                    if (address.representative) {
//                        Box(
//                            modifier = Modifier
//                                .padding(horizontal = 10.dp)
//                                .background(
//                                    Color(0xFFFF6F6F),
//                                    shape = RoundedCornerShape(50)
//                                ) // 빨간색 배경과 둥근 모서리
//                                .padding(horizontal = 20.dp, vertical = 2.dp)
//                        ) {
//                            Text(
//                                text = "대표 주소지",
//                                color = Color.White,
//                                fontSize = 12.sp
//                            )
//                        }
//                    }
//                }
//
//                Text(text = "${address.roadAddress} ${address.detailAddress}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(text = address.jibunAddress, fontSize = 13.sp, color = Color.Gray)
//                Spacer(modifier = Modifier.height(4.dp))
//                Row(
//                ) {
//                    if ( !address.representative ) {
//                        OutlinedButton(
//                            onClick = {
//                                val addressData = AddressUpdateData(
//                                    isRepresentative = true
//                                )
//                                viewModel.updateAddress(
//                                    token = accessToken,
//                                    addressIdx = address.addressIdx,
//                                    addressData = addressData,
//                                    onUpdateSuccess = {
//                                        Log.d("AddressUpdateItem", "수정 성공")
//                                        viewModel.fetchUserAddresses(accessToken)
//                                    },
//                                    onError = {
//                                        Log.e("AddressUpdateItem", "수정 실패")
//                                    }
//                                )
//                            },
//                            shape = RoundedCornerShape(15.dp),
//                            border = BorderStroke(1.dp, Color(0xFFA093DE)),
//                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFA093DE))
//                        ) {
//                            Text(
//                                text = "대표 주소지로 설정",
//                            )
//                        }
//                        Spacer(modifier = Modifier.width(8.dp))
//                    }
//                    OutlinedButton(
//                        onClick = {
//                            viewModel.deleteAddress(
//                                token = accessToken,
//                                addressIdx = address.addressIdx,
//                                onDeleteSuccess = {
//                                    Log.d("AddressUpdateItem", "삭제 성공")
//                                },
//                                onError = {
//                                    Log.e("AddressUpdateItem", "삭제 실패")
//                                }
//                            )
//                        },
//                        shape = RoundedCornerShape(15.dp),
//                        border = BorderStroke(1.dp, Color(0xFFFF6F6F)),
//                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF6F6F))
//                    ) {
//                        Text(
//                            text = "삭제",
//                        )
//                    }
//
//                }
//            }
//        }
//
//        Divider(
//            color = Color(0xFFF2F2F2),
//            thickness = 3.dp,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp)
//        )
//    }
//}