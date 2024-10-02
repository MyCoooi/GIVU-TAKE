import React, { useEffect, useState } from "react";
import { apiSearchFundingReview } from "../../apis/funding/apiSearchFundingReview"; // API 함수 import
import "./FundingReviews.css";

const FundingReviews = ({ fundingIdx }) => {
  const [review, setReview] = useState("");
  const [loading, setLoading] = useState(true);
  const [hasReview, setHasReview] = useState(false);

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

  if (loading) {
    return <p>로딩 중...</p>;
  }

  return (
    <div className="funding-reviews">
      <h2>펀딩 후기</h2>
      {hasReview ? (
        <p>{review}</p>
      ) : (
        <p>아직 작성된 후기가 없습니다.</p>
      )}
    </div>
  );
};

export default FundingReviews;
