import React from 'react';
import { NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { Input } from 'antd';

export const Brand = () => (
  <NavbarBrand tag={Link} to="/" className="brand-logo" style={{ display: 'flex', alignItems: 'center' }}>
    <span className="font-logo">ShineWay</span>
    <Input.Search placeholder="Tìm kiếm ...." enterButton style={{ width: 300, marginLeft: 16 }} />
  </NavbarBrand>
);
