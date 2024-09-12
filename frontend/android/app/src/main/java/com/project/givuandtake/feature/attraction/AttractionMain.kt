import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.givuandtake.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun AttractionMain() {
    var province by remember { mutableStateOf("도 선택") }
    var state by remember { mutableStateOf("구 선택") }
    var availableStates by remember { mutableStateOf(listOf<String>()) }
    var selectedLocation by remember { mutableStateOf("") }
    var expandedProvince by remember { mutableStateOf(false) }
    var expandedState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB3C3F4))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Grouped box for Province and State dropdowns with GIF
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB3C3F4), shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Dropdown buttons
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    // Province Dropdown
                    Box(modifier = Modifier.wrapContentSize()) {
                        OutlinedButton(
                            onClick = { expandedProvince = !expandedProvince },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(text = province)
                        }
                        DropdownMenu(
                            expanded = expandedProvince,
                            onDismissRequest = { expandedProvince = false }
                        ) {
                            listOf("부산광역시", "서울특별시", "대구광역시").forEach { selectedProvince ->
                                DropdownMenuItem(onClick = {
                                    province = selectedProvince
                                    expandedProvince = false
                                    availableStates = when (selectedProvince) {
                                        "부산광역시" -> listOf("영도구")
                                        "서울특별시" -> listOf("강남구")
                                        "대구광역시" -> listOf("수성구")
                                        else -> listOf()
                                    }
                                    state = "구 선택"
                                    selectedLocation = ""
                                }) {
                                    Text(text = selectedProvince)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // State Dropdown
                    Box(modifier = Modifier.wrapContentSize()) {
                        OutlinedButton(
                            onClick = { expandedState = !expandedState },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(text = state)
                        }
                        DropdownMenu(
                            expanded = expandedState,
                            onDismissRequest = { expandedState = false }
                        ) {
                            availableStates.forEach { selectedState ->
                                DropdownMenuItem(onClick = {
                                    state = selectedState
                                    expandedState = false
                                    if (province != "도 선택" && state != "구 선택") {
                                        selectedLocation = "$province $state"
                                    }
                                }) {
                                    Text(text = selectedState)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Temperature and Weather info box with GifImage on the right
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4099E9), shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = if (selectedLocation.isNotEmpty()) selectedLocation else "지역을 선택해주세요",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(text = "31°C", fontSize = 36.sp, color = Color(0xFFFCBE22))
                }
                GifImage() // GifImage is now placed to the right of the text
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "전통시장 축제 관광지 체험마을", fontSize = 14.sp, color = Color.Gray)

        // Market information
        Column {
            MarketItem(marketName = "안양관양시장", parkingAvailable = true, restroomAvailable = true)
            MarketItem(marketName = "석수시장", parkingAvailable = true, restroomAvailable = false)
            MarketItem(marketName = "의정부시장", parkingAvailable = false, restroomAvailable = false)
        }
    }
}

@Composable
fun GifImage() {
    GlideImage(
        imageModel = R.drawable.sunny, // assuming sunny.gif is in the res/drawable folder
        modifier = Modifier.size(150.dp) // Increase size to 200%
    )
}

@Composable
fun MarketItem(marketName: String, parkingAvailable: Boolean, restroomAvailable: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = marketName, fontSize = 20.sp, color = Color.Black)
            Text(text = "전통시장", fontSize = 14.sp, color = Color.Gray)
        }

        if (parkingAvailable) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Parking available")
        }
        if (restroomAvailable) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Default.DateRange, contentDescription = "Restroom available")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAttractionMain() {
    AttractionMain()
}

