import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom"; // useNavigate와 useParams import
import Sidebar from "../Sidebar"; // Sidebar import
import "./DonationsDetail.css"; // CSS import
import Swal from "sweetalert2"; // SweetAlert2 for alerts
import { apiDonationsDetail } from "../../apis/donations/apiDonationsDetail"; // API 함수 import
import { apiDonationsSales } from "../../apis/donations/apiDonationsSales"; // 판매량 조회 API 함수 import

const DonationsDetail = () => {
  const navigate = useNavigate();
  const { giftIdx } = useParams(); // URL에서 giftIdx 가져오기
  const [selectedMenu, setSelectedMenu] = useState("기부품");
  const [activeTab, setActiveTab] = useState("소개"); // Active tab state
  const [donation, setDonation] = useState(null); // 기부품 데이터 상태
  const [salesCount, setSalesCount] = useState(0); // 판매량 상태
  const [loading, setLoading] = useState(true); // 로딩 상태

  useEffect(() => {
    const fetchDonationDetail = async () => {
      try {
        const data = await apiDonationsDetail(giftIdx); // giftIdx 사용
        setDonation(data);
        const salesData = await apiDonationsSales(giftIdx); // 여기서 giftIdx를 사용하여 판매량 조회
        setSalesCount(salesData); // 판매량 설정
      } catch (error) {
        console.error("기부품 상세 정보를 가져오는 데 실패했습니다:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchDonationDetail();
  }, [giftIdx]); // giftIdx가 변경될 때마다 API 요청

  // Delete button handler
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
        // 삭제 로직 추가 필요
        Swal.fire(
          '삭제되었습니다!',
          '기부품이 성공적으로 삭제되었습니다.',
          'success'
        );
        // 삭제 후 기부품 목록 페이지로 이동
        navigate("/donations");
      }
    });
  };

  const renderContent = () => {
    if (loading) {
      return <p>로딩 중...</p>;
    }

    if (!donation) {
      return <p>데이터를 불러오는 데 실패했습니다.</p>;
    }

    switch (activeTab) {
      case "소개":
        return (
          <div className="donations-description-section">
            <p className="donations-description">
              {donation.giftContent}
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

  if (loading) {
    return <p>로딩 중...</p>;
  }

  if (!donation) {
    return <p>데이터를 불러오는 데 실패했습니다.</p>;
  }

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
            <button className="donations-edit-button" onClick={() => navigate(`/donations/edit/${donation.id}`)}>
              수정
            </button>
            <button className="donations-delete-button" onClick={handleDelete}>
              삭제
            </button>
          </div>
        </div>

        <div className="donations-detail-body">
          <div className="donations-thumbnail-section">
            {donation.giftThumbnail ? (
              <img
                src={donation.giftThumbnail}
                alt="기부품 썸네일"
                className="donations-thumbnail"
              />
            ) : (
              <div className="donations-thumbnail-placeholder">썸네일</div>
            )}
          </div>

          <div className="donations-info-section">
            <h2 className="donations-title">{donation.giftName}</h2>
            <p className="donations-category">카테고리: {donation.categoryName}</p>
            <p className="donations-price">가격: {donation.price.toLocaleString()}원</p>
            <p className="donations-stock">판매량: {salesCount}개</p> {/* 판매량을 여기서 표시 */}
            <p className="donations-corporationName">판매자: {donation.corporationName}</p>
            <p className="donations-date">등록일: {new Date(donation.createdDate).toLocaleDateString()}</p>
          </div>
        </div>

        {/* Render content based on active tab */}
        {renderContent()}
      </div>
    </div>
  );
};

export default DonationsDetail;
