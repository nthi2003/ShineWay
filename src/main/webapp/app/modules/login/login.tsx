import React, { useState } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { login } from 'app/shared/reducers/authentication';
import './login.scss';

export const Login = () => {
  const dispatch = useAppDispatch();
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const loginError = useAppSelector(state => state.authentication.loginError);

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [rememberMe, setRememberMe] = useState(false);

  const pageLocation = useLocation();
  const { from } = pageLocation.state || { from: { pathname: '/', search: pageLocation.search } };

  const handleLogin = () => {
    dispatch(login(email, password, rememberMe));
  };

  if (isAuthenticated) {
    return <Navigate to={from} replace />;
  }

  return (
    <div className="login-container">
      <div className="login-content">
        {/* Header */}
        <div className="login-header">
          <span className="brand-name">ShineWay</span>
          <span className="page-title">Đăng Nhập</span>
        </div>

        {/* Main Content */}
        <div className="login-main">
          <img
            src="https://storage.googleapis.com/tagjs-prod.appspot.com/v1/dtkItS0VKm/sdtz7csn_expires_30_days.png"
            alt="ShineWay Logo"
            className="login-logo"
          />
          <div className="login-form">
            <span className="login-subtitle">Vui lòng đăng nhập để trải nghiệm các tiện ích của ShineWay</span>

            {loginError && <div className="error-message">Đăng nhập thất bại! Vui lòng kiểm tra thông tin đăng nhập.</div>}

            {/* Email Input */}
            <div className="input-group">
              <span className="input-icon">@</span>
              <input type="email" placeholder="Tên email" value={email} onChange={e => setEmail(e.target.value)} />
            </div>

            {/* Password Input */}
            <div className="input-group password-group">
              <img
                src="https://storage.googleapis.com/tagjs-prod.appspot.com/v1/dtkItS0VKm/2ihtl8uj_expires_30_days.png"
                alt="Password Icon"
                className="password-icon"
              />
              <input type="password" placeholder="Mật khẩu" value={password} onChange={e => setPassword(e.target.value)} />
            </div>

            {/* Forgot Password */}
            <div className="forgot-password">
              <a href="/account/reset/request">Quên mật khẩu ?</a>
            </div>

            {/* Remember Me */}
            <div className="remember-me">
              <input type="checkbox" id="rememberMe" checked={rememberMe} onChange={e => setRememberMe(e.target.checked)} />
              <label htmlFor="rememberMe">Ghi nhớ đăng nhập</label>
            </div>
          </div>
        </div>

        {/* Login Button */}
        <div className="login-button-container">
          <div className="button-wrapper">
            <button className="login-button" onClick={handleLogin}>
              Đăng Nhập
            </button>
          </div>
        </div>

        {/* Footer */}
        <span className="footer-text">ShineWay – Nền tảng thông minh cho dịch vụ nhà hàng vượt trội</span>
      </div>
    </div>
  );
};

export default Login;
