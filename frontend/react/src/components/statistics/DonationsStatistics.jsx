import React, { useState, useEffect } from "react";
import Sidebar from "../Sidebar"; // Sidebar import
import "./DonationsStatistics.css"; // 공통 CSS 파일
import { apiMyDonations } from "../../apis/donations/apiMyDonations";
import { getUserInfo } from "../../apis/auth/apiUserInfo"; // getUserInfo import
import { apiDonationsStatistics } from "../../apis/statistics/apiDonationsStatistics";

const DonationsStatistics = () => {
  const [selectedMenu, setSelectedMenu] = useState("통계");
  const [donations, setDonations] = useState([]); // 기부품 목록
  const [selectedDonationIdx, setSelectedDonationIdx] = useState(""); // 선택된 기부품의 idx
  const [donationStatistics, setDonationStatistics] = useState(null); // 통계 데이터
  const [errorMessage, setErrorMessage] = useState(null); // 오류 메시지
  const [corporationEmail, setCorporationEmail] = useState(""); // 사용자 이메일 상태

  // 사용자 정보를 불러오는 함수
  const fetchUserInfo = async () => {
    try {
      const userInfo = await getUserInfo(); // 사용자 정보 API 호출
      setCorporationEmail(userInfo.email); // 이메일 상태에 저장
    } catch (error) {
      console.error("사용자 정보를 가져오는 데 실패했습니다:", error);
    }
  };

  // 기부품 목록을 불러오는 함수
  const fetchDonations = async () => {
    if (!corporationEmail) return; // 이메일이 없으면 호출 중단
    try {
      const donationData = await apiMyDonations(corporationEmail); // 이메일로 기부품 API 호출
      setDonations(donationData); // 가져온 기부품 목록 설정
    } catch (error) {
      console.error("기부품 목록 불러오기 실패:", error);
    }
  };

  // 컴포넌트가 마운트되면 사용자 정보를 가져오고, 기부품 목록을 불러옴
  useEffect(() => {
    fetchUserInfo(); // 사용자 정보 불러오기
  }, []);

  // corporationEmail이 설정되면 기부품 목록을 가져옴
  useEffect(() => {
    if (corporationEmail) {
      fetchDonations();
    }
  }, [corporationEmail]);

  // 드롭다운에서 기부품 선택 시 처리
  const handleDonationChange = async (event) => {
    const donationIdx = event.target.value;
    setSelectedDonationIdx(donationIdx); // 선택된 기부품의 idx 설정

    // 기부품 통계 불러오기
    if (donationIdx) {
      try {
        const statisticsData = await apiDonationsStatistics(donationIdx);
        setDonationStatistics(statisticsData); // 통계 데이터 저장
        setErrorMessage(null); // 오류 메시지 초기화
      } catch (error) {
        console.error("기부품 통계 불러오기 실패:", error);
        setDonationStatistics(null);
        setErrorMessage("기부품 통계를 불러오는 데 실패했습니다.");
      }
    } else {
      setDonationStatistics(null);
    }
  };

  return (
    <div className="donations-statistics-container">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <div className="donations-statistics-content">
        <div className="donations-select-container">
          <h2>기부품을 선택해 주세요.</h2>
          <div className="donations-dropdown">
            <select
              id="donations-select"
              name="donations-select"
              value={selectedDonationIdx}
              onChange={handleDonationChange}
            >
              <option value="">기부품 선택</option>
              {donations.map((donation) => (
                <option key={donation.giftIdx} value={donation.giftIdx}>
                  {donation.giftName}
                </option>
              ))}
            </select>
          </div>

          {/* 통계 데이터가 있을 경우 텍스트로 표시 */}
          {donationStatistics && (
            <div className="donation-statistics-data">
              <h3>기부 통계</h3>
              <p>연간 통계: {JSON.stringify(donationStatistics.giftYearStatistics)}</p>
              <p>성별 비율: {JSON.stringify(donationStatistics.giftPercentageByGender)}</p>
              <p>카테고리 비율: {JSON.stringify(donationStatistics.giftPercentageByCategory)}</p>
              <p>연령대 비율: {JSON.stringify(donationStatistics.giftPercentageByAge)}</p>
              <h4>구매자 목록:</h4>
              <ul>
                {donationStatistics.giftPurchasers.map((purchaser, index) => (
                  <li key={index}>{purchaser.name}: {purchaser.price}원</li>
                ))}
              </ul>
            </div>
          )}

          {/* 오류 메시지 표시 */}
          {errorMessage && <p className="error-message">{errorMessage}</p>}
        </div>
      </div>
    </div>
  );
};

export default DonationsStatistics;
