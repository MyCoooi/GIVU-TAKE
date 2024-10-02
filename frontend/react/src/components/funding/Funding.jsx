import React, { useEffect, useState } from "react";
import Sidebar from "../Sidebar"; // Sidebar import
import "./funding.css"; // 공통 CSS 파일
import { apiSearchFunding } from "../../apis/funding/apiSearchFunding"; // 방금 만든 API 함수 import
import { useNavigate } from "react-router-dom"; // useNavigate import 추가


const Funding = () => {
  const [selectedMenu, setSelectedMenu] = useState("펀딩");
  const navigate = useNavigate(); // useNavigate 훅


  // 펀딩 종류와 상태를 관리하는 state
  const [selectedType, setSelectedType] = useState("D"); // 기본값을 '재난재해'로 설정
  const [selectedStatus, setSelectedStatus] = useState("1"); // 기본값을 '진행 예정'으로 설정

  // API에서 받아온 펀딩 리스트를 저장하는 state
  const [fundingList, setFundingList] = useState([]);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가

  // 페이지네이션 관련 상태
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 6; // 페이지당 표시할 항목 수

  // API 호출 함수
  const fetchFundingList = async (type = "D", state = "0") => {
    try {
      const data = await apiSearchFunding(type, state);
      setFundingList(data); // 받아온 데이터를 state에 저장
    } catch (error) {
      console.error("펀딩 데이터를 가져오는 데 실패했습니다:", error);
    } finally {
      setLoading(false); // 데이터 로딩이 끝났으므로 로딩 상태 false로 설정
    }
  };

  // 페이지 로드 시 초기 데이터 불러오기
  useEffect(() => {
    fetchFundingList("D", "1"); // 초기에는 type "D"과 state "1"을 사용하여 데이터 불러오기
  }, []);

  // 필터 버튼 클릭 시 API 호출을 통해 데이터를 다시 불러오는 함수
  const handleFilterChange = (type, state) => {
    setSelectedType(type);
    setSelectedStatus(state);
    setLoading(true); // 새로운 데이터를 불러올 때 로딩 상태로 변경
    fetchFundingList(type, state);
  };

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  // 현재 페이지에서 보여줄 데이터 계산
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = fundingList.slice(indexOfFirstItem, indexOfLastItem);

  // 총 페이지 수 계산
  const totalPages = Math.max(1, Math.ceil(fundingList.length / itemsPerPage));

  return (
    <div className="funding-page-layout">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <div className="funding-page-content">
        <div className="funding-header">
          <h1>펀딩 관리</h1>
          <button className="register-button">등록</button>
        </div>

        {/* 필터 버튼 */}
        <div className="filter-container">
          <div className="funding-type-buttons">
            <button
              className={`filter-button ${selectedType === "D" ? "active" : ""}`}
              onClick={() => handleFilterChange("D", selectedStatus)}
            >
              재난재해
            </button>
            <button
              className={`filter-button ${selectedType === "R" ? "active" : ""}`}
              onClick={() => handleFilterChange("R", selectedStatus)}
            >
              지역기부
            </button>
          </div>
          <div className="funding-status-buttons">
            <button
              className={`filter-button ${selectedStatus === "0" ? "active" : ""}`}
              onClick={() => handleFilterChange(selectedType, "0")}
            >
              진행예정
            </button>
            <button
              className={`filter-button ${selectedStatus === "1" ? "active" : ""}`}
              onClick={() => handleFilterChange(selectedType, "1")}
            >
              진행중
            </button>
            <button
              className={`filter-button ${selectedStatus === "2" ? "active" : ""}`}
              onClick={() => handleFilterChange(selectedType, "2")}
            >
              완료
            </button>
          </div>
        </div>

        {/* 데이터 로딩 중일 때 표시 */}
        {loading ? (
          <p>로딩 중...</p>
        ) : (
          <div className="funding-grid">
          {currentItems.length > 0 ? (
            currentItems.map((funding) => (
              <div
                key={funding.fundingIdx}
                className="funding-card"
                onClick={() => navigate(`/funding/${funding.fundingIdx}`)} // 클릭 시 상세 페이지로 이동
              >
                <div className="funding-image-placeholder">이미지</div>
                <div className="funding-details">
                  <h2 className="funding-title">{funding.fundingTitle}</h2>
                  <div className="funding-info">
                    <p className="end-date">마감일<br /> {funding.endDate}</p>
                  </div>
                </div>
                <div className="funding-rate">
                  달성률 <br />
                  {funding.goalMoney === 0 || funding.totalMoney === 0
                    ? "0%"
                    : `${Math.round((funding.totalMoney / funding.goalMoney) * 100)}%`}
                </div>
              </div>
            ))
          ) : (
            <p>해당 조건에 맞는 펀딩이 없습니다.</p>
          )}
        </div>
        )}

        {/* 페이지네이션 */}
        <div className="pagination">
          <span onClick={() => handlePageChange(1)}>&lt;&lt;</span>
          {Array.from({ length: totalPages }, (_, index) => (
            <span
              key={index}
              className={currentPage === index + 1 ? "active" : ""}
              onClick={() => handlePageChange(index + 1)}
            >
              {index + 1}
            </span>
          ))}
          <span onClick={() => handlePageChange(totalPages)}>&gt;&gt;</span>
        </div>
      </div>
    </div>
  );
};

export default Funding;
