import axios from "axios";

const apiUrl = "https://j11e202.p.ssafy.io/api";

// 펀딩 후기 작성 요청을 보내는 함수
export const apiWriteFundingReview = async (fundingIdx, reviewContent, accessToken) => {
  try {
    // API 요청
    const response = await axios.post(
      `${apiUrl}/government-fundings/${fundingIdx}/review`,
      {
        reviewContent, // 후기 내용
      },
      {
        headers: {
          Authorization: `Bearer ${accessToken}`, // 인증 토큰 추가
          "Content-Type": "application/json", // JSON 형식으로 요청
        },
      }
    );

    // 응답 확인
    if (response.data.success) {
      console.log("후기 작성 성공:", response.data.data);
      return response.data.data; // 작성된 후기 반환
    } else {
      throw new Error("후기 작성에 실패했습니다.");
    }
  } catch (error) {
    console.error(
      "후기 작성 실패:",
      error.response ? error.response.data : error.message
    );
    throw error;
  }
};

export default { apiWriteFundingReview };
