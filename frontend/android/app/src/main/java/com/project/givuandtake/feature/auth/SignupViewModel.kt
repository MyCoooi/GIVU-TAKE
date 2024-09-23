package com.project.givuandtake.auth

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class SignupViewModel : ViewModel() {
    // Step1 data
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var phoneNumber by mutableStateOf("")

    // Step2 data
    var postalCode by mutableStateOf("")
    var address by mutableStateOf("")
    var addressDetail by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var gender by mutableStateOf("")

    // Step3 data (추가적인 맞춤 정보가 있을 경우 여기에 저장)
}
