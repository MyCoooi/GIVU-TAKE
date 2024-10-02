import React, { useEffect, useState } from "react";
import { apiSearchFundingReview } from "../../apis/funding/apiSearchFundingReview"; // API 함수 import
import { apiWriteFundingReview } from "../../apis/funding/apiWriteFundingReview"; // 후기 작성 API 함수 import
import "./FundingReviews.css";

const FundingReviews = ({ fundingIdx, accessToken }) => {
  const [review, setReview] = useState("");
  const [loading, setLoading] = useState(true);
  const [hasReview, setHasReview] = useState(false);
  const [isEditing, setIsEditing] = useState(false); // 후기 수정 모드인지 확인하는 상태
  const [editReview, setEditReview] = useState(""); // 수정 중인 후기 내용

  // 후기를 가져오는 함수
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

  // 후기 작성 또는 수정 요청 함수
  const handleSubmitReview = async () => {
    try {
      await apiWriteFundingReview(fundingIdx, editReview, accessToken);
      setReview(editReview); // 수정된 내용을 반영
      setIsEditing(false); // 수정 모드 종료
      setHasReview(true); // 후기가 존재한다고 업데이트
    } catch (error) {
      console.error("후기 작성 또는 수정에 실패했습니다:", error);
    }
  };

  // 기존의 후기 작성 버튼을 클릭했을 때 후기 작성 및 수정 모드로 전환
  const handleReviewButtonClick = () => {
    if (hasReview) {
      setEditReview(review); // 기존 후기가 있을 경우 해당 내용을 불러옴
    }
    setIsEditing(true); // 작성 또는 수정 모드로 전환
  };

  if (loading) {
    return <p>로딩 중...</p>;
  }

  return (
    <div className="funding-reviews">
      <div className="funding-reviews-header">
        <h2>펀딩 후기</h2>
      </div>

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
              작성 완료
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

      {!isEditing && (
        <div className="write-review-button-container">
          <button className="write-review-button" onClick={handleReviewButtonClick}>
            {hasReview ? "후기 수정" : "후기 작성"}
          </button>
        </div>
      )}
    </div>
  );
};

export default FundingReviews;
