import { AudioOutlined } from '@ant-design/icons';
import { Input, Space, Button } from 'antd';
import React from 'react';
import { useState } from 'react';
import { Modal } from 'antd';
import AddQuestion from './AddQuestion';

const { Search } = Input;
const suffix = (
  <AudioOutlined
    style={{
      fontSize: 16,
      color: '#1890ff',
    }}
  />
);
const onSearch = (value) => console.log(value);

const App = () => {
  const [open, setOpen] = useState(false);
    const showModal = () => {
      setOpen(true);
    };
    const handleOk = () => {
      // setModalText('The modal will be closed after two seconds');
      setOpen(false);
    };
    const handleCancel = () => {
      console.log('Clicked cancel button');
      setOpen(false);
    };
  return (
  <Space direction="horizontal">
    <Search
      placeholder="请输入关键词"
      allowClear
      enterButton="查询题目"
      size="large"
      onSearch={onSearch}
    />
    <Button type="primary" onClick={showModal}>
      添加题目
    </Button>
     <Modal
          title="添加题目"
          open={open}
          onOk={handleOk}
          onCancel={handleCancel}
        >
          <AddQuestion/>
    </Modal>
  </Space>
);
}
export default App;