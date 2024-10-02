import React, { useState } from "react";
import Sidebar from "../Sidebar"; // Sidebar import
import "./Statistics.css"; // 공통 CSS 파일

const Statistics = () => {
    const [selectedMenu, setSelectedMenu] = useState("통계");
  
    return (
      <div className="page-layout">
        <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
        <div className="page-content">
          통계 페이지입니다.
        </div>
      </div>
    );
  };
  
  export default Statistics;
  