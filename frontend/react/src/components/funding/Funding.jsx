import React, { useState } from "react";
import Sidebar from "../Sidebar"; // Sidebar import
import "./funding.css"; // 공통 CSS 파일

const Funding = () => {
  const [selectedMenu, setSelectedMenu] = useState("펀딩");

  return (
    <div className="page-layout">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <div className="page-content">
        펀딩 페이지입니다.
      </div>
    </div>
  );
};

export default Funding;
