import {
  UploadOutlined,
  UserOutlined,
  VideoCameraOutlined,
} from '@ant-design/icons';
import { Layout, Menu } from 'antd';
import React from 'react';
import SearchUser from './components/SearchUser';
import UserTable from './components/UserTable';
import SearchQuestion from './components/SearchQuestion';
import QuestionTable from './components/QuestionTable';
import { useState } from 'react';

const { Header, Footer, Sider, Content } = Layout;

const App = () => {
  const [selectedKey, setSelectedKey] = useState('1');
  return(
  <>
    <Layout>
      <Header><h1 style={{ color: '#ffffff' }}>Quiz管理系统</h1></Header>
      <Layout>
          <Sider>
          <Menu
            theme="dark"
            mode="inline"
            defaultSelectedKeys={['1']}
            onClick={({ key }) => setSelectedKey(key)}
            items={[
              { key: '1', icon: <UserOutlined />, label: '用户管理' },
              { key: '2', icon: <VideoCameraOutlined />, label: '题目管理' }
            ]}
          />
        </Sider>   
         <Content>
          {selectedKey === '1' && (
            <>
              <SearchUser />
              <UserTable />
            </>
          )}
          {selectedKey === '2' && (
              <>
              <SearchQuestion />
              <QuestionTable />
            </>
          )}
        </Content>
      </Layout>
        <Footer style={{textAlign: 'center',}}>Quiz管理系统 ©2025 Created by ljy</Footer>
    </Layout>
  </>
);
}
export default App;