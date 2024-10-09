import React, { useEffect, useState } from "react";
import Sidebar from "../Sidebar";
import "./Funding.css";
import { apiSearchFunding } from "../../apis/funding/apiSearchFunding";
import { useNavigate, useLocation } from "react-router-dom"; // useLocation import 추가

const Funding = () => {
  const [selectedMenu, setSelectedMenu] = useState("펀딩");
  const navigate = useNavigate();
  const location = useLocation(); // useLocation 사용하여 이전 상태 받기

  // 펀딩 종류와 상태를 관리하는 state
  const [selectedType, setSelectedType] = useState(
    new URLSearchParams(location.search).get("type") || "all"
  ); // URL에서 type 상태 불러오기
  const [selectedStatus, setSelectedStatus] = useState(
    new URLSearchParams(location.search).get("status") || "all"
  ); // URL에서 status 상태 불러오기

  const [fundingList, setFundingList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 6;

  const fetchFundingList = async (type, state) => {
    try {
      let data = [];
      if (type === "all" && state === "all") {
        const disasterData0 = await apiSearchFunding("D", "0");
        const disasterData1 = await apiSearchFunding("D", "1");
        const disasterData2 = await apiSearchFunding("D", "2");
        const regionalData0 = await apiSearchFunding("R", "0");
        const regionalData1 = await apiSearchFunding("R", "1");
        const regionalData2 = await apiSearchFunding("R", "2");

        data = [
          ...disasterData0,
          ...disasterData1,
          ...disasterData2,
          ...regionalData0,
          ...regionalData1,
          ...regionalData2,
        ];
      } else if (state === "all") {
        const data0 = await apiSearchFunding(type, "0");
        const data1 = await apiSearchFunding(type, "1");
        const data2 = await apiSearchFunding(type, "2");

        data = [...data0, ...data1, ...data2];
      } else if (type === "all") {
        const disasterData = await apiSearchFunding("D", state);
        const regionalData = await apiSearchFunding("R", state);

        data = [...disasterData, ...regionalData];
      } else {
        data = await apiSearchFunding(type, state);
      }
      setFundingList(data);
    } catch (error) {
      console.error("펀딩 데이터를 가져오는 데 실패했습니다:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchFundingList(selectedType, selectedStatus); // 선택된 필터로 데이터 불러오기
  }, [selectedType, selectedStatus]);

  const handleFilterChange = (type, state) => {
    setSelectedType(type);
    setSelectedStatus(state);
    setLoading(true);

    // URL에 상태를 반영하여 저장
    navigate(`/funding?type=${type}&status=${state}`);
    fetchFundingList(type, state);
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = fundingList.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.max(1, Math.ceil(fundingList.length / itemsPerPage));

  return (
    <div className="funding-page-layout">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <div className="funding-page-content">
        <div className="funding-header">
          <h1>펀딩 관리</h1>
          <button className="register-button" onClick={() => navigate("/funding/create-funding")}>
            등록
          </button>
        </div>

        <div className="filter-container">
          <div className="funding-type-buttons">
            <button
              className={`filter-button ${selectedType === "all" ? "active" : ""}`}
              onClick={() => handleFilterChange("all", selectedStatus)}
            >
              전체
            </button>
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
              className={`filter-button ${selectedStatus === "all" ? "active" : ""}`}
              onClick={() => handleFilterChange(selectedType, "all")}
            >
              전체
            </button>
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

        {loading ? (
          <p>로딩 중...</p>
        ) : (
          <div className="funding-grid">
            {currentItems.length > 0 ? (
              currentItems.map((funding) => (
                <div
                  key={funding.fundingIdx}
                  className="funding-card"
                  onClick={() =>
                    navigate(`/funding/${funding.fundingIdx}`, {
                      state: { type: selectedType, status: selectedStatus },
                    })
                  }
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
