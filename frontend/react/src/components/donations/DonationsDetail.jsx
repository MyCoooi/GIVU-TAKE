import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom"; // useNavigate와 useParams import
import Sidebar from "../Sidebar"; // Sidebar import
import "./DonationsDetail.css"; // CSS import
import Swal from "sweetalert2"; // SweetAlert2 for alerts
import { apiDonationsDetail } from "../../apis/donations/apiDonationsDetail"; // API 함수 import
import { apiDonationsSales } from "../../apis/donations/apiDonationsSales"; // 판매량 조회 API 함수 import
import { apiDeleteDonations } from "../../apis/donations/apiDeleteDonations"; // 삭제 API import
import { apiUpdateDonations } from "../../apis/donations/apiUpdateDonations"; // 수정 API import
import { getUserInfo } from "../../apis/auth/apiUserInfo"; // UserInfo 가져오기
import TokenManager from "../../utils/TokenManager"; // TokenManager import

const DonationsDetail = () => {
  const navigate = useNavigate();
  const { giftIdx } = useParams(); // URL에서 giftIdx 가져오기
  const [selectedMenu, setSelectedMenu] = useState("기부품");
  const [activeTab, setActiveTab] = useState("소개"); // Active tab state
  const [donation, setDonation] = useState(null); // 기부품 데이터 상태
  const [salesCount, setSalesCount] = useState(0); // 판매량 상태
  const [isEditing, setIsEditing] = useState(false); // 수정 모드 상태
  const [userInfo, setUserInfo] = useState({
    sido: "",
    sigungu: "",
  }); // 사용자 정보 상태

  const [updatedDonation, setUpdatedDonation] = useState({
    giftName: "",
    price: "",
    giftThumbnail: "",
    giftContent: "",
    categoryName: "", // 카테고리 추가
  });

  const categories = ["지역상품권", "농축산물", "수산물", "가공식품", "공예품"]; // 선택할 수 있는 카테고리 리스트

  useEffect(() => {
    const fetchDonationDetail = async () => {
      try {
        const data = await apiDonationsDetail(giftIdx); // giftIdx 사용
        setDonation(data);
        setUpdatedDonation({
          giftName: data.giftName,
          price: data.price?.toString(),
          giftThumbnail: data.giftThumbnail,
          giftContent: data.giftContent,
          categoryName: data.categoryName, // 카테고리 데이터를 가져옵니다
        });
        const salesData = await apiDonationsSales(giftIdx); // 여기서 giftIdx를 사용하여 판매량 조회
        setSalesCount(salesData); // 판매량 설정
      } catch (error) {
        console.error("기부품 상세 정보를 가져오는 데 실패했습니다:", error);
      }
    };

    const fetchUserInfo = async () => {
      try {
        const userData = await getUserInfo(); // 사용자 정보 가져오기
        setUserInfo({
          sido: userData.sido,
          sigungu: userData.sigungu,
        });
      } catch (error) {
        console.error("사용자 정보를 가져오는 데 실패했습니다:", error);
      }
    };

    fetchDonationDetail();
    fetchUserInfo(); // 사용자 정보 가져오기
  }, [giftIdx]);

  // Edit button handler
  const handleEditClick = () => {
    setIsEditing(true); // 수정 모드 활성화
  };

  // Cancel button handler
  const handleCancelClick = () => {
    setIsEditing(false); // 수정 모드 비활성화
  };

  // Delete button handler
  const handleDelete = () => {
    Swal.fire({
      title: "정말 삭제하시겠습니까?",
      text: "이 작업은 되돌릴 수 없습니다.",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "삭제",
      cancelButtonText: "취소",
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          const accessToken = TokenManager.getAccessToken(); // TokenManager로 accessToken 가져오기
          await apiDeleteDonations(giftIdx, accessToken); // 삭제 API 호출
          Swal.fire("삭제되었습니다!", "기부품이 성공적으로 삭제되었습니다.", "success");
          // 삭제 후 기부품 목록 페이지로 이동
          navigate("/donations");
        } catch (error) {
          Swal.fire("삭제 실패", "기부품을 삭제하는 중 문제가 발생했습니다. 다시 시도해주세요.", "error");
          console.error("기부품 삭제 실패:", error);
        }
      }
    });
  };

  // Save button handler
  const handleSaveClick = async () => {
    try {
      const accessToken = TokenManager.getAccessToken(); // accessToken 가져오기
      const cartegoryIdx = categories.indexOf(updatedDonation.categoryName) + 1; // 선택된 카테고리의 인덱스 계산

      // 수정된 데이터를 백엔드로 전송하여 저장하는 로직
      await apiUpdateDonations(
        giftIdx,
        {
          giftName: updatedDonation.giftName,
          price: parseInt(updatedDonation.price, 10), // 가격을 숫자로 변환
          giftThumbnail: updatedDonation.giftThumbnail,
          giftContent: updatedDonation.giftContent,
          cartegoryIdx: cartegoryIdx, // 'cartegoryIdx'로 수정
        },
        accessToken
      );

      Swal.fire({
        title: "수정되었습니다.",
        text: "기부품 정보가 성공적으로 수정되었습니다.",
        icon: "success",
        confirmButtonText: "확인",
      }).then(() => {
        window.location.reload(); // 새로고침
      });

      setIsEditing(false); // 수정 모드 종료
    } catch (error) {
      Swal.fire({
        title: "수정 실패",
        text: "기부품 정보를 수정하는 중 문제가 발생했습니다.",
        icon: "error",
        confirmButtonText: "확인",
      });
      console.error("기부품 수정 실패:", error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedDonation((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const renderContent = () => {
    switch (activeTab) {
      case "소개":
        return (
          <div className="donations-description-section">
            {isEditing ? (
              <textarea
                name="giftContent"
                value={updatedDonation.giftContent}
                onChange={handleChange}
                className="edit-textarea"
              />
            ) : (
              <p className="donations-description">{donation?.giftContent}</p>
            )}
          </div>
        );
      case "후기":
        return (
          <div className="donations-reviews-section">
            <p>후기 내용이 여기에 표시됩니다.</p>
          </div>
        );
      default:
        return null;
    }
  };

  return (
    <div className="donations-detail-container">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />

      <div className="donations-detail-content">
        <h1 className="donations-detail-title">기부품 상세</h1>

        <div className="donations-detail-header">
          <div className="donations-tabs">
            <button className={activeTab === "소개" ? "active" : ""} onClick={() => setActiveTab("소개")}>
              소개
            </button>
            <button className={activeTab === "후기" ? "active" : ""} onClick={() => setActiveTab("후기")}>
              후기
            </button>
          </div>
          <div className="donations-button-group">
            {isEditing ? (
              <>
                {/* 수정 모드일 때 저장/취소 버튼 */}
                <button className="save-button" onClick={handleSaveClick}>
                  저장
                </button>
                <button className="cancel-button" onClick={handleCancelClick}>
                  취소
                </button>
              </>
            ) : (
              <>
                {/* 수정 모드가 아닐 때 수정/삭제 버튼 */}
                <button className="donations-edit-button" onClick={handleEditClick}>
                  수정
                </button>
                <button className="donations-delete-button" onClick={handleDelete}>
                  삭제
                </button>
              </>
            )}
          </div>
        </div>

        <div className="donations-detail-body">
          <div className="donations-thumbnail-section">
            {/* 썸네일 이미지는 왼쪽에 유지 */}
            {isEditing ? (
              <img
                src={updatedDonation.giftThumbnail} // 수정 모드에서도 기존 이미지를 먼저 보여줍니다
                alt="기부품 썸네일"
                className="donations-thumbnail"
              />
            ) : donation?.giftThumbnail ? (
              <img src={donation.giftThumbnail} alt="기부품 썸네일" className="donations-thumbnail" />
            ) : (
              <div className="donations-thumbnail-placeholder">썸네일</div>
            )}
          </div>

          <div className="donations-info-section">
            {isEditing ? (
              <>
                <div className="input-row">
                  <label htmlFor="giftName">기부품 이름:</label>
                  <input
                    id="giftName"
                    type="text"
                    name="giftName"
                    value={updatedDonation.giftName}
                    onChange={handleChange}
                    className="edit-input"
                  />
                </div>
                <div className="input-row">
                  <label htmlFor="price">가격:</label>
                  <input
                    id="price"
                    type="text"
                    name="price"
                    value={updatedDonation.price}
                    onChange={handleChange}
                    className="edit-input"
                  />
                </div>
                <div className="input-row">
                  <label htmlFor="category">카테고리:</label>
                  <select
                    id="category"
                    name="categoryName"
                    value={updatedDonation.categoryName}
                    onChange={handleChange}
                    className="edit-input"
                  >
                    {categories.map((category) => (
                      <option key={category} value={category}>
                        {category}
                      </option>
                    ))}
                  </select>
                </div>
              </>
            ) : (
              <>
                <h2 className="donations-title">{donation?.giftName}</h2>
                <p className="donations-price">가격: {donation?.price?.toLocaleString()}원</p>
                <p className="donations-category">카테고리: {donation?.categoryName}</p>
              </>
            )}
            <p className="donations-stock">판매량: {salesCount}개</p> {/* 판매량을 여기서 표시 */}
            <p className="donations-corporationName">
              판매자: {userInfo.sido} {userInfo.sigungu} {/* 판매자 정보에 sido와 sigungu 표시 */}
            </p>
            <p className="donations-date">등록일: {new Date(donation?.createdDate).toLocaleDateString()}</p>
          </div>
        </div>

        {/* Render content based on active tab */}
        {renderContent()}
      </div>
    </div>
  );
};

export default DonationsDetail;
