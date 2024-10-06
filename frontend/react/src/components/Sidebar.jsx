import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // useNavigate 훅 추가
import './Sidebar.css'; // CSS 파일 import

const Sidebar = ({ selectedMenu, setSelectedMenu }) => {
  const menuItems = ['기부품', '펀딩', '통계', '회원정보'];
  const [isStatisticsOpen, setIsStatisticsOpen] = useState(false); // 통계 탭 하위 메뉴 열림 상태
  const navigate = useNavigate(); // useNavigate 훅 선언

  const handleMenuClick = (item) => {
    setSelectedMenu(item);

    if (item === '기부품') {
      navigate('/donations'); // 기부품 페이지로 이동
    } else if (item === '펀딩') {
      navigate('/funding'); // 펀딩 페이지로 이동
    } else if (item === '통계') {
      setIsStatisticsOpen(!isStatisticsOpen); // 통계 하위 메뉴 열림/닫힘 설정
    } else if (item === '회원정보') {
      navigate('/userinfo'); // 회원정보 페이지로 이동
    }
  };

  const handleSubMenuClick = (subMenu) => {
    if (subMenu === '기부품 통계') {
      navigate('/donation-statistics'); // 기부품 통계 페이지로 이동
    } else if (subMenu === '펀딩 통계') {
      navigate('/funding-statistics'); // 펀딩 통계 페이지로 이동
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
          <div key={item}>
            <div
              className={`menu-item ${selectedMenu === item ? 'menu-item-active' : ''}`}
              onClick={() => handleMenuClick(item)}
            >
              {item}
            </div>
            {/* 통계 탭의 하위 메뉴 */}
            {item === '통계' && isStatisticsOpen && (
              <div className="submenu">
                <div
                  className="submenu-item"
                  onClick={() => handleSubMenuClick('기부품 통계')}
                >
                  기부품 통계
                </div>
                <div
                  className="submenu-item"
                  onClick={() => handleSubMenuClick('펀딩 통계')}
                >
                  펀딩 통계
                </div>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Sidebar;
