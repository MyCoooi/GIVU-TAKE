package com.project.givuandtake.auth

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.project.givuandtake.core.data.AddressDto
import com.project.givuandtake.core.data.SignupDto

class SignupViewModel : ViewModel() {
    // 회원가입 정보 관련 필드 (SignupDto)
    var signupInfo = mutableStateOf(SignupDto())
        private set // 외부에서 직접 값을 설정하지 못하도록

    // 주소 관련 필드 (AddressDto)
    var addressInfo = mutableStateOf(AddressDto())
        private set

    // 이름 업데이트 함수
    fun updateName(newName: String) {
        signupInfo.value = signupInfo.value.copy(name = newName)
    }

    // 이메일 업데이트 함수
    fun updateEmail(newEmail: String) {
        signupInfo.value = signupInfo.value.copy(email = newEmail)
    }

    // 성별 업데이트 함수
    fun updateGender(isMale: Boolean) {
        signupInfo.value = signupInfo.value.copy(isMale = isMale)
    }

    // 비밀번호 업데이트 함수
    fun updatePassword(newPassword: String) {
        signupInfo.value = signupInfo.value.copy(password = newPassword)
    }

    // 전화번호 업데이트 함수
    fun updateMobilePhone(newPhone: String) {
        signupInfo.value = signupInfo.value.copy(mobilePhone = newPhone)
    }

    // 성별 업데이트 함수
    fun updateIsMale(isMale: Boolean) {
        signupInfo.value = signupInfo.value.copy(isMale = isMale)
    }

    // 생년월일 업데이트 함수
    fun updateBirth(newBirth: String) {
        signupInfo.value = signupInfo.value.copy(birth = newBirth)
    }

    // 주소 업데이트 함수
    fun updateAddress(newAddress: String) {
        addressInfo.value = addressInfo.value.copy(address = newAddress)
    }

    // 상세주소 업데이트 함수
    fun updateDetailAddress(newDetailAddress: String) {
        addressInfo.value = addressInfo.value.copy(detailAddress = newDetailAddress)
    }

    // 주소 이름 업데이트 함수
    fun updateAddressName(newAddressName: String) {
        addressInfo.value = addressInfo.value.copy(addressName = newAddressName)
    }

    // 추가된 메서드들
    fun updateZoneCode(newZoneCode: String) {
        addressInfo.value = addressInfo.value.copy(zoneCode = newZoneCode)
    }

    fun updateUserSelectedType(newType: String) {
        addressInfo.value = addressInfo.value.copy(userSelectedType = newType)
    }

    fun updateAutoRoadAddress(newAutoRoadAddress: String) {
        addressInfo.value = addressInfo.value.copy(autoRoadAddress = newAutoRoadAddress)
    }

    fun updateAutoJibunAddress(newAutoJibunAddress: String) {
        addressInfo.value = addressInfo.value.copy(autoJibunAddress = newAutoJibunAddress)
    }

    fun updateBuildingCode(newBuildingCode: String) {
        addressInfo.value = addressInfo.value.copy(buildingCode = newBuildingCode)
    }

    fun updateBuildingName(newBuildingName: String) {
        addressInfo.value = addressInfo.value.copy(buildingName = newBuildingName)
    }

    fun updateIsApartment(isApartment: Boolean) {
        addressInfo.value = addressInfo.value.copy(isApartment = isApartment)
    }

    fun updateSido(newSido: String) {
        addressInfo.value = addressInfo.value.copy(sido = newSido)
    }

    fun updateSigungu(newSigungu: String) {
        addressInfo.value = addressInfo.value.copy(sigungu = newSigungu)
    }

    fun updateSigunguCode(newSigunguCode: String) {
        addressInfo.value = addressInfo.value.copy(sigunguCode = newSigunguCode)
    }

    fun updateRoadNameCode(newRoadNameCode: String) {
        addressInfo.value = addressInfo.value.copy(roadNameCode = newRoadNameCode)
    }

    fun updateBcode(newBcode: String) {
        addressInfo.value = addressInfo.value.copy(bcode = newBcode)
    }

    fun updateRoadName(newRoadName: String) {
        addressInfo.value = addressInfo.value.copy(roadName = newRoadName)
    }

    fun updateBname(newBname: String) {
        addressInfo.value = addressInfo.value.copy(bname = newBname)
    }

    fun updateBname1(newBname1: String) {
        addressInfo.value = addressInfo.value.copy(bname1 = newBname1)
    }


    // 생년월일을 T00:00:00 형식으로 변환하는 함수
    fun getFormattedBirth(): String {
        return try {
            val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val birthDate: java.util.Date? = dateFormat.parse(signupInfo.value.birth)

            val outputFormat =
                java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.getDefault())
            birthDate?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}