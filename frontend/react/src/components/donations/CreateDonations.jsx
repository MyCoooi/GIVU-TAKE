import React, { useState } from "react";
import Sidebar from "../Sidebar"; // Sidebar component import
import "./CreateDonations.css"; // CSS import

const CreateDonations = () => {
  const [donationName, setDonationName] = useState("");
  const [category, setCategory] = useState("");
  const [price, setPrice] = useState("");
  const [description, setDescription] = useState("");
  const [selectedMenu, setSelectedMenu] = useState("기부품");

  const [thumbnail, setThumbnail] = useState(null); // 이미지 상태 추가

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
    // Save logic here
    console.log({
      donationName,
      category,
      price,
      description,
      thumbnail, // 썸네일 이미지 포함
    });
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
                src={thumbnail}
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
              <option value="category1">카테고리1</option>
              <option value="category2">카테고리2</option>
              <option value="category3">카테고리3</option>
              <option value="category4">카테고리4</option>
              <option value="category5">카테고리5</option>
            </select>

            <input
              type="text"
              placeholder="가격"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
              className="donations-input-unique"
            />
            <div className="donations-image-upload">
              <label htmlFor="thumbnail" className="donations-custom-file-upload">
                이미지 선택
              </label>
              <input type="file" id="thumbnail" accept="image/*" onChange={handleImageChange} />
            </div>

          </div>
        </div>

        <div className="donations-description-section-unique">
          <textarea
            placeholder="설명 (스크롤)"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="donations-description-textarea-unique"
          />
        </div>
      </div>
    </div>
  );
};

export default CreateDonations;
