import React from 'react';
import { useNavigate } from 'react-router-dom'; // useNavigate 훅 추가
import './Sidebar.css'; // CSS 파일 import

const Sidebar = ({ selectedMenu, setSelectedMenu }) => {
  const menuItems = ['기부품', '펀딩', '통계', '회원정보'];
  const navigate = useNavigate(); // useNavigate 훅 선언

  const handleMenuClick = (item) => {
    setSelectedMenu(item);
    
    // 각 메뉴에 따라 경로 설정
    if (item === '기부품') {
        navigate('/donations'); // 기부품 페이지로 이동
      } else if (item === '펀딩') {
        navigate('/funding'); // 펀딩 페이지로 이동
      } else if (item === '통계') {
        navigate('/statistics'); // 통계 페이지로 이동
      } else if (item === '회원정보') {
        navigate('/userinfo'); // 회원정보 페이지로 이동
      }
    };
    
    return (
    <div className="sidebar">
      <div className="header">GIVU & TAKE</div>
      <div className="profile-section">
        <img
          src="https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"
          alt="프로필사진"
          className="profile-image"
        />
        <div>사용자명</div>
      </div>
      <div className="menu">
        {menuItems.map((item) => (
          <div
            key={item}
            className={`menu-item ${selectedMenu === item ? 'menu-item-active' : ''}`}
            onClick={() => handleMenuClick(item)} // 클릭 시 handleMenuClick 호출
          >
            {item}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Sidebar;
