import React, { useState } from "react";
import Sidebar from "../Sidebar"; // Sidebar import
import "./funding.css"; // 공통 CSS 파일

const Funding = () => {
  const [selectedMenu, setSelectedMenu] = useState("펀딩");

  // 펀딩 종류와 상태를 관리하는 state
  const [selectedType, setSelectedType] = useState("전체");
  const [selectedStatus, setSelectedStatus] = useState("전체");

  // 가짜 데이터
  const fundingList = [
    { id: 1, name: "아이들에게 따뜻한 희망을 전해주세요", type: "재난재해", status: "In Progress", startDate: "2024-01-01", endDate: "2024-12-31", rate: "80%" },
    { id: 2, name: "지역기부 펀딩1", type: "지역기부", status: "Planned", startDate: "2024-02-01", endDate: "2024-11-30", rate: "50%" },
    { id: 3, name: "재난재해 펀딩2", type: "재난재해", status: "Completed", startDate: "2024-03-01", endDate: "2024-10-10", rate: "100%" },
    { id: 4, name: "지역기부 펀딩2", type: "지역기부", status: "In Progress", startDate: "2024-04-01", endDate: "2024-12-15", rate: "60%" },
    { id: 5, name: "재난재해 펀딩3", type: "재난재해", status: "Planned", startDate: "2024-05-01", endDate: "2025-01-05", rate: "0%" },
    { id: 6, name: "지역기부 펀딩3", type: "지역기부", status: "Completed", startDate: "2024-06-01", endDate: "2024-09-25", rate: "100%" },
  ];

  // 필터링된 리스트를 반환하는 함수
  const filteredFundingList = fundingList.filter((funding) => {
    return (
      (selectedType === "전체" || funding.type === selectedType) &&
      (selectedStatus === "전체" || funding.status === selectedStatus)
    );
  });

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
              className={`filter-button ${selectedType === "전체" ? "active" : ""}`}
              onClick={() => setSelectedType("전체")}
            >
              전체
            </button>
            <button
              className={`filter-button ${selectedType === "재난재해" ? "active" : ""}`}
              onClick={() => setSelectedType("재난재해")}
            >
              재난재해
            </button>
            <button
              className={`filter-button ${selectedType === "지역기부" ? "active" : ""}`}
              onClick={() => setSelectedType("지역기부")}
            >
              지역기부
            </button>
          </div>
          <div className="funding-status-buttons">
            <button
              className={`filter-button ${selectedStatus === "전체" ? "active" : ""}`}
              onClick={() => setSelectedStatus("전체")}
            >
              전체
            </button>
            <button
              className={`filter-button ${selectedStatus === "Planned" ? "active" : ""}`}
              onClick={() => setSelectedStatus("Planned")}
            >
              진행예정
            </button>
            <button
              className={`filter-button ${selectedStatus === "In Progress" ? "active" : ""}`}
              onClick={() => setSelectedStatus("In Progress")}
            >
              진행중
            </button>
            <button
              className={`filter-button ${selectedStatus === "Completed" ? "active" : ""}`}
              onClick={() => setSelectedStatus("Completed")}
            >
              완료
            </button>
          </div>
        </div>

        {/* 필터링된 펀딩 카드 목록 */}
        <div className="funding-grid">
          {filteredFundingList.length > 0 ? (
            filteredFundingList.map((funding) => (
              <div key={funding.id} className="funding-card">
                <div className="funding-image-placeholder">이미지</div>
                <div className="funding-details">
                  <h2 className="funding-title">{funding.name}</h2>
                  <div className="funding-info">
                    <p className="end-date">마감일<br></br> {funding.endDate}</p>
                  </div>
                </div>
                <div className={`status-label ${funding.status === "Planned" ? "status-planned" : funding.status === "In Progress" ? "status-in-progress" : "status-completed"}`}>
                  {funding.status === "Planned" ? "진행예정" : funding.status === "In Progress" ? "진행중" : "완료"}
                </div>
                <div className="funding-rate">달성률 <br /> {funding.rate}</div> {/* 줄 바꿈 추가 */}
              </div>
            ))
          ) : (
            <p>해당 조건에 맞는 펀딩이 없습니다.</p>
          )}
        </div>

        {/* 페이지네이션 */}
        <div className="pagination">
          <span>&lt;&lt;</span>
          <span>1</span>
          <span>2</span>
          <span>3</span>
          <span>&gt;&gt;</span>
        </div>
      </div>
    </div>
  );
};

export default Funding;
