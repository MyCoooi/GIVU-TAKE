import React from 'react';
import { useNavigate } from 'react-router-dom'; // useNavigate import 추가
import './MainPage.css';
import logo from "../../assets/logo.png";
import download from "../../assets/download.png";

const MainPage = () => {
  const navigate = useNavigate(); // useNavigate 훅 사용
  return (
    <div className="main-page">
      <div className="content">
        <div className="header">
          <p className="brand">GIVU & TAKE</p>

          {/* 회원가입과 로그인 버튼 */}
          <div className="auth-buttons">
            <button className="login-button" onClick={() =>navigate('/login')}>로그인</button>
            <button className="signup-button" onClick={() => navigate('/signup')}>
            회원가입
            </button>
          </div>
        </div>

        
        <h1 className="title">든든한<br />집밥 한상이<br />그리울 때</h1>

        <div className="download-button">
          <a href="https://j11e202.p.ssafy.io/download/app.apk" className="app-download">
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
