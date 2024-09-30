import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MainPage from './components/mainPage/MainPage';
import SignupPage from './components/auth/SignupPage'; // 회원가입 페이지를 import
import LoginPage from './components/auth/LoginPage'; // 회원가입 페이지를 import

import React from 'react';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </Router>
  );
};

export default App;
