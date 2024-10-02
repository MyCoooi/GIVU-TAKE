import React, { useState } from "react";
import Sidebar from "../Sidebar"; // Sidebar import
import "./Donations.css"; // 공통 CSS 파일

const Donations = () => {
    const [selectedMenu, setSelectedMenu] = useState("기부품");
  
    return (
      <div className="page-layout">
        <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
        <div className="page-content">
          기부 페이지입니다.
        </div>
      </div>
    );
  };
  
  export default Donations;
  