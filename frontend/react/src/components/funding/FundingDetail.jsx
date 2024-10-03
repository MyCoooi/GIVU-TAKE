import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom"; // useNavigate 추가
import Sidebar from "../Sidebar";
import { apiFundingDetail } from "../../apis/funding/apiFundingDetail"; 
import { apiUpdateFunding } from "../../apis/funding/apiUpdateFunding"; // Update API import
import { apiDeleteFunding } from "../../apis/funding/apiDeleteFunding"; // Delete API import
import FundingReviews from "./FundingReviews";
import FundingComments from "./FundingComments";
import "./FundingDetail.css";
import TokenManager from "../../utils/TokenManager"; // TokenManager import
import Swal from 'sweetalert2'; // SweetAlert2 import


const FundingDetail = () => {
  const { fundingIdx } = useParams();
  const navigate = useNavigate(); // useNavigate for redirecting after deletion
  const [funding, setFunding] = useState(null);
  const [loading, setLoading] = useState(true);
  const [selectedMenu, setSelectedMenu] = useState("펀딩");
  const [activeTab, setActiveTab] = useState("소개"); // 선택된 탭을 관리하는 state 추가
  const [isEditing, setIsEditing] = useState(false); // 수정 모드 여부를 관리하는 state
  const [updatedFunding, setUpdatedFunding] = useState({
    fundingTitle: "",
    fundingContent: "",
    goalMoney: "",
    startDate: "",
    endDate: "",
    fundingType: "", // 펀딩 유형 추가
  });

  useEffect(() => {
    const fetchFundingDetail = async () => {
      try {
        const data = await apiFundingDetail(fundingIdx);
        setFunding(data);
        setUpdatedFunding({
          fundingTitle: data.fundingTitle,
          fundingContent: data.fundingContent,
          goalMoney: formatNumberWithCommas(data.goalMoney.toString()), // 쉼표가 적용된 목표 금액
          startDate: data.startDate,
          endDate: data.endDate,
          fundingType: data.fundingType
        });
      } catch (error) {
        console.error("펀딩 상세 정보를 가져오는 데 실패했습니다:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchFundingDetail();
  }, [fundingIdx]);

  const handleEditClick = () => {
    setIsEditing(true); // 수정 모드 활성화
  };

  const handleCancelClick = () => {
    setIsEditing(false); // 수정 모드 비활성화
  };

  const handleDeleteClick = async () => {
    Swal.fire({
      title: '정말 삭제하시겠습니까?',
      text: "이 작업은 되돌릴 수 없습니다.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: '삭제',
      cancelButtonText: '취소'
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          const accessToken = TokenManager.getAccessToken(); // AccessToken from TokenManager
          await apiDeleteFunding(fundingIdx, accessToken); // API 호출하여 삭제 요청

          Swal.fire(
            '삭제되었습니다!',
            '펀딩이 성공적으로 삭제되었습니다.',
            'success'
          ).then(() => {
            navigate('/funding'); // 삭제 후 펀딩 리스트로 리디렉션
          });
        } catch (error) {
          Swal.fire('삭제 실패', '펀딩 삭제 중 오류가 발생했습니다.', 'error');
        }
      }
    });
  };

  // 숫자에 쉼표를 추가하는 함수
  const formatNumberWithCommas = (number) => {
    return number.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  };

  const removeCommas = (number) => {
    return number.replace(/,/g, "");
  };

  const handleSaveClick = async () => {
    try {
      const accessToken = TokenManager.getAccessToken(); // AccessToken from TokenManager
      const updatedData = {
        ...updatedFunding,
        goalMoney: parseInt(removeCommas(updatedFunding.goalMoney)), // 쉼표 제거 후 숫자로 변환
      };

      await apiUpdateFunding(fundingIdx, updatedData, accessToken); // API 호출하여 수정 내용 저장
      Swal.fire({
        title: "펀딩이 수정되었습니다.",
        icon: "success",
        confirmButtonText: "확인",
      }).then(() => {
        window.location.reload(); // 확인 버튼 누르면 새로고침
      });
      
    } catch (error) {
      console.error("펀딩 수정 중 오류 발생:", error);
      Swal.fire({
        title: "펀딩 수정이 실패하였습니다.",
        icon: "error",
        confirmButtonText: "확인",
      });
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    
    if (name === "goalMoney") {
      const numericValue = removeCommas(value);
      setUpdatedFunding((prev) => ({
        ...prev,
        [name]: formatNumberWithCommas(numericValue),
      }));
    } else {
      setUpdatedFunding((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
  };
  
  const handleTypeChange = (e) => {
    setUpdatedFunding((prev) => ({
      ...prev,
      fundingType: e.target.value, // 펀딩 유형 수정
    }));
  };

  if (loading) {
    return <p>로딩 중...</p>;
  }

  const renderContent = () => {
    switch (activeTab) {
      case "소개":
        return (
          <div className="funding-description">
            <h2>펀딩 소개</h2>
            {isEditing ? (
              <textarea
                name="fundingContent"
                value={updatedFunding.fundingContent}
                onChange={handleChange}
                className="edit-textarea"
              />
            ) : (
              <p>{funding.fundingContent}</p>
            )}
          </div>
        );
      case "응원댓글":
        return <FundingComments fundingIdx={fundingIdx} />;
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
        <h1 className="funding-detail-title">펀딩 상세</h1>

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

          {/* 수정 및 삭제 버튼 표시 */}
          {isEditing ? (
            <div className="edit-buttons">
              <button className="save-button" onClick={handleSaveClick}>저장</button>
              <button className="cancel-button" onClick={handleCancelClick}>취소</button>
            </div>
          ) : (
            activeTab === "소개" && (
              <div className="edit-buttons">
                <button className="edit-button" onClick={handleEditClick}>수정</button>
                <button className="delete-button" onClick={handleDeleteClick}>삭제</button>
              </div>
            )
          )}
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

            {/* 모든 input-row를 감싸는 큰 div */}
            <div className="funding-info-wrapper">
              <div className="funding-info">
                {isEditing ? (
                  <>
                    <div className="input-row">
                      <label htmlFor="fundingTitle">펀딩 제목:</label>
                      <input
                        id="fundingTitle"
                        type="text"
                        name="fundingTitle"
                        value={updatedFunding.fundingTitle}
                        onChange={handleChange}
                        className="edit-input"
                      />
                    </div>
                    <div className="input-row">
                      <label>목표 금액:</label>
                      <input
                        type="text"
                        name="goalMoney"
                        value={updatedFunding.goalMoney}
                        onChange={handleChange}
                        className="edit-input"
                      />
                    </div>

                    <div className="input-row">
                      <label>시작일:</label>
                      <input
                        type="date"
                        name="startDate"
                        value={updatedFunding.startDate}
                        onChange={handleChange}
                        className="edit-input"
                      />
                    </div>

                    <div className="input-row">
                      <label>마감일:</label>
                      <input
                        type="date"
                        name="endDate"
                        value={updatedFunding.endDate}
                        onChange={handleChange}
                        className="edit-input"
                      />
                    </div>
                    <div className="input-row">
                      <label>펀딩 유형:</label>
                      <div className="funding-type">
                        <label>
                          <input
                            type="radio"
                            name="fundingType"
                            value="D"
                            checked={updatedFunding.fundingType === "D"} // "재난재해" 선택
                            onChange={handleTypeChange}
                          />
                          재난재해
                        </label>
                        <label>
                          <input
                            type="radio"
                            name="fundingType"
                            value="R"
                            checked={updatedFunding.fundingType === "R"} // "지역기부" 선택
                            onChange={handleTypeChange}
                          />
                          지역기부
                        </label>
                      </div>
                    </div>
                  </>
                ) : (
                  <>
                    <h2>{funding.fundingTitle}</h2>
                    <p>펀딩 유형: {funding.fundingType === "D" ? "재난재해" : "지역기부"}</p>
                    <p>펀딩 기간: {funding.startDate} ~ {funding.endDate}</p>
                    <p>달성 금액: {funding.totalMoney.toLocaleString()}원</p>
                    <p>목표 금액: {funding.goalMoney.toLocaleString()}원</p>
                  </>
                )}
              </div>
              <p className="registration-date">등록일: {funding.startDate}</p>
            </div>
          </div>
        )}

        {/* 현재 선택된 탭에 따라 다른 내용 렌더링 */}
        {renderContent()}
      </div>
    </div>
  );
};

export default FundingDetail;
