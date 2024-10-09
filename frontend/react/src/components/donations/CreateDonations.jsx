import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom"; // 페이지 이동을 위한 useNavigate import
import Sidebar from "../Sidebar"; // Sidebar component import
import "./CreateDonations.css"; // CSS import
import { apiCreateDonations } from "../../apis/donations/apiCreateDonations"; // API 파일 import
import TokenManager from "../../utils/TokenManager"; // TokenManager import
import Swal from "sweetalert2"; // SweetAlert2 for alerts

const CreateDonations = () => {
  const [donationName, setDonationName] = useState("");
  const [category, setCategory] = useState("");
  const [price, setPrice] = useState("");
  const [description, setDescription] = useState(""); // 설명 텍스트 상태
  const [thumbnail, setThumbnail] = useState(null); // 대표 이미지 상태 추가
  const [contentImage, setContentImage] = useState(null); // 내용 이미지 상태 추가
  const [selectedMenu, setSelectedMenu] = useState("기부품");
  const navigate = useNavigate(); // 페이지 이동을 위한 useNavigate 훅
  const descriptionRef = useRef(null); // 텍스트 상태를 저장할 ref

  // 대표 이미지 선택 핸들러
  const handleThumbnailChange = (e) => {
    setThumbnail(e.target.files[0]); // 선택한 파일을 상태에 저장
  };

  // 내용 이미지 선택 핸들러
  const handleContentImageChange = (e) => {
    setContentImage(e.target.files[0]); // 선택한 파일을 상태에 저장
  };

  // 숫자 포맷 함수: 입력된 숫자를 천 단위로 쉼표를 추가하는 함수
  const formatPrice = (value) => {
    const number = value.replace(/[^0-9]/g, ""); // 숫자 외의 문자는 모두 제거
    return new Intl.NumberFormat().format(number); // 천 단위 쉼표 추가
  };

  // 가격 입력 핸들러
  const handlePriceChange = (e) => {
    const formattedValue = formatPrice(e.target.value);
    setPrice(formattedValue); // 쉼표가 추가된 값을 상태에 저장
  };

  // 등록 버튼 클릭 핸들러
  const handleSave = async () => {
    const accessToken = TokenManager.getAccessToken(); // TokenManager에서 accessToken 가져오기

    const donationData = {
      giftName: donationName,
      categoryIdx: parseInt(category.replace("category", "")), // category가 예를 들어 'category1'이므로 숫자로 변환
      giftContent: description,
      price: parseInt(price.replace(/,/g, "")), // 쉼표 제거 후 숫자로 변환
    };

    try {
      // API 호출
      const result = await apiCreateDonations(donationData, thumbnail, contentImage, accessToken);
      console.log("기부품 등록 성공:", result);

      // 성공 알림 및 페이지 이동
      Swal.fire({
        title: "등록 성공",
        text: "기부품이 성공적으로 등록되었습니다.",
        icon: "success",
        confirmButtonText: "확인",
      }).then(() => {
        navigate("/donations"); // 확인 버튼 클릭 시 /donations로 이동
      });
    } catch (error) {
      console.error("기부품 등록 실패:", error);

      // 오류 알림
      Swal.fire({
        title: "등록 실패",
        text: "기부품 등록 중 오류가 발생했습니다. 다시 시도해주세요.",
        icon: "error",
        confirmButtonText: "확인",
      });
    }
  };

  return (
    <div className="create-donations-layout-unique">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />

      <div className="create-donations-content-unique">
        <div className="donations-header-unique">
          <h1>기부품 등록</h1>
          <button className="donations-save-button-unique" onClick={handleSave}>
            저장
          </button>
        </div>

        <div className="donations-details-unique">
          <div className="donations-thumbnail-section-unique">
            {thumbnail ? (
              <img
                src={URL.createObjectURL(thumbnail)}
                alt="썸네일 미리보기"
                className="donations-thumbnail-unique"
              />
            ) : (
              <div className="donations-thumbnail-placeholder-unique">썸네일</div>
            )}
          </div>

          <div className="donations-details-section-unique">
            <input
              type="text"
              placeholder="상품명"
              value={donationName}
              onChange={(e) => setDonationName(e.target.value)}
              className="donations-input-unique"
            />
            <select
              value={category}
              onChange={(e) => setCategory(e.target.value)}
              className="donations-input-unique category-select-unique"
            >
              <option value="" disabled>
                카테고리
              </option>
              <option value="category1">지역상품권</option>
              <option value="category2">농축산물</option>
              <option value="category3">수산물</option>
              <option value="category4">가공식품</option>
              <option value="category5">공예품</option>
            </select>

            <input
              type="text"
              placeholder="가격"
              value={price}
              onChange={handlePriceChange} // 가격 입력 핸들러
              className="donations-input-unique"
            />

            <div className="donations-image-upload">
              {/* 대표 이미지 업로드 */}
              <label htmlFor="thumbnail" className="donations-custom-file-upload">
                대표 이미지
              </label>
              <input type="file" id="thumbnail" accept="image/*" onChange={handleThumbnailChange} />
              
              {/* 내용 이미지 업로드 */}
              <label htmlFor="contentImage" className="donations-content-file-upload">
                내용 이미지
              </label>
              <input type="file" id="contentImage" accept="image/*" onChange={handleContentImageChange} />
            </div>
          </div>
        </div>

        <div className="donations-description-section-unique">
          <textarea
            placeholder="설명을 입력하세요"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="donations-description-textarea-unique"
            ref={descriptionRef}
          />

          {/* 내용 이미지 미리보기 섹션 */}
          {contentImage && (
            <div className="donations-content-image-section-unique">
              <img
                src={URL.createObjectURL(contentImage)}
                alt="내용 이미지 미리보기"
                className="donations-content-image-inside"
              />
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CreateDonations;
