import React, { useState } from "react";
import Sidebar from "../Sidebar"; // Sidebar 컴포넌트 import
import "./UserInfo.css";

const UserInfo = () => {
  const [selectedMenu, setSelectedMenu] = useState("회원정보");

  return (
    <div className="user-info-page">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <div className="user-info-container">
        <div className="user-info-header">
          <button className="edit-button">수정</button> {/* 수정 버튼 */}
        </div>
        <div className="user-info-content">
          <div className="profile-picture">
            <img
              src="https://via.placeholder.com/150"
              alt="프로필 사진"
              className="profile-img"
            />
          </div>
          <div className="user-details">
            <h2 className="region-name">지역명</h2>
            <p className="user-info-email">
              <strong>이메일 :</strong> asdf@anver.com
            </p>
            <p className="user-info-phone">
              <strong>전화번호 :</strong> 010-7777-7777
            </p>
          </div>
        </div>
        <div className="user-info-footer">
          <button className="logout-button">로그아웃</button>
        </div>
      </div>
    </div>
  );
};

export default UserInfo;
