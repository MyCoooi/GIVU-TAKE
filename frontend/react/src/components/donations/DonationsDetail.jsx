import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // useNavigate for navigation
import Sidebar from "../Sidebar"; // Sidebar import
import "./DonationsDetail.css"; // CSS import
import Swal from "sweetalert2"; // SweetAlert2 for alerts

const DonationsDetail = () => {
  const navigate = useNavigate();
  const [selectedMenu, setSelectedMenu] = useState("기부품");
  const [activeTab, setActiveTab] = useState("소개"); // Active tab state

  // Delete button handler (you can add your logic here)
  const handleDelete = () => {
    Swal.fire({
      title: '정말 삭제하시겠습니까?',
      text: "이 작업은 되돌릴 수 없습니다.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: '삭제',
      cancelButtonText: '취소'
    }).then((result) => {
      if (result.isConfirmed) {
        // Add your delete logic here
        Swal.fire(
          '삭제되었습니다!',
          '기부품이 성공적으로 삭제되었습니다.',
          'success'
        );
        // After successful deletion, navigate to the donations list page
        navigate("/donations");
      }
    });
  };

  const renderContent = () => {
    switch (activeTab) {
      case "소개":
        return (
          <div className="donations-description-section">
            <p className="donations-description">
              설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명(스크롤)
            </p>
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
            <button
              className={activeTab === "소개" ? "active" : ""}
              onClick={() => setActiveTab("소개")}
            >
              소개
            </button>
            <button
              className={activeTab === "후기" ? "active" : ""}
              onClick={() => setActiveTab("후기")}
            >
              후기
            </button>
          </div>
          <div className="donations-button-group">
            <button className="donations-edit-button" onClick={() => navigate("/donations/edit")}>
              수정
            </button>
            <button className="donations-delete-button" onClick={handleDelete}>
              삭제
            </button>
          </div>
        </div>

        <div className="donations-detail-body">
          <div className="donations-thumbnail-section">
            <div className="donations-thumbnail-placeholder">썸네일</div>
          </div>

          <div className="donations-info-section">
            <h2 className="donations-title">상품명상품명상품명상품명</h2>
            <p className="donations-category">카테고리</p>
            <p className="donations-price">가격가격가격</p>
            <p className="donations-stock">판매량 : 000개</p>
            <p className="donations-date">등록일</p>
          </div>
        </div>

        {/* Render content based on active tab */}
        {renderContent()}
      </div>
    </div>
  );
};

export default DonationsDetail;
