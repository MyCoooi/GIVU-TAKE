import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Sidebar from "../Sidebar";
import { apiFundingDetail } from "../../apis/funding/apiFundingDetail"; 
import FundingReviews from "./FundingReviews";
import FundingComments from "./FundingComments";
import "./FundingDetail.css";

const FundingDetail = () => {
  const { fundingIdx } = useParams();
  const [funding, setFunding] = useState(null);
  const [loading, setLoading] = useState(true);
  const [selectedMenu, setSelectedMenu] = useState("펀딩");
  const [activeTab, setActiveTab] = useState("소개"); // 선택된 탭을 관리하는 state 추가

  useEffect(() => {
    const fetchFundingDetail = async () => {
      try {
        const data = await apiFundingDetail(fundingIdx);
        setFunding(data);
      } catch (error) {
        console.error("펀딩 상세 정보를 가져오는 데 실패했습니다:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchFundingDetail();
  }, [fundingIdx]);

  if (loading) {
    return <p>로딩 중...</p>;
  }

  // 탭별 내용을 렌더링하는 함수
  const renderContent = () => {
    switch (activeTab) {
      case "소개":
        return (
          <div className="funding-description">
            <h2>펀딩 소개</h2>
            <p>{funding.fundingContent}</p>
          </div>
        );
      case "응원댓글":
        return <FundingComments fundingIdx={fundingIdx} />
      case "후기":
        return (
          <div>
            <FundingReviews fundingIdx={fundingIdx} />
          </div>
        );
      default:
        return null;
    }
  };

  return (
    <div className="funding-detail-layout">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />

      <div className="funding-detail-content">
        {/* 펀딩 상세 제목 추가 */}
        <h1 className="funding-detail-title">펀딩 상세</h1>

        {/* 탭 UI는 상단에 유지 */}
        <div className="funding-detail-header">
          <div className="funding-tabs">
            <button
              className={activeTab === "소개" ? "active" : ""}
              onClick={() => setActiveTab("소개")}
            >
              소개
            </button>
            <button
              className={activeTab === "응원댓글" ? "active" : ""}
              onClick={() => setActiveTab("응원댓글")}
            >
              응원댓글
            </button>
            <button
              className={activeTab === "후기" ? "active" : ""}
              onClick={() => setActiveTab("후기")}
            >
              후기
            </button>
          </div>
          {/* 수정 버튼은 '소개' 탭에서만 표시 */}
          {activeTab === "소개" && <button className="edit-button">수정</button>}
        </div>

        {/* 응원댓글 클릭 시 펀딩 상세 정보 숨기기 */}
        {activeTab === "소개" && (
          <div className="funding-detail-body">
            <div className="funding-thumbnail">
              {funding.thumbnail ? (
                <img src={funding.thumbnail} alt="펀딩 썸네일" />
              ) : (
                <div className="thumbnail-placeholder">펀딩 썸네일</div>
              )}
            </div>
            <div className="funding-info">
              <h2>{funding.fundingTitle}</h2>
              <p>펀딩 유형: {funding.fundingType === "D" ? "재난재해" : "지역기부"}</p>
              <p>펀딩 기간: {funding.startDate} ~ {funding.endDate}</p>
              <p>달성 금액: {funding.totalMoney.toLocaleString()}원</p>
              <p>목표 금액: {funding.goalMoney.toLocaleString()}원</p>
              <p className="achievement-rate">달성률: {Math.round((funding.totalMoney / funding.goalMoney) * 100)}%</p>
            </div>
            <p className="registration-date">등록일: {funding.startDate}</p>
          </div>
        )}

        {/* 현재 선택된 탭에 따라 다른 내용 렌더링 */}
        {renderContent()}
      </div>
    </div>
  );
};

export default FundingDetail;
