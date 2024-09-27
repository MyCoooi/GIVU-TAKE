import axios from "axios";

// const apiUrl = "https://j11e202.p.ssafy.io/api";
const apiUrl = "/dev/api";

// 시도 데이터를 가져오는 함수
export const getSido = async () => {
  try {
    const response = await axios.get(`${apiUrl}/regions/sido`); // 백틱 사용
    return response.data;
  } catch (error) {
    console.error("Failed to fetch 시도 data:", error);
    throw error;
  }
};

// 시군구 데이터를 가져오는 함수
export const getSigungu = async (sido) => {
  try {
    const response = await axios.get(`${apiUrl}/regions/sigungu`, { // 백틱 사용
      params: {
        sido, // 시도 파라미터를 쿼리스트링으로 전달
      },
    });
    return response.data;
  } catch (error) {
    console.error("Failed to fetch 시군구 data:", error);
    throw error;
  }
};

// 회원가입 요청을 보내는 함수
export const signUp = async (formData) => {
  const signUpDto = {
    name: formData.name,
    email: formData.email,
    password: formData.password,
    mobilePhone: formData.mobilePhone,
    landlinePhone: formData.landlinePhone,
    sido: formData.sido,
    sigungu: formData.sigungu,
    profileImageUrl: formData.profileImageUrl || "http://example.com/profile.jpg", // 프로필 이미지 URL 기본값
    // roles: "ROLE_CORPORATIONYET", // 기본 역할 설정
    roles: "ROLE_CORPORATION", // 기본 역할 설정
    isSocial: false,
    socialType: null,
    socialSerialNum: null,
  };

  try {
    const response = await axios.post(`${apiUrl}/users`, { signUpDto }); // 회원가입 POST 요청
    return response.data; // 응답 데이터 반환
  } catch (error) {
    console.error("Failed to sign up:", error);
    throw error;
  }
};

export default { getSido, getSigungu, signUp };
