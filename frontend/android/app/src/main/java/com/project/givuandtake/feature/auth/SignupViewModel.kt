package com.project.givuandtake.auth

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class SignupViewModel : ViewModel() {
    // signUpDto 관련 필드
    var name by mutableStateOf("")
    var isMale by mutableStateOf(false)
    var birth by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var mobilePhone by mutableStateOf("")
    var landlinePhone = "02-1234-5678"
    var profileImageUrl = "http://example.com/profile.jpg"
    var roles = "ROLE_CLIENT"  // 고정된 값
    var isSocial = false
    var socialType: String? = null
    var socialSerialNum: String? = null

    // addressAddDto 관련 필드
    var zoneCode by mutableStateOf("")
    var addressName by mutableStateOf("")
    var address by mutableStateOf("")
    var userSelectedType = "R"  // 고정된 값
    var roadAddress by mutableStateOf("")
    var jibunAddress by mutableStateOf("")
    var detailAddress by mutableStateOf("")
    var autoRoadAddress by mutableStateOf("")
    var autoJibunAddress by mutableStateOf("")
    var buildingCode by mutableStateOf("")
    var buildingName by mutableStateOf("")
    var isApartment by mutableStateOf(false)
    var sido by mutableStateOf("")
    var sigungu by mutableStateOf("")
    var sigunguCode by mutableStateOf("")
    var roadNameCode by mutableStateOf("")
    var bcode by mutableStateOf("")
    var roadName by mutableStateOf("")
    var bname by mutableStateOf("")
    var bname1 by mutableStateOf("")
    var isRepresentative = true
}
