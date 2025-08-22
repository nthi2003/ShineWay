import React from 'react';
import { Row, Col, Card, Button, Typography, Divider } from 'antd';
import {
  BarChartOutlined,
  FileTextOutlined,
  AppstoreOutlined,
  RiseOutlined,
  InboxOutlined,
  SettingOutlined,
  TeamOutlined,
  FileDoneOutlined,
  MessageOutlined,
  SwapOutlined,
  CalendarOutlined,
  DownOutlined,
} from '@ant-design/icons';

const { Title } = Typography;

const appList = [
  { label: 'Doanh thu', icon: <BarChartOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
  { label: 'Thực đơn', icon: <FileTextOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
  { label: 'Hạ tầng', icon: <AppstoreOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
  { label: 'Lương', icon: <RiseOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
  { label: 'Kho', icon: <InboxOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
  { label: 'Cài đặt', icon: <SettingOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
  { label: 'Nhân sự', icon: <TeamOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
  { label: 'Hóa đơn', icon: <FileDoneOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
  { label: 'Phản hồi', icon: <MessageOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
  { label: 'Ca làm', icon: <SwapOutlined style={{ fontSize: 32, color: '#4A90E2' }} /> },
];

export const Home = () => {
  return (
    <div style={{ background: '#fff', minHeight: '100vh', padding: 32 }}>
      {/* Header */}
      <div style={{ textAlign: 'center', marginBottom: 32 }}>
        <Title level={1} style={{ marginBottom: 0 }}>
          <span style={{ color: '#0088FF' }}>ShineWay</span>
          <span style={{ color: '#222', fontWeight: 700 }}> - Hệ thống hỗ trợ vận hành nhà hàng</span>
        </Title>
      </div>

      {/* Main content */}
      <Row
        gutter={32}
        style={{
          background: '#4A90E2',
          borderRadius: 28,
          maxWidth: 1100,
          margin: '0 auto',
          padding: 40,
        }}
      >
        {/* Left: Card/Chart */}
        <Col xs={24} md={10}>
          <Card
            style={{
              borderRadius: 16,
              marginBottom: 24,
              boxShadow: '0 2px 12px #0002',
            }}
            bodyStyle={{ padding: 20 }}
          >
            <Row align="middle" justify="space-between" style={{ marginBottom: 16 }}>
              <Col>
                <Button style={{ fontWeight: 600 }}>
                  Xuất / Nhập kho <DownOutlined />
                </Button>
              </Col>
              <Col>
                <Button icon={<CalendarOutlined />} />
              </Col>
            </Row>
            <div style={{ marginBottom: 8, fontSize: 15 }}>
              Ngày: <span style={{ color: '#0088FF', fontWeight: 600 }}>19/08/2025</span>
            </div>
            <div style={{ marginBottom: 4 }}>
              Nhập kho: <span style={{ color: 'red', fontWeight: 600 }}>1.090.000đ</span>
            </div>
            <div style={{ marginBottom: 12 }}>
              Xuất kho: <span style={{ color: 'green', fontWeight: 600 }}>520.000đ</span>
            </div>

            <div style={{ height: 80, background: '#f5f5f5', borderRadius: 8, marginBottom: 12 }}>
              <svg width="100%" height="80">
                <polyline
                  fill="none"
                  stroke="#0088FF"
                  strokeWidth="3"
                  points="0,70 30,40 60,60 90,30 120,50 150,20 180,40 210,10 240,30 270,10 300,20"
                />
                <polyline
                  fill="none"
                  stroke="#F5222D"
                  strokeWidth="3"
                  points="0,60 30,30 60,50 90,20 120,40 150,10 180,30 210,20 240,20 270,20 300,10"
                />
              </svg>
            </div>
            <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: 13, color: '#888' }}>
              <span>19/7/2025</span>
              <span>19/8/2025</span>
            </div>
            <Button type="primary" style={{ float: 'right', marginTop: 12 }}>
              Chi tiết &nbsp; &gt;&gt;
            </Button>
          </Card>
        </Col>

        {/* Right: App icons */}
        <Col xs={24} md={14}>
          <div style={{ color: '#fff', fontWeight: 600, fontSize: 20, marginBottom: 24 }}>Tất cả ứng dụng</div>
          <Row gutter={[24, 24]}>
            {appList.map(app => (
              <Col xs={8} md={6} key={app.label}>
                <Card
                  style={{
                    borderRadius: 14,
                    boxShadow: '0 2px 8px #0001',
                    textAlign: 'center',
                    minWidth: 90,
                    minHeight: 90,
                  }}
                  bodyStyle={{ padding: 0, paddingTop: 18, paddingBottom: 10 }}
                  hoverable
                >
                  <div>{app.icon}</div>
                  <div style={{ color: '#222', fontWeight: 600, fontSize: 15, marginTop: 8 }}>{app.label}</div>
                </Card>
              </Col>
            ))}
          </Row>
        </Col>
      </Row>
    </div>
  );
};

export default Home;
