import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';
import { Avatar, Badge } from 'antd';
import { QuestionCircleOutlined, BellOutlined, MenuOutlined, UserOutlined } from '@ant-design/icons';

const accountMenuItemsAuthenticated = () => (
  <>
    <MenuItem icon="wrench" to="/account/settings" data-cy="settings">
      Cài đặt
    </MenuItem>
    <MenuItem icon="lock" to="/account/password" data-cy="passwordItem">
      Mật khẩu
    </MenuItem>
    <MenuItem icon="sign-out-alt" to="/logout" data-cy="logout">
      Đăng xuất
    </MenuItem>
  </>
);

const accountMenuItems = () => (
  <>
    <MenuItem id="login-item" icon="sign-in-alt" to="/login" data-cy="login">
      Đăng nhập
    </MenuItem>
    <MenuItem icon="user-plus" to="/account/register" data-cy="register">
      Đăng ký
    </MenuItem>
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <div style={{ display: 'flex', alignItems: 'center', gap: 24 }}>
    <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
      <Avatar size={40} icon={<UserOutlined />} />
      <div>
        <div style={{ fontWeight: 600, color: '#222' }}>Nguyễn Văn Quốc Thi</div>
        <span style={{ background: '#FFD666', color: '#222', borderRadius: 8, padding: '2px 10px', fontSize: 12, fontWeight: 500 }}>
          Quản lí
        </span>
      </div>
    </div>
    <div style={{ height: 32, borderLeft: '1px solid #e5e5e5', margin: '0 16px' }} />
    <QuestionCircleOutlined style={{ fontSize: 22, marginRight: 18, color: '#222' }} />
    <Badge dot>
      <BellOutlined style={{ fontSize: 22, color: '#222' }} />
    </Badge>
    <MenuOutlined style={{ fontSize: 22, color: '#222' }} />
    <NavDropdown
      icon={null}
      name={null}
      id="account-menu"
      data-cy="accountMenu"
      style={{ display: 'none' }} // Ẩn dropdown, chỉ dùng menu khi click vào avatar nếu muốn
    >
      {isAuthenticated && accountMenuItemsAuthenticated()}
      {!isAuthenticated && accountMenuItems()}
    </NavDropdown>
  </div>
);

export default AccountMenu;
