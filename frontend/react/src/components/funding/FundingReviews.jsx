import React, { useEffect, useState } from "react";
import { apiSearchFundingReview } from "../../apis/funding/apiSearchFundingReview"; 
import { apiWriteFundingReview } from "../../apis/funding/apiWriteFundingReview"; 
import { apiUpdateFundingReview } from "../../apis/funding/apiUpdateFundingReview"; // 수정 API 추가
import TokenManager from "../../utils/TokenManager"; 
import "./FundingReviews.css";

const FundingReviews = ({ fundingIdx }) => {
  const [review, setReview] = useState("");
  const [loading, setLoading] = useState(true);
  const [hasReview, setHasReview] = useState(false);
  const [isEditing, setIsEditing] = useState(false); 
  const [editReview, setEditReview] = useState("");
  const [isEditingReview, setIsEditingReview] = useState(false); // 후기 수정 중인지 확인하는 상태

  const accessToken = TokenManager.getAccessToken(); 

  useEffect(() => {
    const fetchReview = async () => {
      try {
        const response = await apiSearchFundingReview(fundingIdx);
        if (response.success && response.reviewContent) {
          setReview(response.reviewContent);
          setHasReview(true);
        } else {
          setHasReview(false);
        }
      } catch (error) {
        console.error("후기를 가져오는 데 실패했습니다:", error);
        setHasReview(false);
      } finally {
        setLoading(false);
      }
    };

    fetchReview();
  }, [fundingIdx]);

  const handleSubmitReview = async () => {
    if (editReview.trim() === "") {
      alert("후기를 입력해주세요.");
      return;
    }

    console.log("Submitting review:");
    console.log("fundingIdx:", fundingIdx);
    console.log("reviewContent:", editReview);
    console.log("accessToken:", accessToken);

    try {
      if (isEditingReview) {
        // 후기 수정 모드일 때
        await apiUpdateFundingReview(fundingIdx, editReview, accessToken); // 수정 API 호출
        console.log("후기 수정 성공");
      } else {
        // 후기 작성 모드일 때
        await apiWriteFundingReview(fundingIdx, editReview, accessToken); // 작성 API 호출
        console.log("후기 작성 성공");
      }

      setReview(editReview); // 수정된 내용을 반영
      setIsEditing(false); 
      setIsEditingReview(false); // 후기 수정 모드 종료
      setHasReview(true); 
    } catch (error) {
      console.error("후기 작성 또는 수정에 실패했습니다:", error);
    }
  };

  const handleReviewButtonClick = () => {
    if (hasReview) {
      setEditReview(review); 
      setIsEditingReview(true); // 수정 모드로 전환
    } else {
      setEditReview(""); 
      setIsEditingReview(false); // 새로운 작성 모드로 전환
    }
    setIsEditing(true); 
  };

  if (loading) {
    return <p>로딩 중...</p>;
  }

  return (
    <div className="funding-reviews">
      <div className="funding-reviews-header">
        <h2>펀딩 후기</h2>
      </div>
      {!isEditing && (
        <div className="write-review-button-container">
          <button className="write-review-button" onClick={handleReviewButtonClick}>
            {hasReview ? "후기 수정" : "후기 작성"}
          </button>
        </div>
      )}

      {isEditing ? (
        <div className="editing-container">
          <textarea
            className="review-textarea"
            value={editReview}
            onChange={(e) => setEditReview(e.target.value)}
            placeholder="후기를 작성하세요"
          />
          <div className="review-buttons">
            <button className="submit-review-button" onClick={handleSubmitReview}>
              {isEditingReview ? "수정 완료" : "작성 완료"} {/* 버튼 텍스트 변경 */}
            </button>
            <button className="cancel-review-button" onClick={() => setIsEditing(false)}>
              취소
            </button>
          </div>
        </div>
      ) : (
        <div>
          {hasReview ? <p>{review}</p> : <p>아직 작성된 후기가 없습니다.</p>}
        </div>
      )}
    </div>
  );
};

export default FundingReviews;
