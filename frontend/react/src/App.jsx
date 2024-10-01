import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MainPage from './components/mainPage/MainPage';
import SignupPage from './components/auth/SignupPage';
import LoginPage from './components/auth/LoginPage';
import MyPage from './components/myPage/MyPage';
import Donations from './components/donations/Donations';
import Funding from './components/funding/Funding';
import Statistics from './components/statistics/Statistics';
import UserInfo from './components/userinfo/UserInfo';


import React from 'react';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/userinfo" element={<UserInfo />} />
        <Route path="/donations" element={<Donations />} />
        <Route path="/funding" element={<Funding />} />
        <Route path="/statistics" element={<Statistics />} />
      </Routes>
    </Router>
  );
};

export default App;
