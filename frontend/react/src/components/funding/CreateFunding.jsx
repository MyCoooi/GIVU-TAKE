import React, { useState } from "react";
import Sidebar from "../Sidebar"; // Sidebar component 가져오기
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css"; // Datepicker 스타일 추가
import TokenManager from "../../utils/TokenManager"; // TokenManager 가져오기
import { apiCreateFunding } from "../../apis/funding/apiCreateFunding"; // 펀딩 등록 API 가져오기
import Swal from "sweetalert2"; // SweetAlert2 import
import { useNavigate } from "react-router-dom"; // 페이지 이동을 위한 useNavigate
import "./CreateFunding.css";

const CreateFunding = () => {
  const [fundingName, setFundingName] = useState("");
  const [goalAmount, setGoalAmount] = useState("");
  const [description, setDescription] = useState("");
  const [selectedMenu, setSelectedMenu] = useState("펀딩");

  // 펀딩 유형을 관리하는 state
  const [fundingType, setFundingType] = useState("D"); // 기본값: 재난재해 (D)

  // 시작일과 마감일을 위한 state
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);

  // 썸네일 이미지 선택을 위한 state
  const [thumbnail, setThumbnail] = useState(null);

  const navigate = useNavigate(); // 페이지 이동을 위한 useNavigate 훅

  // 금액 형식화 함수 (천 단위로 쉼표 추가)
  const formatNumber = (value) => {
    const number = value.replace(/[^0-9]/g, ""); // 숫자 외 문자는 모두 제거
    return new Intl.NumberFormat().format(number); // 쉼표를 추가하여 포맷
  };

  // 금액 입력 핸들러
  const handleGoalAmountChange = (e) => {
    const formattedValue = formatNumber(e.target.value);
    setGoalAmount(formattedValue);
  };

  // 이미지 선택 핸들러
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setThumbnail(reader.result); // 선택한 이미지 파일을 미리보기로 표시
      };
      reader.readAsDataURL(file);
    }
  };

  // 한국 시간 기준으로 날짜를 맞추는 함수
  const setKoreanTime = (date) => {
    if (!date) return null;
    const adjustedDate = new Date(date);
    adjustedDate.setHours(9, 0, 0, 0); // 한국 시간은 UTC+9, 시간을 9시로 설정해 보정
    return adjustedDate.toISOString().split("T")[0]; // 날짜 형식으로 변환 (yyyy-MM-dd)
  };

  // 등록 버튼 클릭 핸들러
  const handleSave = async () => {
    const accessToken = TokenManager.getAccessToken(); // TokenManager에서 AccessToken 가져오기

    const fundingData = {
      fundingTitle: fundingName,
      fundingContent: description,
      goalMoney: parseInt(goalAmount.replace(/,/g, "")), // 쉼표 제거 후 숫자로 변환
      startDate: setKoreanTime(startDate), // 시작일을 한국 시간으로 맞춰 변환
      endDate: setKoreanTime(endDate), // 마감일을 한국 시간으로 맞춰 변환
      fundingThumbnail: thumbnail, // 썸네일 이미지를 포함
      fundingType: fundingType, // 사용자가 선택한 펀딩 유형을 포함
    };

    try {
      // API 호출
      const result = await apiCreateFunding(fundingData, accessToken);
      console.log("펀딩 등록 성공:", result);

      // 성공 알림 및 페이지 이동
      Swal.fire({
        title: "펀딩 등록 성공",
        text: "펀딩이 성공적으로 등록되었습니다.",
        icon: "success",
        confirmButtonText: "확인",
      }).then(() => {
        // 확인 버튼을 누르면 펀딩 페이지로 이동
        navigate("/funding");
      });
    } catch (error) {
      console.error("펀딩 등록 중 오류 발생:", error);

      // ES0004 에러코드에 대한 처리
      if (error.response && error.response.data.code === "ES0004") {
        Swal.fire({
          title: "펀딩 등록 실패",
          text: "모금 시작일은 현재 날짜 이후여야 합니다.",
          icon: "error",
          confirmButtonText: "확인",
        });
      } else {
        Swal.fire({
          title: "오류",
          text: "펀딩 등록에 실패했습니다.",
          icon: "error",
          confirmButtonText: "확인",
        });
      }
    }
  };

  return (
    <div className="create-funding-layout">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />

      <div className="create-funding-content">
        <div className="header">
          <h1>펀딩 등록</h1>
          <button className="save-button" onClick={handleSave}>
            등록
          </button>
        </div>

        {/* 펀딩 세부 정보 */}
        <div className="funding-details-container">
          <div className="thumbnail">
            {/* 썸네일 미리보기 */}
            {thumbnail ? (
              <img src={thumbnail} alt="썸네일 미리보기" className="thumbnail-preview" />
            ) : (
              <div className="thumbnail-placeholder">썸네일</div>
            )}
          </div>

          <div className="details-section">
            <input
              type="text"
              placeholder="펀딩명"
              className="funding-name"
              value={fundingName}
              onChange={(e) => setFundingName(e.target.value)}
            />
            <div className="funding-type">
              <label>
                <input
                  type="radio"
                  name="fundingType"
                  value="D" // 재난재해를 "D"로 설정
                  checked={fundingType === "D"} // 상태에 따라 체크 상태 관리
                  onChange={(e) => setFundingType(e.target.value)} // 선택된 값을 상태에 저장
                />{" "}
                재난재해
              </label>
              <label>
                <input
                  type="radio"
                  name="fundingType"
                  value="R" // 지역기부를 "R"로 설정
                  checked={fundingType === "R"} // 상태에 따라 체크 상태 관리
                  onChange={(e) => setFundingType(e.target.value)} // 선택된 값을 상태에 저장
                />{" "}
                지역기부
              </label>
            </div>

            {/* 시작일과 마감일 선택 */}
            <div className="dates">
              <div className="date-picker">
                <label>시작일:</label>
                <DatePicker
                  selected={startDate}
                  onChange={(date) => setStartDate(date)}
                  dateFormat="yyyy-MM-dd"
                  placeholderText="날짜를 선택하세요"
                />
              </div>

              <div className="date-picker">
                <label>마감일:</label>
                <DatePicker
                  selected={endDate}
                  onChange={(date) => setEndDate(date)}
                  dateFormat="yyyy-MM-dd"
                  placeholderText="날짜를 선택하세요"
                />
              </div>
            </div>

            {/* 목표 금액 입력 필드 */}
            <div className="goal-amount">
              <input
                type="text"
                placeholder="목표 금액"
                value={goalAmount}
                onChange={handleGoalAmountChange} // 금액 입력 핸들러
              />
            </div>

            {/* 이미지 선택 버튼 */}
            <div className="image-upload">
              <label htmlFor="thumbnail" className="custom-file-upload">
                이미지 선택
              </label>
              <input type="file" id="thumbnail" accept="image/*" onChange={handleImageChange} />
            </div>
          </div>
        </div>

        {/* 설명 섹션 */}
        <div className="description-section">
          <textarea
            placeholder="설명을 입력해주세요"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </div>
      </div>
    </div>
  );
};

export default CreateFunding;
