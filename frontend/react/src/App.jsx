import React from 'react';
import './MainPage.css';
import logo from "./assets/logo.png";
import download from "./assets/download.png";

const MainPage = () => {
  return (
    <div className="main-page">
      <div className="content">
        <div className="header">
          <p className="brand">GIVU & TAKE</p>

          {/* 회원가입과 로그인 버튼 */}
          <div className="auth-buttons">
            <button className="signup-button">회원가입</button>
            <button className="login-button">로그인</button>
          </div>
        </div>
        
        <h1 className="title">든든한<br />집밥 한상이<br />그리울 때</h1>

        <div className="download-button">
          <a href="https://example.com" className="app-download">
            <img src={download} className="downloadImg" alt="앱 다운로드" />
          </a>
        </div>

        <div className="qr-code">
          <p className="qrtext">QR 코드 다운로드</p>
          <img src={logo} className="qrImg" alt="QR 코드" />
        </div>
      </div>
    </div>
  );
};

export default MainPage;
