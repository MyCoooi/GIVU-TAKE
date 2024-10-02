import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; // useParams로 fundingIdx를 URL에서 가져옴
import Sidebar from "../Sidebar";
import { apiFundingDetail } from "../../apis/funding/apiFundingDetail"; // API 파일 import
import "./fundingDetail.css"; // 스타일 파일 import

const FundingDetail = () => {
  const { fundingIdx } = useParams(); // URL에서 fundingIdx 가져오기
  const [funding, setFunding] = useState(null);
  const [loading, setLoading] = useState(true);
  const [selectedMenu, setSelectedMenu] = useState("펀딩"); // Sidebar에서 사용할 상태 추가

  useEffect(() => {
    const fetchFundingDetail = async () => {
      try {
        const data = await apiFundingDetail(fundingIdx); // API 요청
        setFunding(data); // 받아온 데이터를 state에 저장
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

  return (
    <div className="funding-detail-layout">
      {/* setSelectedMenu를 Sidebar에 전달 */}
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <div className="funding-detail-content">
        <div className="funding-detail-header">
          <h1>펀딩 상세</h1>
          <div className="funding-tabs">
            <span>소개</span>
            <span>응원댓글</span>
            <span>후기</span>
          </div>
          <button className="edit-button">수정</button>
        </div>

        <div className="funding-detail-body">
          <div className="funding-thumbnail">
            <img src={funding.thumbnail} alt="펀딩 썸네일" />
          </div>
          <div className="funding-info">
            <h2>{funding.fundingTitle}</h2>
            <p>펀딩 유형: {funding.fundingType === "D" ? "재난재해" : "지역기부"}</p>
            <p>
              {funding.startDate} ~ {funding.endDate}
            </p>
            <p>목표 금액: {funding.goalMoney.toLocaleString()}원</p>
            <p>등록일: {funding.startDate}</p>
          </div>
        </div>

        <div className="funding-description">
          <p>{funding.fundingContent}</p>
        </div>
      </div>
    </div>
  );
};

export default FundingDetail;
