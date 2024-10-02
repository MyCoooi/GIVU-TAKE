import React, { useState } from "react";
import Sidebar from "../Sidebar"; // Sidebar component 가져오기
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css"; // Datepicker 스타일 추가
import "./CreateFunding.css";

const CreateFunding = () => {
  const [fundingName, setFundingName] = useState("");
  const [goalAmount, setGoalAmount] = useState("");
  const [description, setDescription] = useState("");
  const [selectedMenu, setSelectedMenu] = useState("펀딩");

  // 시작일과 마감일을 위한 state
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);

  // 썸네일 이미지 선택을 위한 state
  const [thumbnail, setThumbnail] = useState(null);

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

  const handleSave = () => {
    console.log("등록 클릭:", fundingName, goalAmount, description, startDate, endDate, thumbnail);
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
                <input type="radio" name="fundingType" /> 재난재해
              </label>
              <label>
                <input type="radio" name="fundingType" /> 지역기부
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
